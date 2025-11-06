package com.qcmz.cmc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * CmcWalletAccount entity. @author MyEclipse Persistence Tools
 */

public class CmcWalletAccount implements java.io.Serializable {

	// Fields

	private Long userid;
	private String servicetype;
	private Double balance = 0.0;
	private Double incomeamount = 0.0;
	private Double expensesamount = 0.0;
	private Date createtime;
	
	private List<CmcWalletBill> bills = new ArrayList<CmcWalletBill>();
	private UserSimpleBean user;
	
	
	// Constructors

	/** default constructor */
	public CmcWalletAccount() {
	}

	// Property accessors

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getServicetype() {
		return servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getIncomeamount() {
		return incomeamount;
	}

	public void setIncomeamount(Double incomeamount) {
		this.incomeamount = incomeamount;
	}
	
	public Double getExpensesamount() {
		return expensesamount;
	}

	public void setExpensesamount(Double expensesamount) {
		this.expensesamount = expensesamount;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public List<CmcWalletBill> getBills() {
		return bills;
	}

	public void setBills(List<CmcWalletBill> bills) {
		this.bills = bills;
	}

	public UserSimpleBean getUser() {
		return user;
	}

	public void setUser(UserSimpleBean user) {
		this.user = user;
	}
}