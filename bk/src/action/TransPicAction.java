package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcPtPic;
import com.qcmz.cmc.process.OrderProcess;
import com.qcmz.cmc.process.TransPicProcess;
import com.qcmz.cmc.service.ITransPicService;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.vo.TransPicIPictureBean;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.JsonUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

/**
 * 类说明：图片翻译
 * 修改历史：
 */
public class TransPicAction extends BaseAction {
	@Autowired
	private ITransPicService transPicService;
	@Autowired
	private TransPicProcess transPicProcess;
	@Autowired
	private OrderProcess orderProcess;
	
	//返回分页
	private PageBean pageBean;
	/**
	 * 图片编号
	 */
	private String picId;
	/**
	 * 图片信息
	 */
	private CmcPtPic entity;
	/**
	 * 标注信息json串
	 */
	private String ipictureJson;
	
	/**
	 * 页面初始化
	 */
	public String doInit(){
		return QUERY;
	}
	
	/**
	 * 查询
	 * @return
	 * 修改历史：
	 */
	public String doQuery(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		transPicService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}
	
	/**
	 * 详细
	 */
	public String doDetail(){
		entity = transPicService.getPicJoinTransLogMsg(picId);
		
		TransPicIPictureBean ipicture = new TransPicIPictureBean();
		ipicture.setPicture1(BeanConvertUtil.toTransPicIPictureTransBean(entity.getTrans()));
		ipictureJson = JsonUtil.object2String(ipicture);
		
		return DETAIL;
	}
	
	/**
	 * 删除
	 */
	public String doDelete(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), IPUtil.getRemoteAddr(request));
		
		try {
			//删除
			transPicService.delPic(picId, oper);
		} catch(DataException ae){
			handleResult = false;
			setResult(ae.getMessage());
		} catch (Exception e) {
			logger.error("删除信息失败",e);
			handleResult = false;
			setResult("删除信息失败");
		}
		
		//返回
		print();
		
		return null;
	}
	
	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public CmcPtPic getEntity() {
		return entity;
	}

	public void setEntity(CmcPtPic entity) {
		this.entity = entity;
	}

	public String getIpictureJson() {
		return ipictureJson;
	}

	public void setIpictureJson(String ipictureJson) {
		this.ipictureJson = ipictureJson;
	}
}