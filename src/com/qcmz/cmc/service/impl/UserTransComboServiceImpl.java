package com.qcmz.cmc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IUserTransComboDao;
import com.qcmz.cmc.entity.CmcCombo;
import com.qcmz.cmc.entity.CmcComboPackage;
import com.qcmz.cmc.entity.CmcUCombo;
import com.qcmz.cmc.entity.CmcUCombohis;
import com.qcmz.cmc.service.IUserTransComboService;
import com.qcmz.cmc.util.TransComboUtil;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataAccessException;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.OperLogUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class UserTransComboServiceImpl implements IUserTransComboService {
	@Autowired
	private IUserTransComboDao userTransComboDao;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 分页获取用户翻译套餐列表，带套餐
	 * @param map
	 * @param pageBean<CmcUCombo>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		userTransComboDao.queryByMapTerm(map, pageBean);
		
		List<CmcUCombo> userComboList = (List<CmcUCombo>) pageBean.getResultList();
		if(userComboList.isEmpty()) return;
		
		try {
			Set<Long> userIds = new HashSet<Long>();
			for (CmcUCombo rc : userComboList) {
				userIds.add(rc.getUserid());				
			}
			//获取用户信息
			Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
			for (CmcUCombo userCombo : userComboList) {
				userCombo.setUser(userMap.get(userCombo.getUserid()));
			}
		} catch (Exception e) {
			logger.error("获取用户信息失败",e);
		}
	}
	
	/**
	 * 获取用户有效的套餐列表
	 * @param userId
	 * @param transType
	 * @param comboType
	 * @return
	 */
	public List<CmcUCombo> findUserValidCombo(Long userId, String transType, Integer comboType){
		return userTransComboDao.findUserValidCombo(userId, transType, comboType);
	}
	
	/**
	 * 获取用户套餐列表
	 * @param userId
	 * @param transType
	 * @param comboType
	 * @return
	 */
	public List<CmcUCombo> findUserCombo(Long userId, String transType, Integer comboType){
		return userTransComboDao.findUserCombo(userId, transType, comboType);
	}
	
	/**
	 * 获取用户套餐列表
	 * @param ucids
	 * @return
	 */
	public List<CmcUCombo> findUserCombo(Set<Long> ucids){
		return userTransComboDao.findUserCombo(ucids);
	}
	
	/**
	 * 获取用户套餐信息
	 * @param ucid
	 * @return
	 */
	public CmcUCombo getUserCombo(Long ucid){
		return (CmcUCombo) userTransComboDao.load(CmcUCombo.class, ucid);
	}
	
	
	/**
	 * 获取用户套餐信息
	 * @param orderId
	 * @return
	 */
	public CmcUCombo getUserCombo(String orderId){
		return userTransComboDao.getUserCombo(orderId);
	}
	
	/**
	 * 获取用户套餐信息
	 * @param exchangeCode
	 * @return
	 */
	public CmcUCombo getUserComboByCode(String exchangeCode){
		return userTransComboDao.getUserComboByCode(exchangeCode);
	}
	
	/**
	 * 获取用户套餐信息，含套餐、使用记录
	 * @param ucid
	 * @return
	 */
	public CmcUCombo getUserComboJoin(Long ucid){
		CmcUCombo combo = userTransComboDao.getUserComboJoinCombo(ucid);
		combo.setUser(UserMap.getUser(combo.getUserid()));
		combo.setHises(userTransComboDao.findHis(ucid));
		return combo;
	}
	
	/**
	 * 获取用户翻译套餐使用的已完成的订单数
	 * @param ucid not null
	 * @param operDate
	 * @return
	 */
	public Long getUserComboUsedCount(Long ucid, Date operDate){
		return userTransComboDao.getUserComboUsedCount(ucid, operDate);
	}
	
	/**
	 * 保存用户套餐信息
	 * @param cmcUCombo
	 */
	public void saveOrUpdateUserCombo(CmcUCombo cmcUCombo){
		userTransComboDao.saveOrUpdate(cmcUCombo);
	}

	/**
	 * 添加用户套餐
	 * @param combo
	 * @param pkg
	 * @param userId
	 * @param num
	 * @param startTime
	 * @param orderId
	 * @param paySide
	 * @param copies 批量添加份数
	 */
	public List<CmcUCombo> addUserCombo(CmcCombo combo, CmcComboPackage pkg, Long userId, Integer num, Date startTime, String orderId, Integer paySide, int copies){
		List<CmcUCombo> result = new ArrayList<CmcUCombo>();
		
		//避免订单重复添加套餐
		if(StringUtil.notBlankAndNull(orderId)){
			CmcUCombo userCombo = userTransComboDao.getUserCombo(orderId);
			if(userCombo!=null) return result;
		}
		
		String title = null;
		if(pkg!=null){
			title = TransComboUtil.getComboTitle(combo.getTitle(), pkg.getPkgtitle());
		}else{
			title = TransComboUtil.getComboTitle(combo.getTitle(), num, combo.getUnitname());
		}
		
		Date now = new Date();
		Date endTime = null;
		
		//开始时间
		if(startTime==null || startTime.before(now)){
			startTime = now;
		}
		startTime = DateUtil.setMinTime(startTime);
		
		//结束时间
		if(!DictConstant.DICT_VALIDITYTYPE_FOREVER.equals(combo.getValiditytype())){
			int cycleValue = 0;
			if(DictConstant.DICT_VALIDITYTYPE_FIXED.equals(combo.getValiditytype())){				
				cycleValue = combo.getCyclevalue();	//固定
			}else if(DictConstant.DICT_VALIDITYTYPE_ASSIGNED.equals(combo.getValiditytype())){
				cycleValue = num;
			}
			if(DictConstant.DICT_VALIDITYCYCLETYPE_DAY.equals(combo.getCycletype())){
				endTime = DateUtil.addDay(startTime, cycleValue-1);
			}else if(DictConstant.DICT_VALIDITYCYCLETYPE_MONTH.equals(combo.getCycletype())){
				endTime = DateUtil.addMonth(startTime, cycleValue);
			}else if(DictConstant.DICT_VALIDITYCYCLETYPE_YEAR.equals(combo.getCycletype())){
				endTime = DateUtil.addYear(startTime, cycleValue);
			}
			endTime = DateUtil.setMaxTime(endTime);
		}	
		
		CmcUCombo entity = null;
		for (int i = 0; i < copies; i++) {
			entity = new CmcUCombo();
			entity.setUserid(userId);
			entity.setComboid(combo.getComboid());
			if(pkg!=null){
				entity.setPkgid(pkg.getPkgid());
			}
			entity.setCombotitle(title);
			entity.setTranstype(combo.getTranstype());
			entity.setCombotype(combo.getCombotype());
			entity.setNum(num);
			if(DictConstant.DICT_TRANSCOMBO_UNIT_TIMES.equals(combo.getPriceunit())){
				entity.setUsablenum(entity.getNum());
			}
			entity.setUnit(combo.getPriceunit());
			entity.setStarttime(startTime);
			entity.setEndtime(endTime);
			entity.setOrderid(orderId);
			entity.setCreatetime(now);
			entity.setPayside(paySide);
			
			if(userId==null){
				entity.setExchangecode(TransComboUtil.getExchangeCode(userTransComboDao.newUserComboSeq()));
				entity.setExchangestatus(SystemConstants.STATUS_OFF);
			}
			result.add(entity);
		}
		
		userTransComboDao.saveOrUpdateAll(result);
		
		return result;
	}
	
	/**
	 * 用户使用用户套餐
	 * @param ucid
	 * @param userId
	 * @param orderId
	 * @exception DataException
	 */
	public void useUserCombo4User(Long ucid, Long userId, String transType, String orderId){
		CmcUCombo userCombo = getUserCombo(ucid);
		if(!TransComboUtil.isValid(userCombo, userId, transType)){
			throw new DataException("用户套餐无效");
		}
		Operator oper = OperLogUtil.getUserOperator(userId);
		useUserCombo(userCombo, null, orderId, null, oper);
	}
	
	/**
	 * 操作员录入用户套餐使用记录
	 * @param ucid
	 * @param usedTime
	 * @param orderId
	 * @param remark
	 * @param oper
	 * @exception DataException
	 */
	public void useUserCombo4Operator(Long ucid, Date usedTime, String orderId, String remark, Operator oper){
		CmcUCombo userCombo = getUserCombo(ucid);
		if(!TransComboUtil.isValid(userCombo, userCombo.getUserid(), userCombo.getTranstype())){
			throw new DataException("用户套餐无效");
		}
		useUserCombo(userCombo, usedTime, orderId, remark, oper);
	}
	
	/**
	 * 使用用户套餐
	 * @param userCombo
	 * @param usedTime
	 * @param orderId
	 * @param remark
	 * @param oper
	 */
	private void useUserCombo(CmcUCombo userCombo, Date usedTime, String orderId, String remark, Operator oper){
		Date now = new Date();
		
		CmcUCombohis his = new CmcUCombohis();
		his.setUcid(userCombo.getUcid());
		his.setUsedorderid(orderId);
		his.setUsedtime(usedTime==null?now:usedTime);
		his.setRemark(remark);
		his.setOpercd(oper.getOperCd());
		his.setOpername(oper.getOperName());
		his.setOpertime(now);
		
		userCombo.setUsedtimes(userCombo.getUsedtimes()+1);
		if(DictConstant.DICT_TRANSCOMBO_UNIT_TIMES.equals(userCombo.getUnit())){
			userCombo.setUsablenum(userCombo.getUsablenum()-1);
		}
		
		userTransComboDao.update(userCombo);
		userTransComboDao.save(his);
	}
	
	
	
	/**
	 * 回滚用户套餐使用
	 * @param ucid
	 * @param orderId
	 */
	public void rollbackUserCombo(Long ucid, String orderId){
		CmcUCombo uc = getUserCombo(ucid);
		
		uc.setUsedtimes(uc.getUsedtimes()-1);
		if(DictConstant.DICT_TRANSCOMBO_UNIT_TIMES.equals(uc.getUnit())){
			uc.setUsablenum(uc.getUsablenum()+1);
		}
		
		userTransComboDao.update(uc);
		userTransComboDao.deleteHis(orderId);		
	}
	
	/**
	 * 作废用户套餐
	 * @param ucid
	 * @param oper
	 * @exception DataAccessException
	 */
	public void invalidUserCombo(Long ucid, Operator oper){
		CmcUCombo uc = getUserCombo(ucid);
		if(!SystemConstants.STATUS_OFF.equals(uc.getExchangestatus())
				|| uc.getUserid()!=null){
			throw new DataAccessException("非未兑换的套餐不能作废");
		}
		
		uc.setExchangestatus(SystemConstants.STATUS_INVALID);
		userTransComboDao.update(uc);
		
		OperLogUtil.saveOperLog(CmcUCombo.class, uc.getUcid(), oper, "作废："+uc.getExchangecode());
	}
	
	/**
	 * 删除用户套餐
	 * @param ucid
	 * @param oper
	 * @exception DataAccessException
	 */
	public void deleteUserCombo(Long ucid, Operator oper){
		CmcUCombo uc = getUserCombo(ucid);
		if(!SystemConstants.STATUS_OFF.equals(uc.getExchangestatus())
				|| uc.getUserid()!=null){
			throw new DataAccessException("非未兑换的套餐不能作废");
		}
		
		userTransComboDao.delete(uc);
		
		OperLogUtil.saveDelLog(uc.getUcid(), oper, uc);
	}
}
