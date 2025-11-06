package com.qcmz.cmc.process;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcRpAccount;
import com.qcmz.cmc.entity.CmcRpCash;
import com.qcmz.cmc.entity.CmcRpPacket;
import com.qcmz.cmc.entity.CmcRpReceive;
import com.qcmz.cmc.proxy.pay.wxpay.WxpayProxy;
import com.qcmz.cmc.service.ILockService;
import com.qcmz.cmc.service.IRedPacketAccountService;
import com.qcmz.cmc.service.IRedPacketCashService;
import com.qcmz.cmc.service.IRedPacketService;
import com.qcmz.cmc.util.RedPacketUtil;
import com.qcmz.cmc.util.UserUtil;
import com.qcmz.cmc.vo.PrepayBean;
import com.qcmz.cmc.vo.TradeResultBean;
import com.qcmz.cmc.vo.TransferBean;
import com.qcmz.cmc.ws.provide.vo.PrepayResult;
import com.qcmz.cmc.ws.provide.vo.RedPacketBean;
import com.qcmz.cmc.ws.provide.vo.RedPacketCreateBean;
import com.qcmz.cmc.ws.provide.vo.RedPacketCreateResult;
import com.qcmz.cmc.ws.provide.vo.RedPacketPrepayBean;
import com.qcmz.cmc.ws.provide.vo.RedPacketReceiveResult;
import com.qcmz.cmc.ws.provide.vo.RedPacketReceivedBean;
import com.qcmz.cmc.ws.provide.vo.RedPacketSentBean;
import com.qcmz.cmc.ws.provide.vo.RedPacketUserBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.FileServiceUtil;
import com.qcmz.framework.util.FileTypeUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.framework.ws.vo.UserBean;
import com.qcmz.umc.ws.provide.locator.UserWS;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Component
public class RedPacketProcess {
	@Autowired
	private IRedPacketService redPacketService;
	@Autowired
	private IRedPacketAccountService redPacketAccountService;
	@Autowired
	private IRedPacketCashService redPacketCashService;
	
	@Autowired
	private WxpayProxy wxpayProxy;

	@Autowired
	private LockProcess lockProcess;
	@Autowired
	private ILockService lockService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 创建红包
	 * @param bean
	 * @return
	 * @exception DataException ParamException
	 */
	public RedPacketCreateResult create(RedPacketCreateBean bean, String platform, String version){
		RedPacketCreateResult result = new RedPacketCreateResult();
		
		//1.数据校验
		if(bean.getPacketAmount()<1){
			throw new DataException("红包金额有误");
		}
		if(bean.getPacketNum()<1 || bean.getPacketNum()>bean.getPacketAmount()){
			throw new DataException("红包数量有误");
		}
		if(bean.getServiceAmount()!=RedPacketUtil.calServiceAmount(bean.getPacketAmount())){
			throw new ParamException("服务费有误");
		}
		double payableAmount = DoubleUtil.subtract(DoubleUtil.add(bean.getPacketAmount(), bean.getServiceAmount()), bean.getDeductAmount());
		if(bean.getPayableAmount()!=payableAmount){
			throw new ParamException("应付金额有误");
		}
		
		//2.上锁
		Long lockId = lockProcess.addLock4Unique(DictConstant.DICT_LOCKTYPE_REDPACKET_CREATE, String.valueOf(bean.getUid()));
		if(lockId==null){
			throw new ParamException(ExceptionConstants.MSG_OPER_OVERCLOCKING);
		}
		
		//3.保存入库
		CmcRpPacket packet = null;
		try {
			packet = redPacketService.createRedPacket(bean, platform, version);
		} finally{
			if(lockId!=null){
				lockService.deleteLock(lockId);	//解锁
			}
		}
		
		//4.预支付
		try {
			if(DictConstant.DICT_REDPACKET_STATUS_WAITPAY.equals(packet.getStatus())
					&& StringUtil.notBlankAndNull(bean.getTradeWay())){
				RedPacketPrepayBean prepayBean = new RedPacketPrepayBean();
				prepayBean.setPacketId(packet.getPacketid());
				prepayBean.setTradeWay(bean.getTradeWay());
				prepayBean.setUid(packet.getUserid());
				prepayBean.setAmount(packet.getPayableamount());
				prepayBean.setOpenid(bean.getOpenid());
				result.setPrepayResult(prepay(prepayBean, platform));
			}
		} catch (Exception e) {
			logger.error("预支付处理失败："+packet.getPacketid(), e);
		}
		
		result.setPacketId(packet.getPacketid());
		return result;
	}
	
