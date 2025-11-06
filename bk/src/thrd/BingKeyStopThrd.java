package com.qcmz.cmc.thrd;

import com.qcmz.cmc.process.ProxyAccountProcess;
import com.qcmz.framework.thrd.AbstractThrd;

/**
 * 类说明：停用APIKEY
 * 修改历史：
 */
public class BingKeyStopThrd extends AbstractThrd {
	
	private ProxyAccountProcess proxyAccountProcess;
	private String apiKey;
	
	public BingKeyStopThrd() {
		super();
	}

	public BingKeyStopThrd(ProxyAccountProcess proxyAccountProcess, String apiKey) {
		super();
		this.proxyAccountProcess = proxyAccountProcess;
		this.apiKey = apiKey;
	}

	/** 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		proxyAccountProcess.stopBingTransKey(apiKey);
	}

	public static void start(ProxyAccountProcess apikeyProcess, String apiKey){
		BingKeyStopThrd biyingKeyOffThrd = new BingKeyStopThrd(apikeyProcess, apiKey);
		Thread thrd = new Thread(biyingKeyOffThrd);
		thrd.start();
	}
	
}
