package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.entity.CmcTransDiff;
import com.qcmz.cmc.service.ITransDiffService;
import com.qcmz.cmc.util.TransUtil;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.DateUtil;

@Component
public class TransDiffProcess {
	@Autowired
	private ITransDiffService transDiffService;
	
	/**
	 * 待入库翻译差异队列
	 */
	private ConcurrentLinkedQueue<CmcTransDiff> diffs = new ConcurrentLinkedQueue<CmcTransDiff>();
	
	/**
	 * 加入队列
	 * @param from
	 * @param to
	 * @param src
	 * @param dst
	 * @param similar
	 * @param proxyId
	 * @param sessionId
	 * @param userId
	 * @param uuid
	 * @param pushRegid
	 * @param platform
	 * @param version
	 * @param serviceType
	 */
	public void addDiff(String from, String to, String src, String dst, int similar, Long proxyId
			, String sessionId, Long userId, String uuid, String pushRegid
			, String platform, String version, String serviceType, Integer transModel){
		if(TransUtil.isSaveTransDiff(src, similar, sessionId, pushRegid, platform, version, serviceType, transModel)){
			CmcTransDiff diff = new CmcTransDiff();
			diff.setProxyid(proxyId);
			diff.setSessionid(sessionId);
			diff.setUserid(userId);
			diff.setUuid(uuid);
			diff.setPushregid(pushRegid);
			diff.setFromlang(from);
			diff.setSrc(src);
			diff.setTolang(to);
			diff.setDst(dst);
			diff.setSimilar(similar);
			diff.setTranstime(new Date());
			diff.setStatus(SystemConstants.STATUS_UNDEAL);
			diffs.add(diff);
		}
	}

	/**
	 * 翻译差异入库
	 */
	public void saveDiff(){
		if(diffs.isEmpty()) return;
		
		List<CmcTransDiff> list = new ArrayList<CmcTransDiff>();
		list.addAll(diffs);
		
		diffs = new ConcurrentLinkedQueue<CmcTransDiff>();
		
		transDiffService.saveOrUpdate(list);		
	}
	
	/**
	 * 清除翻译差异
	 */
	public void clearDiff(){
		Date maxTime = DateUtil.addMinute(new Date(), -TransConfig.TRANS_DIFF_DURATION);
		transDiffService.clearDiff(maxTime);
	}
}
