package com.qcmz.cmc.ws.provide.action;

import org.apache.log4j.Logger;

import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 接收微信支付异步通知
 */
public abstract class PaySynWeixinBaseAction extends BaseAction{
	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected String wxpaySuccess = "<xml><return_code>SUCCESS</return_code><return_msg></return_msg></xml>";
	protected String wxpayFail = "<xml><return_code>FAIL</return_code><return_msg></return_msg></xml>";
	
	/**
	 * 微信异步通知（扣款）
	 * 通知出发条件：扣款成功
	 * @return
	 * @throws Exception
	 * 修改历史：
	 */
	@Override
	public String execute() throws Exception {
		StringBuilder sbLog = new StringBuilder();
		String queryString = null;	//xml
		try {
			queryString = read();
//			queryString = (String)readObject();	//测试
//			queryString = "<xml><return_code>SUCCESS</return_code><result_code>SUCCESS</result_code><out_trade_no>230818110212610291</out_trade_no><transaction_id>weixin_1630389021497</transaction_id><total_fee>2500</total_fee><time_end>20230817135021</time_end></xml>";
			
			queryString = queryString.replace("\n", "");
			String subServiceType = StringUtil.subStringFirst(queryString, "<attach><![CDATA[", "]]></attach>");
			if(StringUtil.notBlankAndNull(queryString) && synWxpay(queryString, subServiceType)){
				logger.info(sbLog.append("SUCCESS").append("|").append(queryString));
			}else{
				logger.info(sbLog.append("IGNORE").append("|").append(queryString));
			}
			
			response.getWriter().print(wxpaySuccess);
		} catch (ProxyException e) {
			logger.info(sbLog.append("FAIL[").append(e.getMessage()).append("]|").append(queryString));
			response.getWriter().print(wxpayFail);
		}catch (Exception e) {
			logger.error(sbLog.append("ERROR").append("|").append(queryString), e);
		}
		return null;
	}
	
	public abstract boolean synWxpay(String queryString, String subServieType);
	
	public static void main(String[] args) {
		//1.修改queryString的out_trade_no为订单号、total_fee
		//2.WxpayProxy.parseSyn 取消签名验证
		System.out.println(HttpUtil.post("http://localhost:8088/cmc/services-ssl/paySynWeixin4Zc.do", ""));
	}
}
