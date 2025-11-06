package com.qcmz.cmc.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.entity.CmcTransExample;
import com.qcmz.cmc.entity.CmcTransLibrary;
import com.qcmz.cmc.proxy.trans.BaiduTransExampleProxy;
import com.qcmz.cmc.service.ITransExampleService;
import com.qcmz.cmc.service.ITransLibService;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：翻译例句处理
 * 修改历史：
 */
@Component
public class TransExampleProcess {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ITransLibService transLibService;
	@Autowired
	private ITransExampleService transExampleService;
	@Autowired
	private BaiduTransExampleProxy baiduTransWebProxy;
	
	
	@SuppressWarnings("unchecked")
	public void spiderExample(){
		logger.info("开始获取例句");
		int page = 0;
		int pageSize = 50;
		long pageCount = 0;
		PageBean pageBean = null;
		List<CmcTransLibrary> libs = null;
		List<CmcTransExample> list = null; 
		
		String from = "zh-cn";
		String to = "en";
		Map<String, String> params = new HashMap<String, String>();
		params.put("fromlang", from);
		params.put("tolang", to);
		
		do {
			page++;
			pageBean = new PageBean(page, pageSize);
			transLibService.queryByMapTerm(params, pageBean);
			
			libs = (List<CmcTransLibrary>) pageBean.getResultList();
			if(libs==null || libs.isEmpty()) break;
			
			for (CmcTransLibrary lib : libs) {
				if(lib.getSrc().equalsIgnoreCase(lib.getDst())) continue;
				list = baiduTransWebProxy.spiderExample(from, to, lib.getSrc());
				if(!list.isEmpty()){
					transExampleService.saveOrUpdateAll(list);
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
			
			pageCount = pageBean.getPageCount();
			logger.info(new StringBuilder("完成获取例句：第").append(page).append("页，共").append(pageCount).append("页，合计").append(+pageBean.getTotal()).append("条"));
		} while (page<pageCount);
		logger.info("完成获取例句");
	}
}
