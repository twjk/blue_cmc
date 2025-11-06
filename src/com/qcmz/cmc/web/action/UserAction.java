package com.qcmz.cmc.web.action;

import com.qcmz.framework.action.BaseAction;
import com.qcmz.umc.ws.provide.locator.UserWS;
import com.qcmz.umc.ws.provide.vo.UserBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class UserAction extends BaseAction {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 用户信息
	 */
	private UserBean user;
	
	/**
	 * 
	 * @return
	 * 修改历史：
	 */
	public String showUserInfo(){
		
		user = UserWS.getUser(uid);
		
		return "show";
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}
}