	/**
	 * 取消红包
	 * @param packetId
	 * @param userId
	 * @exception ParamException
	 */
	public void cancel(String packetId, Long userId){
		CmcRpPacket packet = redPacketService.getPacket(packetId);
		if(packet==null || !packet.getUserid().equals(userId)){
			throw new ParamException("无效红包");
		}
		if(!DictConstant.DICT_REDPACKET_STATUS_WAITPAY.equals(packet.getStatus())){
			throw new ParamException("红包不能取消");
		}
		redPacketService.cancelPacket(packetId, userId, packet.getDeductamount());
	}
	
	/**
	 * 预支付
	 * @param bean
	 * @param platform
	 * @return
	 * @exception ParamException
	 */
	public PrepayResult prepay(RedPacketPrepayBean bean, String platform){
		//校验
		CmcRpPacket packet = redPacketService.getPacket(bean.getPacketId());
		if(packet==null){
			throw new ParamException("红包不存在");
		}
		if(!packet.getPayableamount().equals(bean.getAmount())){
			throw new ParamException("金额有误");
		}
		
		//预支付
		PrepayResult preResult = new PrepayResult();
		
		PrepayBean preBean = new PrepayBean();
		preBean.setAmount(bean.getAmount());
		preBean.setOrderId(bean.getPacketId());
		preBean.setOrderDesc(RedPacketUtil.getPayOrderDesc(bean.getPacketId()));
		preBean.setOpenid(bean.getOpenid());
		preBean.setPlatform(platform);
		preBean.setServiceType(DictConstants.DICT_SERVICETYPE_REDPACKET);
		preBean.setSubServiceType(DictConstants.DICT_SUBSERVICETYPE_REDPACKET);
		if(DictConstants.DICT_TRADEWAY_WXPAY.equals(bean.getTradeWay())){
			preResult.setWxpay(wxpayProxy.prePay(preBean));
		}
		
		return preResult;
	}
	
	/**
	 * 处理微信支付异步通知
	 * @param xml
	 * 修改历史：
	 */
	public boolean synWxpay(String xml, String platform){
		TradeResultBean tradeResult = wxpayProxy.parseSyn(xml, DictConstants.DICT_SERVICETYPE_REDPACKET, DictConstants.DICT_SUBSERVICETYPE_REDPACKET, platform);
		if(tradeResult!=null){
			return saveTradeResult(tradeResult);
		}
		return false;
	}
	
	/**
	 * 保存支付结果
	 * @param tradeResult
	 * 修改历史：
	 */
	public boolean saveTradeResult(TradeResultBean tradeResult){
		//1.数据校验
		CmcRpPacket packet = redPacketService.getPacket(tradeResult.getOrderId());
		if(packet==null) return false;
		if(!DictConstant.DICT_TRADESTATUS_SUCCESS.equals(tradeResult.getTradeStatus())) return true;
		
		if(DictConstants.DICT_TRADETYPE_PAY.equals(tradeResult.getTradeType())
				&& DictConstant.DICT_REDPACKET_STATUS_WAITPAY.equals(packet.getStatus())){
			if(!packet.getPayableamount().equals(tradeResult.getAmount())){
				StringBuilder sbTitle = new StringBuilder().append("红包支付金额不匹配：").append(packet.getPacketid());
				StringBuilder sbMsg = new StringBuilder(sbTitle)
				.append("，支付金额[").append(tradeResult.getAmount()).append("]")
				.append("，应付金额[").append(packet.getPayableamount()).append("]")
				;
				MailUtil.sendSimpleMail(SystemConfig.MAILS, null, sbTitle.toString(), sbMsg.toString());
				return false;
			}
			
			//入库
			packet.setTradeway(tradeResult.getTradeWay());
			packet.setPayamount(tradeResult.getAmount());
			packet.setPaytime(tradeResult.getTradeTime());
			packet.setStarttime(new Date());
			packet.setEndtime(DateUtil.addDay(packet.getStarttime(), SystemConfig.REDPACKET_VALIDDAYS));
			packet.setOuttradeid(tradeResult.getOutTradeId());
			packet.setStatus(DictConstant.DICT_REDPACKET_STATUS_VALID);
			redPacketService.paySuccess(packet);
		}
		
		return true;
	}
	
