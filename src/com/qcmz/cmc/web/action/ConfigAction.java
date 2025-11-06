package com.qcmz.cmc.web.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.constant.BeanConstant;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.config.AbstractConfig;
import com.qcmz.framework.job.AbstractJobMgr;
import com.qcmz.framework.util.SpringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class ConfigAction extends BaseAction {
	@Autowired
	private AbstractJobMgr jobMgr;
	
	/**
	 * 配置的Bean id
	 */
	private String beanId;
	/**
	 * 配置信息
	 */
	private Map<String,String> cfgMap;
	
	/**
	 * 保存信息 (Ajax方式调用)
	 */
	public String doSave() {
		try {
			((AbstractConfig)SpringUtil.getBean(beanId)).setPropValueAndFile(cfgMap);
			
			if(BeanConstant.BEANID_CONFIG_JOB.equals(beanId)){
				jobMgr.updateJob();		//定时任务
			}
		} catch (Exception e) {
			logger.error("保存配置失败", e);
			handleResult = false;
			setResult("保存配置失败");
		}

		print();
		return null;
	}

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	public Map<String, String> getCfgMap() {
		return cfgMap;
	}

	public void setCfgMap(Map<String, String> cfgMap) {
		this.cfgMap = cfgMap;
	}
	
}
