package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcTransCorrect;
import com.qcmz.cmc.service.ITransCorrectService;
import com.qcmz.cmc.service.ITransDiffService;
import com.qcmz.cmc.service.ITransUserCorrectService;
import com.qcmz.cmc.vo.TransLibraryEntity;
import com.qcmz.cmc.ws.provide.vo.TransCorrectBean;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.log.Operator;

@Component
public class TransCorrectProcess {
	@Autowired
	private ITransDiffService transDiffService;
	@Autowired
	private ITransCorrectService transCorrectService;
	@Autowired
	private ITransUserCorrectService transUserCorrectService;
	
	@Autowired
	private TransLibProcess transLibProcess;
	
	/**
	 * 获取修正翻译列表
	 * @param uuid
	 * @return
	 */
	public List<TransCorrectBean> findCorrect(String uuid){
		List<TransCorrectBean> result = new ArrayList<TransCorrectBean>();
		TransCorrectBean bean = null;
		
		Date minTime = DateUtil.addDay(DateUtil.getSysDateOnly(), -1);
		List<CmcTransCorrect> list = transCorrectService.findCorrectTrans(uuid, minTime);
		if(list.isEmpty()) return result;
		
		for (CmcTransCorrect human : list) {
			bean = new TransCorrectBean();
			bean.setSid(human.getSessionid());
			bean.setFrom(human.getFromlang());
			bean.setSrc(human.getSrc());
			bean.setTo(human.getTolang());
			bean.setDst(human.getDst());
			result.add(bean);
		}
		return result;
	}
	
	/**
	 * 处理用户纠错
	 * @param correctId
	 * @param dst
	 * @param toLib
	 * @param operator
	 */
	public void dealUserCorrect(Long correctId, String dst, boolean toLib, Operator operator){
		CmcTransCorrect correct = transUserCorrectService.dealUserCorrect(correctId, dst, operator);
		if(toLib){
			saveCorrect2Lib(correct, operator);
		}
	}
	
	/**
	 * 修正翻译
	 * @param diffId
	 * @param dst
	 * @param toLib
	 * @param operator
	 */
	public void correctDiff(Long diffId, String dst, boolean toLib, Operator operator){
		CmcTransCorrect correct = transDiffService.correctDst(diffId, dst, operator);
		if(toLib){
			saveCorrect2Lib(correct, operator);
		}
	}
	
	/**
	 * 修正信息入译库
	 * @param correct
	 * @param operator
	 */
	public void saveCorrect2Lib(CmcTransCorrect correct, Operator operator){
		try {
			TransLibraryEntity lib = new TransLibraryEntity();
			lib.setLibtype(DictConstant.DICT_LIBTYPE_LIB);
			lib.setFromlang(correct.getFromlang());
			lib.setSrc(correct.getSrc());
			lib.setTolang(correct.getTolang());
			lib.setDst(correct.getDst());
			lib.setTwoway(SystemConstants.STATUS_OFF);
			lib.setStatus(SystemConstants.STATUS_ON);
			lib.setCheckstatus(SystemConstants.STATUS_ON);
			lib.setSourceid(DictConstant.DICT_LIBSOURCE_INPUT);
			transLibProcess.saveOrUpdate(lib, operator);
		} catch (Exception e) {
		}		
	}
	
}
