package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ISceneDao;
import com.qcmz.cmc.entity.CmcBScene;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class SceneDao extends BaseDAO implements ISceneDao {
	/**
	 * 获取所有场景
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcBScene> findSceneJoinCat(){
		return (List<CmcBScene>) qryList("from CmcBScene t left join fetch t.cmcBScenecat order by t.cmcBScenecat.sortindex, t.sortindex, t.sceneid");
	}
}
