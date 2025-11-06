package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.process.TestProcess;
import com.qcmz.framework.action.BaseAction;

public class TestAction extends BaseAction {
	@Autowired
	private TestProcess testProcess;
	
	public String testHcicloudTrans(){
		try {
			testProcess.testHcicloudTrans();
		} catch (Exception e) {
			handleResult = false;
			setResult("Exception");
		}

		print();
		return null;
	}
}
