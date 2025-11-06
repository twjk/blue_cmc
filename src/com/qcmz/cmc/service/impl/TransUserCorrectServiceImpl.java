package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.bdc.ws.provide.locator.MsgPushThrd;
import com.qcmz.bdc.ws.provide.locator.MsgPushWS;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.ITransUserCorrectDao;
import com.qcmz.cmc.entity.CmcTransCorrect;
import com.qcmz.cmc.entity.CmcTransUsercorrect;
import com.qcmz.cmc.service.ITransUserCorrectService;
import com.qcmz.cmc.ws.provide.vo.TransUserCorrectBean;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

@Service
public class TransUserCorrectServiceImpl implements ITransUserCorrectService {
	@Autowired
	private ITransUserCorrectDao transUserCorrectDao;
	
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean<CmcTransUsercorrect>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		transUserCorrectDao.queryByMapTerm(map, pageBean);
	}
	
	/**
	 * 获取用户纠错信息
	 * @param correctId
	 * @return
	 */
	public CmcTransUsercorrect getUserCorrect(Long correctId){
		return (CmcTransUsercorrect) transUserCorrectDao.load(CmcTransUsercorrect.class, correctId);
	}
	
	/**
	 * 保存用户纠错记录
	 * @param bean
	 */
	public void saveUserCorrect(TransUserCorrectBean bean){
		CmcTransUsercorrect entity = new CmcTransUsercorrect();
		entity.setSessionid(bean.getSid());
		entity.setUserid(bean.getUid());
		entity.setUuid(bean.getUuid());
		entity.setPushregid(bean.getPushRegid());
		entity.setFromlang(bean.getFrom());
		entity.setSrc(bean.getSrc());
		entity.setTolang(bean.getTo());
		entity.setDst(bean.getDst());
		entity.setMtdst(bean.getMtDst());
		entity.setCreatetime(new Date());
		entity.setStatus(SystemConstants.STATUS_UNDEAL);
		
		transUserCorrectDao.save(entity);
	}
	
	/**
	 * 处理用户纠错记录
	 * @param correctId
	 * @param dst
	 * @param operator
	 * @return
	 */
	public CmcTransCorrect dealUserCorrect(Long correctId, String dst, Operator operator){
		CmcTransUsercorrect entity = getUserCorrect(correctId);

		Date now = new Date();
		
		CmcTransCorrect correct = new CmcTransCorrect();
		correct.setSessionid(entity.getSessionid());
		correct.setUserid(entity.getUserid());
		correct.setUuid(entity.getUuid());
		correct.setPushregid(entity.getPushregid());
		correct.setFromlang(entity.getFromlang());
		correct.setSrc(entity.getSrc());
		correct.setTolang(entity.getTolang());
		correct.setDst(dst);
		correct.setMtdst(entity.getMtdst());
		correct.setCorrectsource(DictConstant.DICT_TRANS_CORRECTSOURCE_USER);
		correct.setOpercd(operator.getOperCd());
		correct.setOpername(operator.getOperName());
		correct.setOpertime(now);
		
		entity.setDst(dst);
		entity.setOpercd(operator.getOperCd());
		entity.setOpername(operator.getOperName());
		entity.setOpertime(now);
		entity.setStatus(SystemConstants.STATUS_SUCCESS);
		
		//入库
		transUserCorrectDao.update(entity);
		transUserCorrectDao.save(correct);
		
		//推送
		MsgPushThrd.pushMsg(1023L, MsgPushWS.PUSHTOTYPE_REGID, entity.getPushregid(), dst, entity.getSessionid(), null);
		
		return correct;
	}
	
	/**
	 * 删除用户纠错记录
	 * @param correctId
	 */
	public void deleteUserCorrect(Long correctId, Operator operator){
		transUserCorrectDao.delete(CmcTransUsercorrect.class, correctId);
	}
}
