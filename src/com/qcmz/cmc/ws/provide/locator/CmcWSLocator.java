package com.qcmz.cmc.ws.provide.locator;

import org.apache.log4j.Logger;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;

import com.qcmz.cmc.ws.provide.webservice.IPackageOrderWS;
import com.qcmz.cmc.ws.provide.webservice.IRewardWS;
import com.qcmz.cmc.ws.provide.webservice.ITransConfigWS;
import com.qcmz.cmc.ws.provide.webservice.ITransLibWS;
import com.qcmz.cmc.ws.provide.webservice.ITransWS;

/**
 * 类说明：
 * 修改历史：
 */
public class CmcWSLocator {
	private static Logger logger = Logger.getLogger(CmcWSLocator.class);
	private static XFireProxyFactory factory = new XFireProxyFactory(XFireFactory.newInstance().getXFire());
	
	public static String CMC_SERVER;
	public static String HTTP_TIMEOUT = "6000";
	
	private static Service transConfigServModel = new ObjectServiceFactory().create(ITransConfigWS.class);
	private static ITransConfigWS transConfigWS;
	
	private static Service transServModel = new ObjectServiceFactory().create(ITransWS.class);
	private static ITransWS transWS;
	
	private static Service transLibServModel = new ObjectServiceFactory().create(ITransLibWS.class);
	private static ITransLibWS transLibWS;
	
	private static Service packageOrderServModel = new ObjectServiceFactory().create(IPackageOrderWS.class);
	private static IPackageOrderWS packageOrderWS;
	
	private static Service rewardServModel = new ObjectServiceFactory().create(IRewardWS.class);
	private static IRewardWS rewardWS;

	public static void init(String server){
		init(server, null);
	}

	/**
	 * 
	 * @param server
	 * @param timeout 超时时间，毫秒
	 */
	public static void init(String server, Long timeout){
		CMC_SERVER = server;
		if(timeout!=null && timeout>0){
			HTTP_TIMEOUT = String.valueOf(timeout);
		}
		transConfigWS = null;
		transWS = null;
		transLibWS = null;
		packageOrderWS = null;
		rewardWS = null;
	}
	
	/**
	 * 翻译配置服务
	 * @return
	 * 修改历史：
	 */
	public static ITransConfigWS getTransConfigWS(){
		if(transConfigWS==null){
			try {
				transConfigWS = (ITransConfigWS) factory.create(transConfigServModel, CMC_SERVER+"/services/ITransConfigWS");
				Client.getInstance(transConfigWS).setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, HTTP_TIMEOUT);
			} catch (Exception e) {
				logger.error("访问翻译配置服务失败", e);
			}
		}
		
		return transConfigWS;
	}
	
	/**
	 * 翻译服务
	 * @return
	 * 修改历史：
	 */
	public static ITransWS getTransWS(){
		if(transWS==null){
			try {
				transWS = (ITransWS) factory.create(transServModel, CMC_SERVER+"/services/ITransWS");
				Client.getInstance(transWS).setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, HTTP_TIMEOUT);
			} catch (Exception e) {
				logger.error("访问翻译服务失败", e);
			}
		}
		
		return transWS;
	}
	
	/**
	 * 译库服务
	 * @return
	 * 修改历史：
	 */
	public static ITransLibWS getTransLibWS(){
		if(transLibWS==null){
			try {
				transLibWS = (ITransLibWS) factory.create(transLibServModel, CMC_SERVER+"/services/ITransLibWS");
				Client.getInstance(transLibWS).setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, HTTP_TIMEOUT);
			} catch (Exception e) {
				logger.error("访问翻译译库服务失败", e);
			}
		}
		
		return transLibWS;
	}
	
	/**
	 * 套餐订单服务
	 * @return
	 * 修改历史：
	 */
	public static IPackageOrderWS getPackageOrderWS(){
		if(packageOrderWS==null){
			try {
				packageOrderWS = (IPackageOrderWS) factory.create(packageOrderServModel, CMC_SERVER+"/services/IPackageOrderWS");
				Client.getInstance(packageOrderWS).setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, HTTP_TIMEOUT);
			} catch (Exception e) {
				logger.error("访问套餐订单服务失败", e);
			}
		}
		
		return packageOrderWS;
	}
	
	/**
	 * 奖励金服务
	 * @return
	 * 修改历史：
	 */
	public static IRewardWS getRewardWS(){
		if(rewardWS==null){
			try {
				rewardWS = (IRewardWS) factory.create(rewardServModel, CMC_SERVER+"/services/IRewardWS");
				Client.getInstance(rewardWS).setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, HTTP_TIMEOUT);
			} catch (Exception e) {
				logger.error("访问奖励金服务失败", e);
			}
		}
		
		return rewardWS;
	}
}
