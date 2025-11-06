package com.qcmz.cmc.thrd;

import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.MailUtil;

/**
 * 类说明：发送邮件
 * 修改历史：
 */
public class SimpleMailThrd extends AbstractThrd {
	private String[] mails;
	private String subject;
	private String content;
	
	
	public SimpleMailThrd(String[] mails, String subject, String content) {
		super();
		this.mails = mails;
		this.subject = subject;
		this.content = content;
	}

	/** 
	 * 发送邮件
	 * 修改历史：
	 */
	@Override
	protected void work() {
		try {
			MailUtil.sendSimpleMail(mails, null, subject, content);
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}
	}

	public static void start(String[] mails, String subject, String content){
		SimpleMailThrd mailThrd = new SimpleMailThrd(mails, subject, content);
		new Thread(mailThrd).start();
	}
	
	public static void start(String[] mails, String subject){
		start(mails, subject, subject);
	}
}
