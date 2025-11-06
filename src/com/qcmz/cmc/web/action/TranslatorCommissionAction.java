package com.qcmz.cmc.web.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.service.ITranslatorCommissionService;
import com.qcmz.cmc.vo.TranslatorCommissionBean;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.util.DateUtil;

/**
 * 类说明：译员结算
 * 修改历史：
 */
public class TranslatorCommissionAction extends BaseAction {
	@Autowired
	private ITranslatorCommissionService translatorCommissionService;
	
	/**
	 * 开始日期
	 */
	private Date start;
	/**
	 * 结束日期
	 */
	private Date end;
	/**
	 * 操作员用户名
	 */
	private String operCd;
	
	private List<TranslatorCommissionBean> translatorCommissionList;
	
	private TranslatorCommissionBean translatorCommissionBean;
	
	/**
	 * 页面初始化
	 */
	public String doInit(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH,1);		
		start = c.getTime();
		end = DateUtil.getSysDateOnly();
		return QUERY;
	}
	
	/**
	 * 查询
	 * @return
	 * 修改历史：
	 */
	public String doQuery(){
		if(start!=null && end!=null 
				&& DateUtil.betweenDay(start, end)<=60){
			translatorCommissionList = translatorCommissionService.findCommission(start, end, operCd);
		}
		return LIST;
	}

	/**
	 * 查询操作员的结算明细
	 * @return
	 * 修改历史：
	 */
	public String doDetail(){
		translatorCommissionBean = translatorCommissionService.getCommission(start, end, operCd);
		return DETAIL;
	}
	
	/**
	 * 导出
	 * @return
	 */
	public String doExport(){
		
		return null;
	}
	
	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getOperCd() {
		return operCd;
	}

	public void setOperCd(String operCd) {
		this.operCd = operCd;
	}

	public List<TranslatorCommissionBean> getTranslatorCommissionList() {
		return translatorCommissionList;
	}

	public void setTranslatorCommissionList(List<TranslatorCommissionBean> translatorCommissionList) {
		this.translatorCommissionList = translatorCommissionList;
	}

	public TranslatorCommissionBean getTranslatorCommissionBean() {
		return translatorCommissionBean;
	}

	public void setTranslatorCommissionBean(TranslatorCommissionBean translatorCommissionBean) {
		this.translatorCommissionBean = translatorCommissionBean;
	}
}