	/**
	 * 获取红包信息
	 * @param packetId
	 * @return
	 * @exception DataException 
	 */
	public RedPacketBean getRedPacketInfo(String packetId, Long receiverId){
		CmcRpPacket packet = redPacketService.getPacket(packetId);
		if(packet==null){
			throw new DataException("红包不存在");
		}else if(!packet.getUserid().equals(receiverId)
				&& !DictConstant.DICT_REDPACKET_STATUS_VALID.equals(packet.getStatus())){
			throw new DataException("无效红包");
		}
		
		RedPacketBean result = new RedPacketBean();
		Set<Long> uids = new HashSet<Long>();
		List<UserBean> users = new ArrayList<UserBean>();
		
		//红包信息
		result.setPacketId(packet.getPacketid());
		result.setTitle(packet.getTitle());
		result.setLangCode(packet.getLangcode());
		result.setTitleCn(packet.getTitlecn());
		result.setPacketAmount(packet.getPacketamount());
		result.setRemainderNum(packet.getPacketnum()-packet.getReceivednum());
		if(packet.getEndtime()!=null){
			result.setEndTime(packet.getEndtime().getTime());
		}
		result.setUser(new UserBean(packet.getUserid()));
		uids.add(packet.getUserid());
		users.add(result.getUser());
		
		//红包领取信息
		if(packet.getReceivednum()>0){
			List<CmcRpReceive> receiveds = redPacketService.findReceived(packetId);
			RedPacketReceivedBean receiveBean = null;
			for (CmcRpReceive cmcRpReceive : receiveds) {
				receiveBean = new RedPacketReceivedBean();
				receiveBean.setPacketId(cmcRpReceive.getPacketid());
				receiveBean.setAmount(cmcRpReceive.getAmount());
				receiveBean.setReceiveTime(cmcRpReceive.getReceivetime().getTime());
				receiveBean.setVoice(cmcRpReceive.getVoice());
				receiveBean.setReceiver(new UserBean(cmcRpReceive.getReceiverid()));
				
				result.getReceives().add(receiveBean);
				uids.add(cmcRpReceive.getReceiverid());
				users.add(receiveBean.getReceiver());
				
				if(receiverId!=null && receiverId.equals(cmcRpReceive.getReceiverid())){
					result.setReceiveAmount(cmcRpReceive.getAmount());
				}
			}
		}
		
		//用户信息
		List<UserSimpleBean> userList = UserWS.findUserSimple(uids);
		for (UserBean userBean : users) {
			for (UserSimpleBean user : userList) {
				if(userBean.getUid().equals(user.getUid())){
					userBean.setNick(user.getNick());
					userBean.setIconUrl(user.getIconUrl());
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 领取红包
	 * @param packetId
	 * @param receiverId
	 * @param file
	 * @return
	 * @exception ParamException,DataException
	 */
	public RedPacketReceiveResult receive(String packetId, Long receiverId, File file){
		//1.校验
		String fileType = FileTypeUtil.getMultimediaType(file);
		if(StringUtil.isBlankOrNull(fileType)){
			throw new ParamException("语音类型有误");
		}
		
		CmcRpPacket packet = redPacketService.getPacket(packetId);
		if(packet==null || !DictConstant.DICT_REDPACKET_STATUS_VALID.equals(packet.getStatus())
				|| packet.getEndtime().getTime()<System.currentTimeMillis()){
			throw new DataException("红包无效");
		}
		
		CmcRpReceive receive = redPacketService.getReceive(packetId, receiverId);
		if(receive!=null){
			throw new DataException("您已领过该红包");
		}
		
		//2.上传文件
		String voiceDirPath = UserUtil.getRedPacketVoiceBasePath(packetId);
		String voiceFileName = new StringBuilder().append(receiverId).append(".").append(fileType).toString();
		String voiceUrl = FileServiceUtil.saveOrUploadFile(file, voiceDirPath, voiceFileName);

		if(StringUtil.isBlankOrNull(voiceUrl)){
			throw new ParamException("语音保存失败");
		}
		
		//3.上锁领取红包
		Long lockId = lockProcess.addLock4Unique(DictConstant.DICT_LOCKTYPE_REDPACKET_RECEIVE, String.valueOf(packetId));
		if(lockId==null){
			throw new DataException("稍后再试");
		}
		
		RedPacketReceiveResult result = new RedPacketReceiveResult();
		try {
			List<CmcRpReceive> list = redPacketService.findUnReceive(packetId);
			if(list.isEmpty()){
				throw new DataException("红包领完了");
			}
			CmcRpReceive entity = list.get(0);
			entity.setReceiverid(receiverId);
			entity.setReceivetime(new Date());
			entity.setVoice(voiceUrl);
			redPacketService.receiveRedPacket(entity);;
			
			result.setAmount(entity.getAmount());
		} finally {
			lockService.deleteLock(lockId);	//解锁
		}
		
		return result;
	}
	
	/**
	 * 申请提现
	 * @param userId
	 * @param cashAmount
	 * @exception ParamException,DataException
	 */
	public void applyCash(Long userId, String wxopenid, Double cashAmount){
		//1.上锁
		Long lockId = lockProcess.addLock4Unique(DictConstant.DICT_LOCKTYPE_REDPACKET_APPLYCASH, String.valueOf(userId));
		if(lockId==null){
			throw new DataException(ExceptionConstants.MSG_OPER_OVERCLOCKING);
		}
		
		//2.申请提现
		try {
			//校验
			CmcRpAccount account = redPacketAccountService.getAccount(userId);
			if(account==null){
				throw new ParamException("帐户不存在");
			}else if(account.getBalance()==0){
				throw new DataException("可提现金额为0");
			}else if(cashAmount>account.getBalance()){
				throw new DataException("申请提现金额超过可提现金额");
			}
			
			//用户微信openid
			if(StringUtil.notBlankAndNull(account.getWxopenid())){
				if(!account.getWxopenid().equals(wxopenid)){
					throw new ParamException("openid有误");
				}
			}else{
				redPacketAccountService.updateWxopenid(userId, wxopenid);
			}
			
			redPacketCashService.applyCash(userId, cashAmount);
		} finally {
			lockService.deleteLock(lockId);	//解锁
		}
	}
	
	/**
	 * 转账付款
	 * @param cashId
	 * @param oper
	 * @exception ProxyException
	 */
	public void transfer(Long cashId, Operator oper){
		//转账
		TradeResultBean tradeResult = transfer(cashId);
		
		if(DictConstant.DICT_TRADESTATUS_FAIL.equals(tradeResult.getTradeStatus())){
			throw new ProxyException(tradeResult.getTradeResult());
		}
		//转账成功变更状态
		redPacketCashService.successCash(cashId, tradeResult.getOutTradeId(), tradeResult.getTradeTime(), oper);
	}
	
	/**
	 * 驳回提现申请
	 * @param cashId
	 * @param reason
	 * @param oper
	 */
	public void rejectCash(Long cashId, String reason, Operator oper){
		redPacketCashService.rejectCash(cashId, reason, oper);
	}
	
	/**
	 * 提现转账
	 * @param cashId
	 */
	public TradeResultBean transfer(Long cashId){
		CmcRpCash cash = redPacketCashService.getCash(cashId);
		CmcRpAccount account = redPacketAccountService.getAccount(cash.getUserid());
		
		TransferBean transferBean = new TransferBean();
		transferBean.setOrderId(RedPacketUtil.getTransferOrderId(cash.getUserid(), cash.getCashid()));
		transferBean.setOrderDesc(RedPacketUtil.getTransferOrderDesc(transferBean.getOrderId()));
		transferBean.setAmount(cash.getCashamount());
		transferBean.setOpenid(account.getWxopenid());
		transferBean.setPlatform(DictConstants.DICT_PLATFORM_MINIPROGRAM);
		transferBean.setServiceType(DictConstants.DICT_SERVICETYPE_REDPACKET);
		transferBean.setSubServiceType(DictConstants.DICT_SUBSERVICETYPE_REDPACKET);
		
		return wxpayProxy.transfer(transferBean);
	}
	
	/**
	 * 分页获取用户发出的红包
	 * @param userId
	 * @param pageBean
	 * @return
	 * @exception ParamException
	 */
	@SuppressWarnings("unchecked")
	public RedPacketUserBean findSent(Long userId, PageBean pageBean){
		RedPacketUserBean result = new RedPacketUserBean();
		
		//帐户信息
		result.setAccount(redPacketAccountService.getAccountInfo(userId));
		
		//红包列表
		redPacketService.findSent(userId, pageBean);
		List<CmcRpPacket> list = (List<CmcRpPacket>) pageBean.getResultList();
		if(list.isEmpty()){
			return result;
		}
		
		RedPacketSentBean packetBean = null;
		for (CmcRpPacket packet : list) {
			packetBean = new RedPacketSentBean();
			packetBean.setPacketId(packet.getPacketid());
			packetBean.setTitle(packet.getTitle());
			packetBean.setLangCode(packet.getLangcode());
			packetBean.setTitleCn(packet.getTitlecn());
			packetBean.setPacketAmount(packet.getPacketamount());
			packetBean.setCreateTime(packet.getCreatetime().getTime());
			
			result.getSents().add(packetBean);
		}
		
		return result;
	}
	
	/**
	 * 获取用户领取的红包
	 * @param userId
	 * @param pageBean
	 * @return
	 * @exception ParamException
	 */
	@SuppressWarnings("unchecked")
	public RedPacketUserBean findReceived(Long receiverId, PageBean pageBean){
		RedPacketUserBean result = new RedPacketUserBean();
		
		//帐户信息
		result.setAccount(redPacketAccountService.getAccountInfo(receiverId));
		
		//红包列表
		redPacketService.findReceived(receiverId, pageBean);
		List<CmcRpReceive> list = (List<CmcRpReceive>) pageBean.getResultList();
		if(list.isEmpty()){
			return result;
		}
		
		Set<Long> uids = new HashSet<Long>();
		List<UserBean> users = new ArrayList<UserBean>();
		RedPacketReceivedBean receiveBean = null;
		for (CmcRpReceive cmcRpReceive : list) {
			receiveBean = new RedPacketReceivedBean();
			receiveBean.setPacketId(cmcRpReceive.getPacketid());
			receiveBean.setAmount(cmcRpReceive.getAmount());
			receiveBean.setReceiveTime(cmcRpReceive.getReceivetime().getTime());
			receiveBean.setVoice(cmcRpReceive.getVoice());
			receiveBean.setUser(new UserBean(cmcRpReceive.getUserid()));
			
			result.getReceiveds().add(receiveBean);
			uids.add(cmcRpReceive.getUserid());
			users.add(receiveBean.getUser());
		}
		
		//用户信息
		List<UserSimpleBean> userList = UserWS.findUserSimple(uids);
		for (UserBean userBean : users) {
			for (UserSimpleBean user : userList) {
				if(userBean.getUid().equals(user.getUid())){
					userBean.setNick(user.getNick());
					userBean.setIconUrl(user.getIconUrl());
				}
			}
		}
		
		return result;
	}

	/**
	 * 红包过期处理
	 */
	public void expireDeal(){
		//过期
		List<String> list = redPacketService.findPacket4ExpireDeal();
		for (String packetId : list) {
			try {
				logger.info("开始处理过期红包："+packetId);
				redPacketService.expireRedPacket(packetId);
				logger.info("完成处理过期红包："+packetId);
			} catch (Exception e) {
				logger.info("处理过期红包失败："+packetId, e);
			}
		}
	}
	
	/**
	 * 红包取消处理
	 */
	public void cancelDeal(){
		List<String> list = redPacketService.findPacket4CancelDeal();
		CmcRpPacket packet = null;
		for (String packetId : list) {
			try {
				logger.info("开始取消红包："+packetId);
				packet = redPacketService.getPacket(packetId);
				redPacketService.cancelPacket(packetId, packet.getUserid(), packet.getDeductamount());
				logger.info("完成取消红包："+packetId);
			} catch (Exception e) {
				logger.info("取消红包失败："+packetId, e);
			}
		}
	}
}
