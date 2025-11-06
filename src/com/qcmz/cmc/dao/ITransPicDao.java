package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcPtPic;
import com.qcmz.cmc.entity.CmcPtTrans;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryBean;
import com.qcmz.cmc.ws.provide.vo.TransPicBean;
import com.qcmz.cmc.ws.provide.vo.TransPicDealListBean;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface ITransPicDao extends IBaseDAO {
	/**
	 * 分页获取图片翻译信息，含订单信息
	 * @param map
	 * @param pageBean
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取用户图片翻译列表
	 * @param userId
	 * @param pageSize
	 * @param lastPicId
	 * @return
	 * 修改历史：
	 */
	public List<TransPicBean> findPic(Long userId, int pageSize, String lastPicId);
	/**
	 * 分页获取图片翻译列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	public List<TransPicDealListBean> findPic4Deal(OrderDealQueryBean query, int pageSize);
	/**
	 * 分页获取捡漏图片翻译列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	public List<TransPicDealListBean> findPic4PickDeal(OrderDealQueryBean query, int pageSize);
	/**
	 * 获取图片列表
	 * @param orderIds
	 * @return
	 */
	public List<CmcPtPic> findPic(List<String> orderIds);
	/**
	 * 获取图片译文列表
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public List<CmcPtTrans> findTrans(String orderId);
	/**
	 * 获取译文列表
	 * @param orderIds
	 * @return
	 * 修改历史：
	 */
	public List<CmcPtTrans> findTrans(List<String> orderIds);
	/**
	 * 更新图片地址
	 * @param picId
	 * @param picUrl
	 * @param thumbUrl
	 * 修改历史：
	 */
	public void updatePicUrl(String picId, String picUrl, String thumbUrl);
	/**
	 * 更新地址
	 * @param picId
	 * @param address
	 */
	public void updateAddress(String picId, String address);
	/**
	 * 清除图片翻译结果
	 * @param picId
	 * 修改历史：
	 */
	public void clearTrans(String picId);
	/**
	 * 删除不保留的翻译结果
	 * @param picId
	 * @param retainTransIds 保留的翻译编号列表
	 * 修改历史：
	 */
	public void deleteTransUnRetain(String picId, List<Long> retainTransIds);
}
