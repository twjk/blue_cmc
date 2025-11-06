package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.CatMap;
import com.qcmz.cmc.process.JobArticleProcess;
import com.qcmz.cmc.util.VersionUtil;
import com.qcmz.cmc.ws.provide.vo.CatQueryResp;
import com.qcmz.cmc.ws.provide.vo.JobArticleQueryReq;
import com.qcmz.cmc.ws.provide.vo.JobArticleQueryResp;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Request;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 就业信息相关接口
 */
@Component
public class JobArticleInterface extends BaseInterface {
	@Autowired
	private JobArticleProcess jobArticleProcess;
	@Autowired
	private CatMap catMap;
	
	/**
	 * 获取分类列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public CatQueryResp findCat(Request req, String interfaceType, String reqIp){
		CatQueryResp resp = new CatQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			if(resp.successed()){}
			
			//处理
			if(resp.successed()){
				resp.setResult(catMap.findCat(DictConstants.CATTYPE_JOBARTICLE));
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 分页获取就业信息
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public JobArticleQueryResp findArticle(JobArticleQueryReq req, String pageSize, String interfaceType, String reqIp){
		JobArticleQueryResp resp = new JobArticleQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
//			if(resp.successed()){}
			
			//处理
			if(resp.successed()){
				String serviceType = SrmClient.getServiceType(req.getAuthAccount());
				String platform = SrmClient.getPlatform(req.getAuthAccount());
				boolean encrypt = VersionUtil.isEncryptJob(serviceType, platform, req.getAppVer());
				
				PageBean pageBean = new PageBean("1", pageSize);
				resp.setResult(jobArticleProcess.findArticle(req.getBean(), pageBean.getPageSize(), encrypt));
			}
			
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
}
