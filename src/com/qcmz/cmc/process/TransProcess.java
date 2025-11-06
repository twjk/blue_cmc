package com.qcmz.cmc.process;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.AppUserForbidMap;
import com.qcmz.cmc.cache.KeywordMap;
import com.qcmz.cmc.cache.ProxyMap;
import com.qcmz.cmc.cache.TransCapMap;
import com.qcmz.cmc.cache.TransMachineMap;
import com.qcmz.cmc.cache.TransResultMap;
import com.qcmz.cmc.comparator.TransResultComparator;
import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.entity.CmcKeyword;
import com.qcmz.cmc.entity.CmcKeywordType;
import com.qcmz.cmc.proxy.ProxyMgr;
import com.qcmz.cmc.proxy.trans.AbstractTransProxy;
import com.qcmz.cmc.thrd.SearchThrd;
import com.qcmz.cmc.thrd.SystemMonitorNotifyThrd;
import com.qcmz.cmc.thrd.TransLogThrd;
import com.qcmz.cmc.util.TransUtil;
import com.qcmz.cmc.vo.TransProxyBean;
import com.qcmz.cmc.vo.TransferProxyBean;
import com.qcmz.cmc.ws.provide.vo.TransBean;
import com.qcmz.cmc.ws.provide.vo.TransDstBean;
import com.qcmz.cmc.ws.provide.vo.TransResult;
import com.qcmz.cmc.ws.provide.vo.TransResultV2;
import com.qcmz.cmc.ws.provide.vo.TransTermBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：翻译
 * 修改历史：
 */
@Component
public class TransProcess {
	@Autowired
	private TransResultMap transResultMap;
	@Autowired
	private TransCapMap transCapMap;
	@Autowired
	private ProxyMgr proxyMgr;
	@Autowired
	private ProxyMap proxyMap;
	@Autowired
	private KeywordMap keywordMap;
	@Autowired
	private AppUserForbidMap appUserForbidMap;
	@Autowired
	private TransMachineMap transMachineMap;
	
	@Autowired
	private TransLibProcess transLibProcess;
	@Autowired
	private TransDiffProcess transDiffProcess;

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 翻译
	 * @param from 源语言
	 * @param to 目标语言
	 * @param src 原文
	 * @param userId 注册用户编号
	 * @param uuid 用户标识
	 * @param ip 请求IP
	 * @param lon 经度
	 * @param lat 纬度
	 * @param platform 平台
	 * @param platformVer 平台版本
	 * @return
	 */
	public TransResult trans(TransBean bean, String serviceType, String platform, String version, String ip){
		
		TransResultV2 transBeanV2 = transV2(bean, serviceType, platform, version, ip);
		TransDstBean transDstBean = transBeanV2.getDsts().get(0);
		
		TransResult transBean = new TransResult();
		transBean.setFrom(transBeanV2.getFrom());
		transBean.setSrc(transBeanV2.getSrc());
		transBean.setTo(transBeanV2.getTo());
		transBean.setDst(transDstBean.getDst());
		transBean.setSimilar(transDstBean.getSimilar());
		transBean.setProxyid(transDstBean.getProxyId());
		transBean.setTtsSrc(transDstBean.getTtsSrc());
		transBean.setTtsText(transDstBean.getTtsText());
		transBean.setKeywords(transBeanV2.getKeywords());
		
		return transBean;
	}
	
	/**
	 * 翻译
	 * @param from 源语言
	 * @param to 目标语言
	 * @param src 原文
	 * @return
	 */
	public TransResult trans(String from, String to, String src){
		TransBean bean = new TransBean();
		bean.setFrom(from);
		bean.setTo(to);
		bean.setSrc(src);
		bean.setUuid(SystemConstants.OPERATOR_DEFAULT);
		return trans(bean, null, DictConstants.DICT_PLATFORM_JAVA, null, null);
	}
	
