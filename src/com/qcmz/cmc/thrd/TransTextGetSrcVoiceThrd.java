package com.qcmz.cmc.thrd;

import com.qcmz.cmc.service.ITransTextService;
import com.qcmz.dmc.ws.provide.locator.TransLogWS;
import com.qcmz.framework.thrd.AbstractThrd;
import com.qcmz.framework.util.SpringUtil;
import com.qcmz.framework.util.StringUtil;

public class TransTextGetSrcVoiceThrd extends AbstractThrd {
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 会话编号
	 */
	private String sessionId;

	public TransTextGetSrcVoiceThrd(String orderId, String sessionId) {
		super();
		this.orderId = orderId;
		this.sessionId = sessionId;
	}


	@Override
	protected void work() {
		String voice = TransLogWS.getSrcVoice(sessionId);
		if(StringUtil.notBlankAndNull(voice)){
			ITransTextService transTextService = (ITransTextService) SpringUtil.getBean("transTextServiceImpl");
			transTextService.updateVoice(orderId, voice);
		}
	}

	public static void start(String orderId, String sessionId){
		if(StringUtil.notBlankAndNull(sessionId)){
			new Thread(new TransTextGetSrcVoiceThrd(orderId, sessionId)).start();
		}
	}
}
