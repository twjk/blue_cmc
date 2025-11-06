package com.qcmz.cmc.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcPkgSentence;
import com.qcmz.cmc.entity.CmcPkgTheme;
import com.qcmz.cmc.service.IThemeSentenceService;
import com.qcmz.cmc.service.IThemeService;
import com.qcmz.cmc.service.ITransLibService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataAccessException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.ArrayUtil;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class ThemeSentenceAction extends BaseAction {
	@Autowired
	private IThemeService themeService;
	@Autowired
	private IThemeSentenceService themeSentenceService;
	@Autowired
	private ITransLibService transLibService;
	
	private PageBean pageBean;
	private CmcPkgSentence entity;
	private CmcPkgTheme themeEntity;
	private Long id;
	private Long themeid;
	private String libtype;
	private String from;
	private String to;
	private String key;
	
	
	/**
	 * 页面初始化
	 */
	public String doInit(){
		request.setAttribute("themeQueryString", request.getQueryString());
		return QUERY;
	}
	
	/**
	 * 查询
	 */
	public String doQuery(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		themeSentenceService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}

	/**
	 * 添加/修改初始化
	 */
	public String doEdit(){
		if(id!=null){
			entity = themeSentenceService.getJoinTheme(id);
		}else{
			themeEntity = themeService.load(themeid);
		}
		return EDIT;
	}
	
	/**
	 * 添加/修改
	 */
	public String doSave(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			themeSentenceService.saveOrUpdate(entity, oper);
		} catch(DataAccessException e){
			handleResult = false;
			setResult(e.getMessage());
		} catch (Exception e) {
			logger.error("保存信息失败",e);
			handleResult = false;
			setResult("保存信息失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 批量添加
	 * @return
	 * 修改历史：
	 */
	public String doAddBatch(){
		themeEntity = themeService.load(themeid);
		return "addBatch";
	}
	
	/**
	 * 查询译库
	 * @return
	 * 修改历史：
	 */
	public String doQueryLib(){
		jsonObj = transLibService.findLib4ImportTheme(themeid, libtype, from, to, key);
		return JSON;
	}
	
	/**
	 * 批量导入保存
	 */
	public String doSaveBatch(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			String cat = request.getParameter("cat");
			String[] arrLibid = request.getParameterValues("libid");
			List<Long> libids = ArrayUtil.toLongList(arrLibid);
			if(!libids.isEmpty()){
				themeSentenceService.importFromLib(themeid, cat, libids, oper);
			}
		} catch(DataAccessException e){
			handleResult = false;
			setResult(e.getMessage());
		} catch (Exception e) {
			logger.error("保存信息失败",e);
			handleResult = false;
			setResult("保存信息失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 删除
	 */
	public String doDelete(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), IPUtil.getRemoteAddr(request));
		
		try {
			//删除
			themeSentenceService.delete(id, oper);
		} catch(DataAccessException ae){
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

	public CmcPkgSentence getEntity() {
		return entity;
	}

	public void setEntity(CmcPkgSentence entity) {
		this.entity = entity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getThemeid() {
		return themeid;
	}

	public void setThemeid(Long themeid) {
		this.themeid = themeid;
	}

	public CmcPkgTheme getThemeEntity() {
		return themeEntity;
	}

	public void setThemeEntity(CmcPkgTheme themeEntity) {
		this.themeEntity = themeEntity;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLibtype() {
		return libtype;
	}

	public void setLibtype(String libtype) {
		this.libtype = libtype;
	}
}