	/**
	 * 翻译
	 * 直译：多通道比较相似度
	 * 中转：多通道时，同一通道中转优先
	 * @param from 源语言
	 * @param to 目标语言
	 * @param src 原文
	 * @param userId 注册用户编号
	 * @param uuid 用户标识
	 * @param ip 请求IP
	 * @param lon 经度
	 * @param lat 纬度
	 * @param platform 平台
	 * @param version 平台版本
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public TransResultV2 transV2(TransBean bean, String serviceType, String platform, String version, String ip){
		//原文emoji表情和空格处理
		String src = StringUtil.clearByte4(bean.getSrc().trim());
		String from = bean.getFrom();
		String to = bean.getTo();
		
		TransResultV2 result = new TransResultV2();
		result.setFrom(from);
		result.setSrc(src);
		result.setTo(to);
		
		StringBuilder sbLog = new StringBuilder().append(from).append("->").append(to).append("，").append(src);
		Date begin = new Date();
		boolean hitCache = false;
		boolean valid = true;
		
		//语言校验
		src = StringUtil.clearPuncLast(src);		
		if(StringUtil.isBlankOrNull(src)
				|| !transCapMap.contains(from) 
				|| !transCapMap.contains(to)){
			valid = false;
		}
		
		//检索
		SearchThrd searchThrd = null;
		Thread thrd  = null;
		if(valid){
			searchThrd = new SearchThrd(this, from, src, bean.getLon(), bean.getLat());
			thrd = new Thread(searchThrd);
			thrd.start();
		}
		
		boolean transSuccess = false;	//翻译结果
		String resultDesc = SystemConstants.SUCCESS;
		
		if(StringUtil.notBlankAndNull(bean.getTmcode())
				&& !transMachineMap.existCode(bean.getTmcode())){
			result.getDsts().add(new TransDstBean("熊美丽翻译机序列号无效"));
			transSuccess = true;
		}
		
		//无效语言或同语言翻译或被禁止用户直接返回原文
		if(!valid || from.equals(to) || appUserForbidMap.isForbid(bean.getUid(), bean.getUuid())){
			result.getDsts().add(new TransDstBean(src));
			transSuccess = true;
		}		
		
		//缓存读取
		if(!transSuccess){
			List<TransDstBean> libs = transLibProcess.findDst(from, to, src);
			if(!libs.isEmpty()){
				result.setDsts(libs);
				resultDesc = SystemConstants.CACHE;
				sbLog.append(" ").append(resultDesc);
				hitCache = true;
				transSuccess = true;
			}
		}

		//代理翻译
		if(!transSuccess){
			TransDstBean dstBean = transByProxy(from, to, src, null);
			if(dstBean==null){
				result.getDsts().add(new TransDstBean(src));
				resultDesc = SystemConstants.ALLFAIL;
				sbLog.append(" ").append(resultDesc);
			}else{
				result.getDsts().add(dstBean);
				transSuccess = true;
				
				//翻译差异
				transDiffProcess.addDiff(from, to, src, dstBean.getDst(), dstBean.getSimilar(), dstBean.getProxyId()
						, bean.getSid(), bean.getUid(), bean.getUuid(), bean.getPushRegid()
						, platform, version, serviceType, bean.getTransModel());
			}
		}
		
		TransDstBean dstBean = result.getDsts().get(0);		

		//短文快译订单
		/*
		OrderCreateResult order = transTextProcess.addTextFromTrans(bean, dstBean.getDst(), dstBean.getSimilar(), serviceType, platform, version);
		if(order!=null){
			result.setOrderId(order.getOrderId());
			result.setDealStatus(order.getDealStatus());
		}
		*/
		
		//保存翻译日志
		TransLogThrd.start(bean.getUid(), bean.getPushRegid(), bean.getUuid(), bean.getTmcode(), bean.getTransType(), bean.getSid(), dstBean.getProxyId()
				, from, result.getSrc(), to, dstBean.getDst(), dstBean.getSimilar()
				, transSuccess, hitCache, resultDesc, begin, new Date()
				, ip, bean.getLon(), bean.getLat(), platform, version, serviceType);
		
		//打印日志
		if(!transSuccess){
			logger.error(sbLog);
		}

		//等待线程完成
		if(thrd!=null && searchThrd!=null){
			try {
				thrd.join();
				List<TransTermBean> terms = (List<TransTermBean>) searchThrd.bean;
				if(terms!=null){
					result.setKeywords(terms);
				}
			} catch (InterruptedException e) {
				logger.error("等待线程完成失败：" + e.getMessage());
			}
		}
				
