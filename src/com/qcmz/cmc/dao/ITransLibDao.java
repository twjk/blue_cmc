package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.vo.TransLibrary4ImportBean;
import com.qcmz.cmc.vo.TransLibraryEntity;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：译库维护
 * 修改历史：
 */
public interface ITransLibDao extends IBaseDAO {
	/**
	 * 分页获取译库列表
	 * @param map
	 * @param pageBean<TransLibraryEntity>
	 * 修改历史：
	 */
	public void queryByMapTerm(String libClass, Map<String, String> map, PageBean pageBean);
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
	 * @return
	 * 修改历史：
	 */
	public List<TransLibraryEntity> findLib(String libClass, String from, String to);
	/**
	 * 获取译库列表
	 * @param libClass not null
	 * @param from not null
	 * @param src not null
	 * @param to not null
	 * @param status
	 * @return
	 * 修改历史：
	 */
	public List<TransLibraryEntity> findLib(String libClass, String from, String src, String to, Integer status);
	/**
	 * 查询译库
	 * @param ids
	 * @return
	 * 修改历史：
	 */
	public List<TransLibraryEntity> findLib(List<Long> ids);
	/**
	 * 获取可以放入缓存的译库列表
	 * @param libClass
	 * @return
	 * 修改历史：
	 */
	public List<TransLibraryEntity> findLib4Cache(String libClass);
	/**
	 * 获取待导入的译库
	 * @param libClass not null
	 * @param themeId not null
	 * @param from not null
	 * @param to not null
	 * @param key
	 * @return
	 * 修改历史：
	 */
	public List<TransLibrary4ImportBean> findLib4ImportTheme(String libClass, Long themeId, String libType, String from, String to, String key);
	/**
	 * 获取英文原文
	 * @param limit
	 * @return
	 * 修改历史：
	 */
	public List<String> findEnSrc(int limit);
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
	 * 获取单条译库信息
	 * @param libClass
	 * @param libId
	 * @return
	 * 修改历史：
	 */
	public TransLibraryEntity getLib(String libClass, Long libId);
	/**
	 * 获取单条译库信息
	 * @param libClass not null
	 * @param from not null
	 * @param src not null
	 * @param to not null
	 * @param dst not null
	 * @return
	 * 修改历史：
	 */
	public TransLibraryEntity getLib(String libClass, String from, String src, String to, String dst);
	/**
	 * 判断是否重复
	 * @param libClass
	 * @param from
	 * @param src
	 * @param to
	 * @param libId
	 * @return
	 * 修改历史：
	 */
	public boolean isRepeat(String libClass, String from, String src, String to, String dst, Long libId);
	/**
	 * 判断是否存在
	 * @param from
	 * @param src
	 * @return
	 * 修改历史：
	 */
	public boolean isExist(String libClass, String from, String src, String to);
	/**
	 * 获取新的译库编号
	 * @return
	 * 修改历史：
	 */
	public Long getNewLibId();
	/**
	 * 更新状态
	 * @param libClass
	 * @param libId
	 * @param status
	 * 修改历史：
	 */
	public void updateStatus(String libClass, Long libId, Integer status);
	/**
	 * 更新核对状态
	 * @param libClass
	 * @param libId
	 * @param checkStatus
	 * 修改历史：
	 */
	public void updateCheckStatus(String libClass, Long libId, Integer checkStatus);
	/**
	 * 增加命中次数
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
	 * 删除
	 * @param libClass
	 * @param libId
	 * 修改历史：
	 */
	public void deleteLib(String libClass, Long libId);
}
