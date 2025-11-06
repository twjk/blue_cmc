package com.qcmz.cmc.ws.provide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.ImageRecognitionCatMap;
import com.qcmz.cmc.process.ImageRecognitionProcess;
import com.qcmz.cmc.service.IImageRecognitionService;
import com.qcmz.cmc.ws.provide.vo.ImageRecognitionCatQueryResp;
import com.qcmz.cmc.ws.provide.vo.ImageRecognitionQueryReq;
import com.qcmz.cmc.ws.provide.vo.ImageRecognitionQueryResp;
import com.qcmz.cmc.ws.provide.vo.ImageRecognizeReq;
import com.qcmz.cmc.ws.provide.vo.ImageRecognizeResp;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.ws.BaseInterface;
import com.qcmz.framework.ws.vo.Request;
import com.qcmz.srm.client.util.SrmClient;

/**
 * 图像识别接口
 */
@Component
public class ImageRecognitionInterface extends BaseInterface {
	@Autowired
	private IImageRecognitionService imageRecognitionService;
	@Autowired
	private ImageRecognitionCatMap imageRecognitionCatMap;
	@Autowired
	private ImageRecognitionProcess imageRecognitionProcess;
	
	/**
	 * 获取图像识别分类列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public ImageRecognitionCatQueryResp findCat(Request req, String interfaceType, String reqIp){
		ImageRecognitionCatQueryResp resp = new ImageRecognitionCatQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className);
			
			//数据验证
			if(resp.successed()){}
			
			//处理
			if(resp.successed()){
				resp.setResult(imageRecognitionCatMap.findValid());
			}
		}catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}
		
		return resp;
	}
	
	/**
	 * 分页获取用户图像识别结果列表
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public ImageRecognitionQueryResp findImage(ImageRecognitionQueryReq req, String interfaceType, String reqIp){
		ImageRecognitionQueryResp resp = new ImageRecognitionQueryResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(imageRecognitionService.findImageInfo(req.getUid(), PageBean.correctPageSize(req.getPageSize()), req.getLastId()));
			}
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	} 
	
	/**
	 * 图像识别
	 * @param req
	 * @param interfaceType
	 * @param reqIp
	 * @return
	 */
	public ImageRecognizeResp recognize(ImageRecognizeReq req, String interfaceType, String reqIp){
		ImageRecognizeResp resp = new ImageRecognizeResp();
		try {
			//身份验证
			SrmClient.validAccount(req, resp, className, req.getBean().getUid());
			
			//数据校验
			if(resp.successed()){
				if(req.getBean().getUid()==null){
					resp.errorParam(ExceptionConstants.MSG_USERID_EMPTY);
				}else if(req.getBean().getCatId()==null){
					resp.errorParam("分类为空");
				}else if(req.getBean().getFile()==null){
					resp.errorParam("图像文件为空");
				}
			}
			
			//处理
			if(resp.successed()){
				resp.setResult(imageRecognitionProcess.recognize(req.getBean()));
			}
		} catch (DataException e) {
			resp.errorData(e.getMessage());
		} catch (ParamException e) {
			resp.errorParam(e.getMessage());
		} catch (Exception e) {
			resp.error();
			logger.error("访问接口失败", e);
		}

		return resp;
	}
}