		return result;
	}
	
	/**
	 * 第三方翻译翻译
	 * 直译：多通道比较相似度
	 * 中转：多通道时，同一通道中转优先
	 * @param from
	 * @param to
	 * @param src
	 * @return 翻译失败返回null
	 */
	public TransDstBean transByProxy(String from, String to, String src, Long proxyId){
		//可用通道
		List<Long> proxys = null;
		List<TransferProxyBean> transferProxys = null;
		if(proxyId!=null){
			proxys = new ArrayList<Long>();
			proxys.add(proxyId);
		}else{
			proxys = transCapMap.findProxy(from, to);
			transferProxys = transCapMap.findTransferProxy(from, to);
			if(proxys==null && transferProxys==null){
				SystemMonitorNotifyThrd.start(new StringBuilder().append(from).append("->").append(to).append(SystemConstants.NOPROXY).toString(), null);
				return null;
			}
		}
		
		List<TransDstBean> dsts = new ArrayList<TransDstBean>();
		TransDstBean bean = null;
		
		//直译逻辑
		if(proxys!=null){
			String proxyDst = null;		//代理翻译结果
			String backTrans = null;	//回译结果
			String proxyName = null;
			
			boolean isBackTrans = false;
			if(TransConfig.BACKTRANS_ON==2){
				if(proxys.size()>1){
					isBackTrans = true;
				}
			}else if(TransConfig.BACKTRANS_ON==1){
				isBackTrans = true;
			}
			
			for (Long pid : proxys) {
				AbstractTransProxy proxy = (AbstractTransProxy) proxyMgr.getTransProxy(pid);
				if(proxy==null) continue;
				proxyName = proxyMap.getProxyName(pid);
				try {
					proxyDst = proxy.trans(from, to, src);
					if(StringUtil.isBlankOrNull(proxyDst)){
						continue;
					}
					
					bean = new TransDstBean(proxyDst);
					bean.setProxyId(pid);
					if(isBackTrans){
						backTrans = proxy.trans(to, from, proxyDst);
						bean.setSimilar(TransUtil.calSimilarity(src, backTrans));
					}
					dsts.add(bean);
					
					if(!isBackTrans || bean.getSimilar()>=TransConfig.TRANS_SIMILAR_MIN) break;	//回译相似度高于指定值，直接采用该翻译结果
				}catch (Exception e) {
					logger.error(proxyName+"翻译出错", e);
				}
			}
			
		}
		
		//中转翻译逻辑
		if(dsts.isEmpty() && transferProxys!=null){
			String transferSrc = null;
			String transferDst = null;
			List<TransDstBean> transfers = null;
			proxyId = null;
			for (TransferProxyBean transferProxy : transferProxys) {
				transferSrc = src;
				transferDst = null;
				try{
					for (TransProxyBean proxyBean : transferProxy.getProxys()) {
						proxyId = proxyBean.getProxyId();
						transfers = transLibProcess.findDst(from, to, src);
						if(!transfers.isEmpty()){
							transferDst = transfers.get(0).getDst();
						}
						if(StringUtil.isBlankOrNull(transferDst)){
							AbstractTransProxy proxy = (AbstractTransProxy) proxyMgr.getTransProxy(proxyBean.getProxyId());
							if(proxy==null) {
								transferDst = null;
								break;
							}
							transferDst = proxy.trans(proxyBean.getFrom(), proxyBean.getTo(), transferSrc);
						}
						transferSrc = transferDst; 
					}
				} catch (Exception e) {	}
				if(StringUtil.notBlankAndNull(transferDst)){
					bean = new TransDstBean(transferDst);
					bean.setProxyId(proxyId);
					dsts.add(new TransDstBean(transferDst));
					logger.info(new StringBuilder("中转翻译：").append(from).append("->").append(to).append("：").append(src));
				}
			}
		}
		
		if(!dsts.isEmpty()){
			//按相似度排序
			if(dsts.size()>1){
				Collections.sort(dsts, new TransResultComparator());
			}
			TransDstBean result = dsts.get(0);
			
			//缓存
			transResultMap.putDst(from, to, src, result.getDst(), result.getSimilar());
			
			return result;
		}
		return null;
	}
	
	/**
	 * 匹配关键字
	 * @param lang
	 * @param content
	 * @param lon
	 * @param lat
	 * @return
	 */
	public List<TransTermBean> matchTerm(String lang, String content, String lon, String lat){
		List<TransTermBean> result = new ArrayList<TransTermBean>();

		//关键词
		List<CmcKeyword> keywords = keywordMap.matchKeyword(content, lang);
		CmcKeywordType type = null;
		TransTermBean bean = null;
		for (CmcKeyword keywordBean : keywords) {
			try {
				type = keywordMap.getType(keywordBean.getTypeid());
				if(type!=null){
					bean = new TransTermBean();
					bean.setTypeId(type.getTypeid());
					bean.setTypeIcon(type.getIcon());
					bean.setKey(keywordBean.getKeyword());
					if(StringUtil.notBlankAndNull(keywordBean.getUrl())){
						bean.setUrl(keywordBean.getUrl());
					}else if(StringUtil.notBlankAndNull(type.getTypeurl())){
						bean.setUrl(type.getTypeurl()+URLEncoder.encode(keywordBean.getKeyword(), "utf-8"));
					}
					result.add(bean);
				}
			} catch (Exception e) {}
		}

		return result;
	}
}
