package com.qcmz.cmc.process;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.bdc.ws.provide.locator.MsgPushWS;
import com.qcmz.bdc.ws.provide.vo.MsgPushReq;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.entity.CmcPkgDaysentence;
import com.qcmz.cmc.service.IDaySentenceService;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
@Component
public class PushProcess {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IDaySentenceService daySentenceService;
	
	
	/**
	 * 推送每日一句
	 * @author 李炳煜
	 * 修改历史：
	 */
	public void pushDaySentence(){
		if(StringUtil.isBlankOrNull(SystemConfig.DAYSENTENCE_PUSHTAGS)){
			logger.info("每日一句推送标签和测试注册编号均为空");
			return;
		}
		
		//获取带推送列表
		List<CmcPkgDaysentence> list = daySentenceService.findSentence4Push();
		if(list.isEmpty()){
			return;
		}
		
		//推送
		Date now = new Date();
		for (CmcPkgDaysentence entity : list) {
			try {
				entity.setPushtime(now);
				
				MsgPushReq req = new MsgPushReq();
				req.setTypeid(MsgPushWS.MSGTYPE_VOICETRANS_DAY);
				req.setToType(MsgPushWS.PUSHTOTYPE_TAG);
				req.setTo(SystemConfig.DAYSENTENCE_PUSHTAGS);
				req.setMsg(entity.getTitle());
				req.setIdentify(String.valueOf(entity.getSentid()));
				req.setTiming(DateUtil.formatDateTime(entity.getReleasetime()));
				MsgPushWS.pushMsg(req);
				
				entity.setPushstatus(SystemConstants.STATUS_SUCCESS);
			} catch (Exception e) {
				logger.error("每日一句推送失败："+entity.getSentid(), e);
			}
		}
		
		//保存推送结果
		daySentenceService.saveOrUpdateAll(list);
	}
	
}
