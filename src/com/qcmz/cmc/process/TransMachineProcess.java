package com.qcmz.cmc.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.TransMachineMap;
import com.qcmz.cmc.ws.provide.vo.TmBean;
import com.qcmz.framework.util.StringUtil;

/**
 * 翻译机处理
 */
@Component
public class TransMachineProcess {
	@Autowired
	private TransMachineMap transMachineMap;
	
	/**
	 * 获取翻译机信息
	 * @param tmcode
	 * @return
	 */
	public TmBean getTm(String tmcode){
		TmBean result = new TmBean();
		result.setTmcode(tmcode);
		
		if(StringUtil.isBlankOrNull(tmcode) || !transMachineMap.existCode(tmcode)){
			return result;
		}
		
		result.setStatus(1);
		result.setLastVersion(transMachineMap.getLastVersion());
		
		return result;
	}
}
