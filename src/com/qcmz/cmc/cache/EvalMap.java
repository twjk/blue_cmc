package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.dao.IEvalDao;
import com.qcmz.cmc.entity.CmcEval;
import com.qcmz.cmc.entity.CmcEvalTag;
import com.qcmz.cmc.ws.provide.vo.EvalBean;
import com.qcmz.cmc.ws.provide.vo.EvalTagBean;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.constant.SystemConstants;

/**
 * 评价信息缓存
 */
@Component
public class EvalMap extends AbstractCacheMap {
	@Autowired
	private IEvalDao evalDao;
	
	/**
	 * <evalid, CmcEva>
	 */
	private Map<Long, CmcEval> map = null;
	private List<EvalBean> validList = null;
	
	@Override @PostConstruct
	protected void init() {
		Map<Long, CmcEval> mapTemp = new HashMap<Long, CmcEval>();
		List<EvalBean> validListTemp = new ArrayList<EvalBean>();
		
		List<CmcEval> evalList = evalDao.findEval();
		List<CmcEvalTag> tagList = evalDao.findEvalTag();
		EvalBean evalBean = null;
		for (CmcEval eval : evalList) {
			mapTemp.put(eval.getEvalid(), eval);
			
			if(SystemConstants.STATUS_ON.equals(eval.getStatus())){
				evalBean = new EvalBean();
				evalBean.setEvalId(eval.getEvalid());
				evalBean.setEvalType(eval.getEvaltype());
				evalBean.setEvalLevel(eval.getEvallevel());
				evalBean.setLevelName(eval.getLevelname());
				evalBean.setIcon(eval.getIcon());
				evalBean.setIcon2(eval.getIcon2());
				for (CmcEvalTag tag : tagList) {
					if(tag.getEvalid().equals(eval.getEvalid())){
						evalBean.getTags().add(new EvalTagBean(tag.getTag()));
					}
				}
				validListTemp.add(evalBean);
			}
		}
		
		map = mapTemp;
		validList = validListTemp;
		
	}

	/**
	 * 获取有效的评价列表
	 * @param evalType
	 * @return
	 */
	public List<EvalBean> findValidEval(Integer evalType){
		List<EvalBean> result = new ArrayList<EvalBean>();
		if(safeInit(validList)){
			if(evalType==null){
				return validList;
			}else{
				for (EvalBean evalBean : validList) {
					if(evalBean.getEvalType().equals(evalType)){
						result.add(evalBean);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 获取评价信息
	 * @param evalId
	 * @return
	 */
	public CmcEval getEval(Long evalId){
		if(safeInit(map)){
			return map.get(evalId);
		}
		return null;
	}
	
	
	@Override
	public void update(Object obj) {

	}

	@Override
	public void delete(Object obj) {

	}

}
