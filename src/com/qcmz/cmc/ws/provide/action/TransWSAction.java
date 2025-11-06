package com.qcmz.cmc.ws.provide.action;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.ws.provide.service.TransConfigInterface;
import com.qcmz.cmc.ws.provide.service.TransInterface;
import com.qcmz.cmc.ws.provide.vo.TransCorrectQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransLogReq;
import com.qcmz.cmc.ws.provide.vo.TransPermitReq;
import com.qcmz.cmc.ws.provide.vo.TransPermitResp;
import com.qcmz.cmc.ws.provide.vo.TransReq;
import com.qcmz.cmc.ws.provide.vo.TransUserCorrectReq;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：翻译接口
 * 修改历史：
 */
public class TransWSAction extends BaseWSAction {
	@Autowired
	private TransInterface transInterface;
	@Autowired
	private TransConfigInterface transConfigInterface;
	
	/**
	 * 机器码
	 */
	private String token;
	/**
	 * 源语言
	 */
	private String srcLang;
	
	private String source;
	private String tarLang;
	private String target;
	
	/**
	 * 注册用户编号
	 */
	private String uid;
	/**
	 * 用户标识
	 */
	private String uuid;
	/**
	 * 推送注册编号
	 */
	private String pushRegid;
	/**
	 * 翻译机编号
	 */
	private String tmcode;
	/**
	 * 翻译类型
	 */
	private String transType;
	/**
	 * 翻译模式
	 */
	private Integer transModel;
	/**
	 * 渠道
	 */
	private String channel;
	/**
	 * 会话标识
	 */
	private String sid;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 待翻译内容
	 */
	private String q;
	/**
	 * 原文
	 */
	private String src;
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 译文
	 */
	private String mtDst;
	/**
	 * 代理编号
	 */
	private Long proxyId;
	/**
	 * 开始时间
	 */
	private Long startTime;
	/**
	 * 结束时间
	 */
	private Long endTime;
	/**
	 * 当前页数
	 */
	private String page;
	/**
	 * 每页记录数
	 */
	private String pageSize;
	/**
	 * 经度
	 */
	private String lon;
	/**
	 * 纬度
	 */
	private String lat;
	
	//获取语音帐户
	@Deprecated
	public String findSpeechAccount(){
		Request req = new Request();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		jsonObj = transConfigInterface.findSpeechAccount(req, interfaceType, getRemoteAddr());
		return JSON;
	}
	
	//校验翻译许可
	@Deprecated
	public String canTrans(){
		TransPermitReq req = new TransPermitReq();
		req.setToken(token);
		req.setLang(srcLang);
		
		TransPermitResp resp = new TransPermitResp();
		resp.setPermit(1);
		
		jsonObj = resp;
		return JSON;
	}
	
	/**
	 * 获取翻译语言列表 
	 */
	public String findLang(){
		Request req = new Request();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		jsonObj = transInterface.findLang(req, interfaceType, getRemoteAddr());
		return JSON;
	}
	
	//翻译
	@Deprecated
	public String trans(){
		TransReq req = new TransReq();
		req.getBean().setFrom(from);
		req.getBean().setTo(to);
		req.getBean().setUuid(uid);
		req.getBean().setSrc(q==null?q:q.trim());
		
		jsonObj = transInterface.trans(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	//翻译
	public String translate(){
		TransReq req = new TransReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.getBean().setFrom(from);
		req.getBean().setTo(to);
		if(NumberUtil.isNumber(uid)){
			req.getBean().setUid(Long.valueOf(uid));
		}
		req.getBean().setUuid(uuid);
		req.getBean().setTmcode(tmcode);
		req.getBean().setTransType(transType);
		req.getBean().setSid(sid);
		req.getBean().setSrc(q);
		req.getBean().setLon(lon);
		req.getBean().setLat(lat);
		
		jsonObj = transInterface.translate(req, interfaceType, getRemoteAddr());
		return JSON;
	}
	
	//翻译
	public String translateV2(){
		TransReq req = new TransReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.getBean().setFrom(from);
		req.getBean().setTo(to);
		if(NumberUtil.isNumber(uid)){
			req.getBean().setUid(Long.valueOf(uid));
		}
		req.getBean().setUuid(uuid);
		req.getBean().setPushRegid(pushRegid);
		req.getBean().setTmcode(tmcode);
		req.getBean().setTransType(transType);
		req.getBean().setTransModel(transModel==null?DictConstant.DICT_TRANSMODEL_FREE:transModel);
		req.getBean().setChannel(channel);
		req.getBean().setSid(sid);
		req.getBean().setSrc(q);
		req.getBean().setLon(lon);
		req.getBean().setLat(lat);
		
		jsonObj = transInterface.translateV2(req, interfaceType, getRemoteAddr());
		return JSON;
	}
	
	/**
	 * 保存翻译日志
	 */
	public String saveTransLog(){
		TransLogReq req = new TransLogReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setFrom(from);
		req.getBean().setSrc(src);
		req.getBean().setTo(to);
		req.getBean().setDst(dst);
		req.getBean().setProxyId(proxyId);
		req.getBean().setUid(StringUtil.isBlankOrNull(uid)?null:Long.valueOf(uid));
		req.getBean().setPushRegid(pushRegid);
		req.getBean().setUuid(uuid);
		req.getBean().setSid(sid);
		req.getBean().setTmcode(tmcode);
		req.getBean().setTransType(transType);
		req.getBean().setLon(lon);
		req.getBean().setLat(lat);
		req.getBean().setStartTime(startTime==null?null:new Date(startTime));
		req.getBean().setEndTime(endTime==null?null:new Date(endTime));
		jsonObj = transInterface.saveTransLog(req, interfaceType, getRemoteAddr());
		return JSON;
	}
	
	/**
	 * 用户纠错
	 */
	public String userCorrect(){
		TransUserCorrectReq req = new TransUserCorrectReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		if(NumberUtil.isNumber(uid)){
			req.getBean().setUid(Long.valueOf(uid));
		}
		req.getBean().setUuid(uuid);
		req.getBean().setPushRegid(pushRegid);
		
		req.getBean().setSid(sid);
		req.getBean().setFrom(from);
		req.getBean().setSrc(src);
		req.getBean().setTo(to);
		req.getBean().setDst(dst);
		req.getBean().setMtDst(mtDst);
		
		
		jsonObj = transInterface.userCorrect(req, interfaceType, getRemoteAddr());
		return JSON;
	}
	
	/**
	 * 获取修正翻译列表 
	 */
	public String findCorrectTrans(){
		TransCorrectQueryReq req = new TransCorrectQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUuid(uuid);
		
		jsonObj = transInterface.findCorrectTrans(req, interfaceType, getRemoteAddr());		
		return JSON;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSrcLang() {
		return srcLang;
	}

	public void setSrcLang(String srcLang) {
		this.srcLang = srcLang;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarLang() {
		return tarLang;
	}

	public void setTarLang(String tarLang) {
		this.tarLang = tarLang;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPushRegid() {
		return pushRegid;
	}

	public void setPushRegid(String pushRegid) {
		this.pushRegid = pushRegid;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public Long getProxyId() {
		return proxyId;
	}

	public void setProxyId(Long proxyId) {
		this.proxyId = proxyId;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getTmcode() {
		return tmcode;
	}

	public void setTmcode(String tmcode) {
		this.tmcode = tmcode;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Integer getTransModel() {
		return transModel;
	}

	public void setTransModel(Integer transModel) {
		this.transModel = transModel;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getMtDst() {
		return mtDst;
	}

	public void setMtDst(String mtDst) {
		this.mtDst = mtDst;
	}
}
