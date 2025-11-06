package com.qcmz.cmc.entity;

/**
 * CmcRewardActivity entity. @author MyEclipse Persistence Tools
 */

public class CmcRewardActivity implements java.io.Serializable {

	// Fields

	private Long actid;
	private String title;
	/**
	 * 邀请人奖励，2或1-5
	 */
	private String inviteramount;
	/**
	 * 被邀请人奖励，2或1-5
	 */
	private String inviteeamount;
	private Integer partfreq = 0;
	private Integer parttimes = -1;
	private Integer newuser;
	private String description;
	private Integer status;

	// Constructors

	/** default constructor */
	public CmcRewardActivity() {
	}

	// Property accessors

	public Long getActid() {
		return this.actid;
	}

	public void setActid(Long actid) {
		this.actid = actid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInviteramount() {
		return this.inviteramount;
	}

	public void setInviteramount(String inviteramount) {
		this.inviteramount = inviteramount;
	}

	public String getInviteeamount() {
		return this.inviteeamount;
	}

	public void setInviteeamount(String inviteeamount) {
		this.inviteeamount = inviteeamount;
	}

	public Integer getPartfreq() {
		return partfreq;
	}

	public void setPartfreq(Integer partfreq) {
		this.partfreq = partfreq;
	}

	public Integer getParttimes() {
		return this.parttimes;
	}

	public void setParttimes(Integer parttimes) {
		this.parttimes = parttimes;
	}

	public Integer getNewuser() {
		return this.newuser;
	}

	public void setNewuser(Integer newuser) {
		this.newuser = newuser;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}