package com.qcmz.cmc.service;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.vo.TransLibrary4ImportBean;
import com.qcmz.cmc.vo.TransLibraryEntity;
import com.qcmz.framework.exception.DataRepeatException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

/**
 * 类说明：译库服务
 * 修改历史：
 */
public interface ITransLibService {
	/**
	 * 分页获取译库列表
	 * @param map
	 * @param pageBean<TransLibraryEntity>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取译库列表
	 * @param libClass not null
	 * @param status
	 * @param pageSize
	 * @return
	 * 修改历史：
	 */
	public List<TransLibraryEntity> findLib(String libClass, Integer status, Long lastId, int pageSize);
	/**
	 * 获取译库列表
	 * @param libClass not null
	 * @param from not null
	 * @param to not null
	 * @param status
	 * @return
	 * 修改历史：
	 */
	public List<TransLibraryEntity> findLib(String libClass, String from, String to);
	/**
	 * 获取译库列表
	 * @param from not null
	 * @param src not null
	 * @param to not null
	 * @param status
	 * @return
	 * 修改历史：
	 */
	public List<TransLibraryEntity> findLib(String from, String src, String to, Integer status);
	/**
	 * 获取可以放入缓存的译库列表
	 * @return
	 * 修改历史：
	 */
	public List<TransLibraryEntity> findLib4Cache(String libClass);
	/**
	 * 获取待导入的译库
	 * @param themeId not null
	 * @param from not null
	 * @param to not null
	 * @param key
	 * @return
	 * 修改历史：
	 */
	public List<TransLibrary4ImportBean> findLib4ImportTheme(Long themeId, String libType, String from, String to, String key);
	/**
	 * 获取译库源语言代码列表
	 * @return
	 * 修改历史：
	 */
	public List<String> findFromLang();
	/**
	 * 获取译库目标语言代码列表
	 * @return
	 * 修改历史：
	 */
	public List<String> findToLang();
	/**
	 * 获取单条记录
	 * @param libClass
	 * @param libId
	 * @return
	 * 修改历史：
	 */
	public TransLibraryEntity getLib(String libClass, Long libId);
	/**
	 * 获取新的译库编号
	 * @return
	 * 修改历史：
	 */
	public Long getNewLibId();
	/**
	 * 判断是否存在
	 * @param from
	 * @param src
	 * @return
	 * 修改历史：
	 */
	public boolean isExist(String from, String src, String to);
	/**
	 * 批量保存，重复继续
	 * @param bean 除了libid,libclass,voice其他字段都需要赋值
	 * @param oper
	 * 修改历史：
	 */
	public void saveOrUpdateAll(List<TransLibraryEntity> list, Operator oper);
	/**
	 * 保存或修改
	 * @param bean
	 * @exception DataRepeatException
	 * 修改历史：
	 */
	public List<TransLibraryEntity> saveOrUpdate(TransLibraryEntity bean, Operator oper);
	/**
	 * 迁移
	 * @param bean
	 * @param toLibClass
	 * @param oper
	 * 修改历史：
	 */
	public void migration(TransLibraryEntity bean, String toLibClass, Long themeId, Operator oper);
	/**
	 * 更新状态
	 * @param libClass
	 * @param libId
	 * @param status
	 * 修改历史：
	 */
	public void updateStatus(String libClass, Long libId, Integer status, Operator oper);
	/**
	 * 更新核对状态
	 * @param libClass
	 * @param libId
	 * @param checkStatus
	 * 修改历史：
	 */
	public void updateCheckStatus(String libClass, Long libId, Integer checkStatus, Operator oper);
	/**
	 * 增加命中次数
	 * @param libClass
	 * @param libId
	 * @param times
	 * 修改历史：
	 */
	public void increaseHitCount(String libClass, Long libId, int times);
	/**
	 * 保存命中次数
	 * @param libClass
	 * @param libId
	 * @param hitCount
	 * 修改历史：
	 */
	public void updateHitCount(String libClass, Long libId, Long hitCount);
	/**
	 * 删除，同时删除反向翻译的结果
	 * @param libClass
	 * @param libId
	 * @param oper
	 * @return
	 * 修改历史：
	 */
	public List<TransLibraryEntity> delete(String libClass, Long libId, Operator oper);
	/**
	 * 删除，不删除反向翻译结果
	 * @param libClass
	 * @param libId
	 * @param oper
	 * @return
	 * 修改历史：
	 */
	public void deleteNotReverse(String libClass, Long libId, Operator oper);
}
