package com.qcmz.cmc.web.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.constant.BeanConstant;
import com.qcmz.cmc.entity.CmcFunction;
import com.qcmz.cmc.entity.CmcProxy;
import com.qcmz.cmc.entity.CmcProxyFunc;
import com.qcmz.cmc.process.CacheSynProcess;
import com.qcmz.cmc.service.IFunctionService;
import com.qcmz.cmc.service.IProxyService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.exception.SystemException;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：代理功能维护
 * 修改历史：
 */
public class ProxyFuncAction extends BaseAction {
	@Autowired
	private IProxyService proxyService;
	@Autowired
	private IFunctionService functionService;
	
	private List<CmcProxy> proxys;
	private List<CmcFunction> funcs;
	private List<String> proxyfuncs = new ArrayList<String>();
	
	public String doEdit(){
		proxys = proxyService.findValid(null);
		funcs = functionService.findAll();
		List<CmcProxyFunc> list = proxyService.findProxyFunc();
		for (CmcProxyFunc cmcProxyFunc : list) {
			proxyfuncs.add(new StringBuilder().append(cmcProxyFunc.getProxyid()).append("|").append(cmcProxyFunc.getFuncid()).toString());
		}
		return EDIT;
	}
	
	public String doSave(){
		try {
			String[] arr = request.getParameterValues("proxyfunc");
			List<CmcProxyFunc> list = new ArrayList<CmcProxyFunc>();
			Long[] arrProxyFun = null; 
			for (String string : arr) {
				arrProxyFun = StringUtil.split2Long(string, "|");
				list.add(new CmcProxyFunc(arrProxyFun[0], arrProxyFun[1]));
			}
			proxyService.saveProxyFunc(list);
			
			CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_FUNCCAP);
		} catch (SystemException e) {
			handleResult = false;
			setResult(e.getMessage());
		} catch (Exception e) {
			handleResult = false;
			setResult("保存失败");
			logger.error("保存失败", e);
		}
		
		print();
		
		return null;
	}

	public List<CmcProxy> getProxys() {
		return proxys;
	}

	public void setProxys(List<CmcProxy> proxys) {
		this.proxys = proxys;
	}

	public List<CmcFunction> getFuncs() {
		return funcs;
	}

	public void setFuncs(List<CmcFunction> funcs) {
		this.funcs = funcs;
	}

	public List<String> getProxyfuncs() {
		return proxyfuncs;
	}

	public void setProxyfuncs(List<String> proxyfuncs) {
		this.proxyfuncs = proxyfuncs;
	}
}
