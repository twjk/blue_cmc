package com.qcmz.cmc.thrd;

import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：系统监控通知
 * 修改历史：
 */
public class SystemMonitorNotifyThrd extends AbstractThrd {
	private String title;
	private String content;
	
	
	public SystemMonitorNotifyThrd(String title, String content) {
		super();
		this.title = title;
		if(StringUtil.isBlankOrNull(content)){
			this.content = title;
		}else{
			this.content = content;
		}
	}

	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		try {
			//邮件
			MailUtil.sendSimpleMail(SystemConfig.MAILS, null, title, content);
			
		} catch (Exception e) {
			logger.error("系统监控通知失败", e);
		}
	}

	public static void start(String title, String content){
		SystemMonitorNotifyThrd transMonitorThrd = new SystemMonitorNotifyThrd(title, content);
		Thread thrd = new Thread(transMonitorThrd);
		thrd.start();
	}
}
