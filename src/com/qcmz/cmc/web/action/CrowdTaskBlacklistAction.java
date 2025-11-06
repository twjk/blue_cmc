package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.constant.BeanConstant;
import com.qcmz.cmc.process.CacheSynProcess;
import com.qcmz.cmc.process.CrowdTaskBlacklistProcess;
import com.qcmz.cmc.service.ICrowdTaskBlacklistService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

public class CrowdTaskBlacklistAction extends BaseAction {
	@Autowired
	private ICrowdTaskBlacklistService crowdTaskBlacklistService;
	@Autowired
	private CrowdTaskBlacklistProcess crowdTaskBlacklistProcess;
	
	
	private Long userId;
	
	/**
	 * 用户加入黑名单
	 */
	public String doAdd(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			crowdTaskBlacklistProcess.addBlacklist(userId, oper);
			CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_CROWDTASK_BLACKLIST);
		} catch (Exception e) {
			logger.error("用户加入黑名单失败",e);
			handleResult = false;
			setResult("用户加入黑名单失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 用户移出黑名单
	 */
	public String doDelete(){
		try {
			crowdTaskBlacklistService.deleteBlacklist(userId);
			CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_CROWDTASK_BLACKLIST);
		} catch (Exception e) {
			logger.error("用户移出黑名单失败",e);
			handleResult = false;
			setResult("用户移出黑名单失败");
		}
		
		print();
		
		return null;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
