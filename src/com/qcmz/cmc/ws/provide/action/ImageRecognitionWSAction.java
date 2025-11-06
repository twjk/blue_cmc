package com.qcmz.cmc.ws.provide.action;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.ImageRecognitionInterface;
import com.qcmz.cmc.ws.provide.vo.ImageRecognitionQueryReq;
import com.qcmz.cmc.ws.provide.vo.ImageRecognizeReq;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.framework.ws.vo.Request;

public class ImageRecognitionWSAction extends BaseWSAction {
	@Autowired
	private ImageRecognitionInterface imageRecognitionInterface;
	
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 分类编号
	 */
	private Long catId;
	/**
	 * 图像文件
	 */
	private File file;
	/**
	 * 每页记录数
	 */
	private Integer pageSize;
	/**
	 * 上一页最后一条图像编号
	 */
	private Long lastId;
	
	/**
	 * 获取分类列表
	 */
	public String findCat(){
		Request req = new Request();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		jsonObj = imageRecognitionInterface.findCat(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 图像识别
	 */
	public String recognize(){
		ImageRecognizeReq req = new ImageRecognizeReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setCatId(catId);
		req.getBean().setFile(file);
		
		jsonObj = imageRecognitionInterface.recognize(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取用户图像识别结果列表
	 */
	public String findImage(){
		ImageRecognitionQueryReq req = new ImageRecognitionQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setPageSize(pageSize);
		req.setLastId(lastId);
		
		jsonObj = imageRecognitionInterface.findImage(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getCatId() {
		return catId;
	}

	public void setCatId(Long catId) {
		this.catId = catId;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getLastId() {
		return lastId;
	}

	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}
}
