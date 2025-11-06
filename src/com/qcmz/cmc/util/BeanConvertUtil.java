package com.qcmz.cmc.util;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.cmc.cache.LangMap;
import com.qcmz.cmc.entity.CmcCombo;
import com.qcmz.cmc.entity.CmcComboLang;
import com.qcmz.cmc.entity.CmcComboPackage;
import com.qcmz.cmc.entity.CmcComboScene;
import com.qcmz.cmc.entity.CmcCtOption;
import com.qcmz.cmc.entity.CmcCtSubject;
import com.qcmz.cmc.entity.CmcCtTask;
import com.qcmz.cmc.entity.CmcDialogMsg;
import com.qcmz.cmc.entity.CmcEval;
import com.qcmz.cmc.entity.CmcIrImage;
import com.qcmz.cmc.entity.CmcIrRecognition;
import com.qcmz.cmc.entity.CmcPkgDaysentence;
import com.qcmz.cmc.entity.CmcPtTrans;
import com.qcmz.cmc.entity.CmcREval;
import com.qcmz.cmc.entity.CmcRItem;
import com.qcmz.cmc.entity.CmcRMsg;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcRpAccount;
import com.qcmz.cmc.entity.CmcUCombo;
import com.qcmz.cmc.vo.TransPicIPictureTransBean;
import com.qcmz.cmc.ws.provide.vo.CrowdOptionBean;
import com.qcmz.cmc.ws.provide.vo.CrowdSubjectBean;
import com.qcmz.cmc.ws.provide.vo.DaySentenceBean;
import com.qcmz.cmc.ws.provide.vo.DialogMsgBean;
import com.qcmz.cmc.ws.provide.vo.ImageRecognitionBean;
import com.qcmz.cmc.ws.provide.vo.ImageRecognitionImageBean;
import com.qcmz.cmc.ws.provide.vo.OrderDetailBean;
import com.qcmz.cmc.ws.provide.vo.OrderEvalDetailBean;
import com.qcmz.cmc.ws.provide.vo.OrderEvalTagBean;
import com.qcmz.cmc.ws.provide.vo.OrderItemBean;
import com.qcmz.cmc.ws.provide.vo.OrderItemListBean;
import com.qcmz.cmc.ws.provide.vo.OrderListBean;
import com.qcmz.cmc.ws.provide.vo.OrderMsgBean;
import com.qcmz.cmc.ws.provide.vo.RedPacketAccountBean;
import com.qcmz.cmc.ws.provide.vo.SceneBean;
import com.qcmz.cmc.ws.provide.vo.TransComboBean;
import com.qcmz.cmc.ws.provide.vo.TransComboLangBean;
import com.qcmz.cmc.ws.provide.vo.TransComboPackageBean;
import com.qcmz.cmc.ws.provide.vo.TransComboTagBean;
import com.qcmz.cmc.ws.provide.vo.TransPicTransBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdAnswerBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdAnswerQcBean;
import com.qcmz.cmc.ws.provide.vo.UserTransComboBean;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class BeanConvertUtil {
	
	public static DaySentenceBean toDaySentenceBean(CmcPkgDaysentence sentence, LangMap langMap){
		DaySentenceBean result = new DaySentenceBean();
		result.setSentenceId(sentence.getSentid());
		result.setDate(DateUtil.formatDate(sentence.getReleasetime()));
		result.setDate2(CmcUtil.formatReleaseTime(result.getDate()));
		result.setTitle(sentence.getTitle());
		result.setFrom(sentence.getFromlang());
		result.setFromName(langMap.getLangName4Text(sentence.getFromlang()));
		result.setSrc(sentence.getSrc());
		result.setTo(sentence.getTolang());
		result.setToName(langMap.getLangName4Text(sentence.getTolang()));
		result.setDst(sentence.getDst());
		result.setSource(sentence.getSource());
		result.setPic(sentence.getPic());
		result.setHtmlUrl(sentence.getHtmlurl());
		return result;
	}
	
	/**
	 * 转为图片打点信息
	 * @param cmcPtTrans
	 * @return
	 * 修改历史：
	 */
	public static TransPicIPictureTransBean toTransPicIPictureTransBean(CmcPtTrans cmcPtTrans){
		TransPicIPictureTransBean result = new TransPicIPictureTransBean();
		result.setId(String.valueOf(cmcPtTrans.getTransid()));
		result.setDescr(cmcPtTrans.getDst());
		result.setLeft(cmcPtTrans.getPosx());
		result.setTop(cmcPtTrans.getPosy());
		return result;		
	}
	
	/**
	 * 转为图片打点信息列表
	 * @param list
	 * @return
	 * 修改历史：
	 */
	public static List<TransPicIPictureTransBean> toTransPicIPictureTransBean(List<CmcPtTrans> list){
		List<TransPicIPictureTransBean> result = new ArrayList<TransPicIPictureTransBean>();
		if(list!=null){
			for (CmcPtTrans entity : list) {
				result.add(toTransPicIPictureTransBean(entity));
			}
		}
		return result;
	}
	
	public static TransPicTransBean toTransPicTransBean(CmcPtTrans cmcPtTrans){
		TransPicTransBean result = new TransPicTransBean();
		result.setTransId(cmcPtTrans.getTransid());
		result.setDst(cmcPtTrans.getDst());
		result.setX(cmcPtTrans.getPosx());
		result.setY(cmcPtTrans.getPosy());
		return result;
	}
	
	/**
	 * 转换翻译结果
	 * 多个翻译结果用§;§分隔，每个翻译内容用§|§分隔
	 * 格式为"翻译编号§|§横坐标§|§纵坐标§|§译文§;§翻译编号§|§横坐标§|§纵坐标§|§译文"
	 * @param transResult
	 * @return
	 * 修改历史：
	 */
	public static List<TransPicTransBean> toTransResult(String transResult){
		List<TransPicTransBean> result = new ArrayList<TransPicTransBean>();
		if(StringUtil.notBlankAndNull(transResult)){
			String[] arrTrans = StringUtil.split(transResult, SystemConstants.SEPARATOR_SEMICOLONMULT);
			TransPicTransBean bean = null;
			for (String str : arrTrans) {
				String[] arr = StringUtil.splitPreserveAllTokens(str, SystemConstants.SEPARATOR_BARMULT);
				bean = new TransPicTransBean();
				if(NumberUtil.isNumber(arr[0])){
					bean.setTransId(Long.valueOf(arr[0]));
				}
				bean.setX(Integer.valueOf(arr[1]));
				bean.setY(Integer.valueOf(arr[2]));
				bean.setDst(arr[3]);
				result.add(bean);
			}
		}
		return result;
	}
	
	/**
	 * 转订单详细信息
	 * @param order
	 * @return
	 */
	public static OrderDetailBean toOrderDetailBean(CmcROrder order, CmcUCombo userCombo){
		OrderDetailBean bean = new OrderDetailBean();
		bean.setOrderId(order.getOrderid());
		bean.setUid(order.getUserid());
		bean.setOrderType(order.getOrdertype());
		bean.setTradeWay(order.getTradeway());
		bean.setAmount(order.getAmount());
		bean.setCouponAmount(order.getCouponamount());
		bean.setWalletAmount(order.getWalletamount());
		bean.setPayableAmount(order.getPayableamount());
		if(userCombo!=null){
			bean.setComboTitle(userCombo.getCombotitle());
		}
		bean.setCreateTime(order.getCreatetime().getTime());
		bean.setOperTime(order.getOpertime()==null?null:order.getOpertime().getTime());
		bean.setNeedTime(order.getNeedtime());
		bean.setAppointFlag(order.getAppointflag());
		bean.setAppointTime(order.getAppointtime()==null?null:order.getAppointtime().getTime());
		bean.setRequirement(order.getRequirement());
		bean.setMobile(order.getMobile());
		bean.setAddress(order.getAddress());
		bean.setLon(order.getLon());
		bean.setLat(order.getLat());
		bean.setDealStatus(order.getDealstatus());
		bean.setEvalStatus(order.getEvalstatus());
		return bean;
	}
	
	/**
	 * 转商品信息
	 * @param cmcRItem
	 * @return
	 */
	public static OrderItemBean toOrderItemBean(CmcRItem cmcRItem){
		OrderItemBean bean = new OrderItemBean();
		bean.setItemName(cmcRItem.getItemname());
		bean.setPicUrl(cmcRItem.getPic());
		bean.setOriPrice(cmcRItem.getOriprice());
		bean.setPrice(cmcRItem.getPrice());
		bean.setItemNum(cmcRItem.getItemnum());
		if(cmcRItem.getStarttime()!=null){
			bean.setStartTime(cmcRItem.getStarttime().getTime());
		}
		if(cmcRItem.getEndtime()!=null){
			bean.setEndTime(cmcRItem.getEndtime().getTime());
		}
		return bean;
	}
	
	/**
	 * 转商品信息
	 * @param cmcRItem
	 * @return
	 */
	public static OrderItemListBean toOrderItemListBean(CmcRItem cmcRItem, OrderListBean orderBean){
		OrderItemListBean bean = new OrderItemListBean();
		bean.setItemName(cmcRItem.getItemname());
		bean.setFrom(orderBean.getFrom());
		bean.setTo(orderBean.getTo());
		bean.setPicUrl(cmcRItem.getPic());
		bean.setOriPrice(cmcRItem.getOriprice());
		bean.setPrice(cmcRItem.getPrice());
		bean.setItemNum(cmcRItem.getItemnum());
		if(cmcRItem.getStarttime()!=null){
			bean.setStartTime(cmcRItem.getStarttime().getTime());
		}
		if(cmcRItem.getEndtime()!=null){
			bean.setEndTime(cmcRItem.getEndtime().getTime());
		}
		return bean;
	}
	
	public static RedPacketAccountBean toRedPacketAccountBean(CmcRpAccount cmcRpAccount){
		RedPacketAccountBean bean = new RedPacketAccountBean();
		bean.setUid(cmcRpAccount.getUserid());
		bean.setBalance(cmcRpAccount.getBalance());
		bean.setCashAmount(cmcRpAccount.getCashamount());
		bean.setReceiveNum(cmcRpAccount.getReceivenum());
		bean.setReceiveAmount(cmcRpAccount.getReceiveamount());
		bean.setSendNum(cmcRpAccount.getSendnum());
		bean.setSendAmount(cmcRpAccount.getSendamount());
		return bean;
	}
	
	public static TransComboBean toTransComboBean(CmcCombo cmcCombo){
		TransComboBean bean = new TransComboBean();
		bean.setComboId(cmcCombo.getComboid());
		bean.setTransType(cmcCombo.getTranstype());
		bean.setComboType(cmcCombo.getCombotype());
		bean.setTitle(cmcCombo.getTitle());
		bean.setOriUnitPrice(cmcCombo.getOriunitprice());
		bean.setUnitPrice(cmcCombo.getUnitprice());
		bean.setPriceUnit(cmcCombo.getPriceunit());
		bean.setUnitName(cmcCombo.getUnitname());
		bean.setServiceWay(cmcCombo.getServiceway());
		bean.setServiceAccount(cmcCombo.getServiceaccount());
		bean.setIcon(cmcCombo.getIcon());
		bean.setPic(cmcCombo.getPic());
		bean.setOverview(cmcCombo.getOverview());
		bean.setDescription(cmcCombo.getDescription());
		bean.setStartTime(cmcCombo.getStarttime().getTime());
		bean.setEndTime(cmcCombo.getEndtime().getTime());
		
		if(StringUtil.notBlankAndNull(cmcCombo.getTags())){
			String[] arr = StringUtil.split(cmcCombo.getTags(), ",");
			for (String tag : arr) {
				bean.getTags().add(new TransComboTagBean(tag));
			}
		}
		
		return bean;
	}
	
	public static TransComboPackageBean toTransComboPackageBean(CmcCombo combo, CmcComboPackage entity){
		TransComboPackageBean bean = new TransComboPackageBean();
		bean.setPkgId(entity.getPkgid());
		bean.setComboId(entity.getComboid());
		bean.setPkgTitle(entity.getPkgtitle());
		bean.setPkgOverview(entity.getPkgoverview());
		bean.setNum(entity.getNum());
		bean.setOriPrice(DoubleUtil.multiply(combo.getOriunitprice(), Double.valueOf(entity.getNum())));
		bean.setPrice(entity.getPrice());
		bean.setPerBuyTimes(entity.getPerbuytimes());
		return bean;
	}
	
	public static UserTransComboBean toUserTransComboBean(CmcUCombo uc, CmcCombo combo){
		UserTransComboBean bean = new UserTransComboBean();
		bean.setUcid(uc.getUcid());
		bean.setTransType(uc.getTranstype());
		bean.setComboType(uc.getCombotype());
		bean.setNum(uc.getNum());
		bean.setUsableNum(uc.getUsableNumReal());
		bean.setUnit(uc.getUnit());
		bean.setUnitName(combo.getUnitname());
		if(uc.getStarttime()!=null){
			bean.setStartTime(uc.getStarttime().getTime());
		}
		if(uc.getEndtime()!=null){
			bean.setEndTime(uc.getEndtime().getTime());
		}
		bean.setComboId(combo.getComboid());
		bean.setComboTitle(uc.getCombotitle());
		bean.setComboIcon(combo.getIcon());
		bean.setComboDesc(combo.getDescription());
		bean.setServiceWay(combo.getServiceway());
		bean.setServiceAccount(combo.getServiceaccount());
		
		for (CmcComboLang lang : combo.getLangs()) {
			bean.getLangs().add(new TransComboLangBean(lang.getFromlang(), lang.getTolang()));
		}
		
		for (CmcComboScene scene : combo.getScenes()) {
			bean.getScenes().add(new SceneBean(scene.getCmcBScene().getSceneid(), scene.getCmcBScene().getTranstype(), scene.getCmcBScene().getScenename(), scene.getCmcBScene().getIcon()));
		}
		

		return bean;
	}
	
	public static OrderEvalDetailBean toOrderEvalDetailBean(CmcREval cmcREval, CmcEval eval){
		if(cmcREval==null) return null;
		OrderEvalDetailBean bean = new OrderEvalDetailBean();
		bean = new OrderEvalDetailBean();
		bean.setOrderId(cmcREval.getOrderid());
		bean.setEvalId(cmcREval.getEvalid());
		bean.setEvalLevel(cmcREval.getEvallevel());
		bean.setLevelName(cmcREval.getLevelname());

		if(eval!=null){
			bean.setEvalType(eval.getEvaltype());
			bean.setIcon(eval.getIcon());
		}
		
		if(StringUtil.notBlankAndNull(cmcREval.getEvaltag())){
			String[] arr = StringUtil.split(cmcREval.getEvaltag(), ";");
			for (String tag : arr) {
				bean.getTags().add(new OrderEvalTagBean(tag));
			}
		}
		return bean;
	}
	
	/**
	 * 
	 * @param msgList 带订单信息
	 * @return
	 */
	public static List<OrderMsgBean> toOrderMsgBean(List<CmcRMsg> msgList){
		List<OrderMsgBean> result = new ArrayList<OrderMsgBean>();
		OrderMsgBean bean = null;
		
		if(msgList==null || msgList.isEmpty()) return result;
		
		for (CmcRMsg msg : msgList) {
			bean = new OrderMsgBean();
			bean.setMsgId(msg.getMsgid());
			bean.setOrderId(msg.getOrderid());
			bean.setPicId(msg.getOrderid());
			bean.setOrderType(msg.getCmcROrder().getOrdertype());
			bean.setMsg(msg.getMsg());
			bean.setSideType(msg.getSidetype());
			bean.setCreateTime(msg.getCreatetime().getTime());
			result.add(bean);
		}
		
		return result;
	}
	
	/**
	 * 转换众包任务题目
	 * @param subjects
	 * @param task
	 * @return
	 */
	public static List<CrowdSubjectBean> toCrowdSubjectBean(List<CmcCtSubject> subjects, CmcCtTask task){
		List<CrowdSubjectBean> result = new ArrayList<CrowdSubjectBean>();
		CrowdSubjectBean subjectBean;
		CrowdOptionBean optionBean;
		
		for (CmcCtSubject subject : subjects) {
			//题目
			subjectBean = new CrowdSubjectBean();
			subjectBean.setSubjectId(subject.getSubjectid());
			subjectBean.setSubjectCat(subject.getSubjectcat());
			subjectBean.setSubjectType(subject.getSubjecttype());
			subjectBean.setPassScore(subject.getPassscore());
			subjectBean.setSubjectReward(subject.getSubjectreward());
			subjectBean.setFrom(subject.getFromlang());
			subjectBean.setFromLangName(CrowdTaskUtil.getLangName(subject.getFromlang()));
			subjectBean.setTo(subject.getTolang());
			subjectBean.setToLangName(CrowdTaskUtil.getLangName(subject.getTolang()));
			subjectBean.setContentType(subject.getContenttype());
			subjectBean.setContent(subject.getContent());
			subjectBean.setAudio(subject.getAudio());
			subjectBean.setVideo(subject.getVideo());
			subjectBean.setVideoSource(subject.getVideosource());
			subjectBean.setPic(subject.getPic());
			subjectBean.setAdParam(subject.getAdparam());
			subjectBean.setRecordTimes(subject.getRecordtimes());
			subjectBean.setBgPic(task.getPic3());
			subjectBean.setAutoVerify(SystemConstants.STATUS_ON.equals(task.getAutoverify()));
			subjectBean.setAutoQc(SystemConstants.STATUS_ON.equals(task.getAutoqc()));
			
			//选项
			for (CmcCtOption option : subject.getOptions()) {
				optionBean = new CrowdOptionBean();
				optionBean.setOptionId(option.getOptionid());
				optionBean.setOptionValue(option.getOptionvalue());
				optionBean.setOptionTitle(option.getOptiontitle());
				optionBean.setMore(SystemConstants.STATUS_ON.equals(option.getWritemore()));
								
				subjectBean.getOptions().add(optionBean);
			}
			
			result.add(subjectBean);
		}
		
		return result;
	}
	
	/**
	 * 转换众包任务用户答案
	 * 多个答案用§;§分隔，每个答案用§|§分隔
	 * 格式为"题目编号§|§选项编号§|§更多内容§|§录音§|§录音时长§|§译文§|§得分§|§图片§;§题目编号§|§选项编号§|§更多内容§|§录音§|§录音时长§|§译文§|§得分§|§图片"
	 * @param answer
	 * @return
	 * 修改历史：
	 */
	public static List<UserCrowdAnswerBean> toUserCrowdAnswer(String answer){
		List<UserCrowdAnswerBean> result = new ArrayList<UserCrowdAnswerBean>();
		if(StringUtil.notBlankAndNull(answer)){
			String[] arrTrans = StringUtil.split(answer, SystemConstants.SEPARATOR_SEMICOLONMULT);
			UserCrowdAnswerBean bean = null;
			for (String str : arrTrans) {
				String[] arr = StringUtil.splitPreserveAllTokens(str, SystemConstants.SEPARATOR_BARMULT);
				bean = new UserCrowdAnswerBean();
				bean.setSubjectId(Long.valueOf(arr[0]));
				if(NumberUtil.isNumber(arr[1])){
					bean.setOptionId(Long.valueOf(arr[1]));
				}
				bean.setMoreContent(arr[2]);
				bean.setVoice(arr[3]);
				if(NumberUtil.isNumber(arr[4])){
					bean.setVoiceDuration(Integer.valueOf(arr[4]));
				}
				bean.setDst(arr[5]);
				if(arr.length>=7 && NumberUtil.isNumber(arr[6])){
					bean.setAnswerScore(Integer.valueOf(arr[6]));
				}
				if(arr.length>=8){
					bean.setPic(arr[7]);
				}
				result.add(bean);
			}
		}
		return result;
	}
	
	/**
	 * 转换众包任务答案审校结果
	 * 多个用;分隔
	 * 格式为"答案编号|得分;答案编号|得分"
	 * @param answerQc
	 * @return
	 */
	public static List<UserCrowdAnswerQcBean> toUserCrowdAnswerQcBean(String answerQc){
		List<UserCrowdAnswerQcBean> result = new ArrayList<UserCrowdAnswerQcBean>();
		if(StringUtil.notBlankAndNull(answerQc)){
			String[] arrTrans = StringUtil.split(answerQc, SystemConstants.SEPARATOR_SEMICOLON);
			UserCrowdAnswerQcBean bean = null;
			for (String str : arrTrans) {
				String[] arr = StringUtil.splitPreserveAllTokens(str, SystemConstants.SEPARATOR_BAR);
				bean = new UserCrowdAnswerQcBean();
				bean.setUaId(Long.valueOf(arr[0]));
				bean.setAnswerScore(Integer.valueOf(arr[1]));
				result.add(bean);
			}
		}
		return result;
	}
	
	/**
	 * 转换图像识别信息
	 * @param image 带识别结果列表
	 * @return
	 */
	public static ImageRecognitionImageBean toImageRecognitionImageBean(CmcIrImage image, String catName){
		ImageRecognitionImageBean result = new ImageRecognitionImageBean();
		result.setImageId(image.getImageid());
		result.setImageUrl(image.getImageurl());
		result.setThumbUrl(image.getThumburl());
		result.setCatId(image.getCatid());
		result.setCatName(catName);
		result.setCreateTime(image.getCreatetime());
		for (CmcIrRecognition ir : image.getCmcIrRecognitions()) {
			result.getRecognitions().add(new ImageRecognitionBean(ir.getContent(), ir.getScore()));
		}
		return result;
	}
	
	/**
	 * 转换对话消息信息
	 * @param list
	 * @return
	 */
	public static List<DialogMsgBean> toDialogMsgBean(List<CmcDialogMsg> list){
		List<DialogMsgBean> result = new ArrayList<DialogMsgBean>();
		DialogMsgBean bean = null;
		
		if(list==null || list.isEmpty()) return result;
		
		for (CmcDialogMsg msg : list) {
			bean = new DialogMsgBean();
			bean.setMsgId(msg.getMsgid());
			bean.setMsgSide(msg.getMsgside());
			bean.setMsg(msg.getMsg());
			bean.setCreateTime(msg.getCreatetime().getTime());
			bean.setSortId(String.valueOf(msg.getMsgid()));
			result.add(bean);
		}
		
		return result;
	}
	
}
