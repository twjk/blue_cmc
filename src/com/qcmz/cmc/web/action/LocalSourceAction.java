package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.cache.LocalSourceMap;
import com.qcmz.cmc.constant.Constant;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.util.StringUtil;

public class LocalSourceAction extends BaseAction {
	@Autowired
	private LocalSourceMap localSourceMap;

	
	private Long id;
	/**
	 * 查询关键词
	 */
	private String key;
	
	
	/**
	 * 查询来源
	 * @return
	 */
	public String findSource(){
		if(key!=null) key = key.trim();
		
		String recentSourceIds = getCookie(Constant.COOKIENAME_SOURCEIDS_RECENT);
		jsonObj = localSourceMap.findSource(key, StringUtil.split2LongList(recentSourceIds, ","));
		
		return JSON;
	}
	
	/**
	 * 获取来源
	 * @return
	 */
	public String getSource(){
		if(id!=null){
			jsonObj = localSourceMap.getSource(id);
		}else if(StringUtil.notBlankAndNull(key)){
			jsonObj = localSourceMap.getSource(key);
		}
		return JSON;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
