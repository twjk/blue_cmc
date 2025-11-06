package com.qcmz.cmc.vo;

public class CrowdTaskLibLackBean {
	/**
	 * 题库编号
	 */
	private Long libId;
	/**
	 * 题库名称
	 */
	private String libName;
	/**
	 * 空闲题目数
	 */
	private Long idleSubjectNum;
	
	public CrowdTaskLibLackBean() {
		super();
	}
	
	public CrowdTaskLibLackBean(Long libId, String libName, Long idleSubjectNum) {
		super();
		this.libId = libId;
		this.libName = libName;
		this.idleSubjectNum = idleSubjectNum;
	}
	
	public Long getLibId() {
		return libId;
	}
	public void setLibId(Long libId) {
		this.libId = libId;
	}
	public String getLibName() {
		return libName;
	}
	public void setLibName(String libName) {
		this.libName = libName;
	}
	public Long getIdleSubjectNum() {
		return idleSubjectNum;
	}
	public void setIdleSubjectNum(Long idleSubjectNum) {
		this.idleSubjectNum = idleSubjectNum;
	}
}
