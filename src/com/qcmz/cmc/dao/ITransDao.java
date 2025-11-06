package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.framework.dao.IBaseDAO;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Jun 4, 2014 3:01:45 PM
 * 修改历史：
 */
public interface ITransDao extends IBaseDAO {
	/**
	 * 查询某用户当天翻译次数
	 * @param token 用户标识
	 * @param excLang 不参加计数的语言
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Sep 4, 2014 3:46:29 PM
	 * 修改历史：
	 */
	public Long getTransCountToday(String token, List<String> excLang);
}
