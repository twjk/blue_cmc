package com.qcmz.cmc.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.entity.CmcRpPacket;
import com.qcmz.cmc.entity.CmcRpReceive;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.NumberUtil;

public class RedPacketUtil {
	
	/**
	 * 红包支持的交易途径
	 */
	public static List<String> TRADEWAYS = new ArrayList<String>();
	static{
		TRADEWAYS.add(DictConstants.DICT_TRADEWAY_WXPAY);
	}
	
	
	/**
	 * 获取新的红包编号:RP(2)+yyMMddHHmmss(12)+uid(8)+random(2)
	 * @return
	 * 修改历史：
	 */
	public static String newPacketId(Long userId){
		return new StringBuilder("RP")
			.append(DateUtil.format(new Date(), "yyMMddHHmmss"))
			.append(userId)
			.append(NumberUtil.random(99, 10))
			.toString()
			;
	}
	
	/**
	 * 支付描述
	 * @param packetId
	 * @return
	 */
	public static String getPayOrderDesc(String packetId){
		return new StringBuilder("趣味语音-").append(packetId).toString();
	}
	
	/**
	 * 转账订单号
	 * @param userId
	 * @param cashId
	 * @return
	 */
	public static String getTransferOrderId(Long userId, Long cashId){
		return new StringBuilder("RP").append(userId).append("C").append(cashId).toString();
	}
	
	/**
	 * 转账描述
	 * @param packetId
	 * @return
	 */
	public static String getTransferOrderDesc(String orderId){
		return new StringBuilder("趣味语音提现-").append(orderId).toString();
	}
	
	/**
	 * 计算服务费
	 * @param packetAmount
	 * @return
	 */
	public static double calServiceAmount(Double packetAmount){
		return DoubleUtil.multiplyRound(packetAmount, SystemConfig.REDPACKET_SERVICEPERCENT*0.001, 2);
	}
	
	/**
	 * 切分生成红包
	 * @param amount
	 * @param packetNum
	 * @return
	 */
	public static double[] genPacketAmount(double amount, int packetNum){
		double[] result = new double[packetNum];
		if(packetNum==1){
			result[0] = amount;
			return result;
		}
		
		double balance = amount;
		double max = balance-packetNum*0.1;
		double min = 0.01;
		for (int i = 0; i < packetNum-1; i++) {
			max = balance-(packetNum-i)*0.1;
			result[i] = DoubleUtil.add(DoubleUtil.multiplyRound(Math.random(), max-min), min); 
			balance = DoubleUtil.subtract(balance ,result[i]);
		}
		result[packetNum-1] = balance;
		
		return result;
	}
	
	/**
	 * 生成待领取红包列表
	 * @param packetId
	 * @param userId
	 * @param packetAmount
	 * @param packetNum
	 * @return
	 */
	public static List<CmcRpReceive> genReceives(CmcRpPacket packet){
		double[] packetAmounts = RedPacketUtil.genPacketAmount(packet.getPacketamount(), packet.getPacketnum());
		List<CmcRpReceive> result = new ArrayList<CmcRpReceive>();
		CmcRpReceive receive = null;
		for (double pa : packetAmounts) {
			receive = new CmcRpReceive();
			receive.setAmount(pa);
			receive.setUserid(packet.getUserid());
			receive.setPacketid(packet.getPacketid());
			result.add(receive);
		}
		return result;
	}
}
