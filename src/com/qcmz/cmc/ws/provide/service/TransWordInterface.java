package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.service.ITransWordService;
import com.qcmz.cmc.ws.provide.vo.CountryResp;
import com.qcmz.cmc.ws.provide.vo.TransWordCountryQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransWordQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransWordResp;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 类说明：分词接口实现
 * 修改历史：
 */
@Component
public class TransWordInterface extends BaseInterface {
	@Autowired
	private ITransWordService transWordService;
	
	/**
	 * 获取国家列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 * 修改历史：
	 */
	public CountryResp findCountry(TransWordCountryQueryReq req, String interfaceType, String reqIp){
		CountryResp resp = new CountryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			
			//处理
			if(resp.successed()){
				resp.setResult(transWordService.findCountry(req.getLangCode()));
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 分页获取词语列表
	 * @param req
	 * @param page
	 * @param pageSize
	 * @param interfaceType
	 * @param reqIp
	 * @return pageBean<TransWordBean>
	 * 修改历史：
	 */
	public TransWordResp findWord(TransWordQueryReq req, String page, String pageSize, String interfaceType, String reqIp){
		TransWordResp resp = new TransWordResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据校验
			
			//处理
			if(resp.successed()){
				PageBean pageBean = new PageBean(page, pageSize, 200);
				resp.setResult(transWordService.findWord(req.getBean(), pageBean.getPage(), pageBean.getPageSize()));
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
}
