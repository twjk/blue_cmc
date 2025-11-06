package com.qcmz.cmc.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.qcmz.cmc.cache.CatMap;
import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.cache.HolidayMap;
import com.qcmz.cmc.job.JobMgr;

/**
 * 功能: 系统启动时加载
 *
 */
public class StartupContextListener extends ContextLoaderListener implements ServletContextListener,java.io.Serializable {

	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		WebApplicationContext webAppContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);

		//定时任务
		((JobMgr) webAppContext.getBean("jobMgr")).updateJob();
		
		//字典初始化
		((DictMap) webAppContext.getBean("dictMap")).refresh();
		
		//分类初始化
		((CatMap) webAppContext.getBean("catMap")).refresh();
		
		//节假日初始化
		((HolidayMap) webAppContext.getBean("holidayMap")).refresh();
	}

	public static void setupContext(ServletContext context){
		
	}
}
