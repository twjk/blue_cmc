package com.qcmz.cmc.service;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcPtPic;
import com.qcmz.cmc.entity.CmcPtTrans;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryBean;
import com.qcmz.cmc.ws.provide.vo.TransPicAddBean;
import com.qcmz.cmc.ws.provide.vo.TransPicBean;
import com.qcmz.cmc.ws.provide.vo.TransPicDealListBean;
import com.qcmz.cmc.ws.provide.vo.TransPicDealBean;
import com.qcmz.cmc.ws.provide.vo.TransPicDealDetailBean;
import com.qcmz.cmc.ws.provide.vo.TransPicDetailBean;
import com.qcmz.cmc.ws.provide.vo.TransPicRotateResult;
import com.qcmz.cmc.ws.provide.vo.TransPriceBean;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface ITransPicService {
	/**
	 * 分页获取图片翻译信息，含用户信息
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
	 * @param picIds
	 * @return
	 */
	public List<CmcPtPic> findPic(List<String> picIds);
	/**
	 * 获取图片译文列表
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public List<CmcPtTrans> findTrans(String orderId);
	/**
	 * 获取翻译图片信息
	 * @param picId
	 * @return
	 * 修改历史：
	 */
	public CmcPtPic getPic(String picId);
	/**
	 * 获取翻译图片信息
	 * @param picId
	 * @param userId
	 * @return
	 * 修改历史：
	 */
	public CmcPtPic getPic(Long userId, String picId);
	/**
	 * 获取翻译图片信息，含翻译记录、操作日志、留言
	 * @param picId
	 * @return
	 * 修改历史：
	 */
	public CmcPtPic getPicJoinTransLogMsg(String picId);
	/**
	 * 获取图片翻译订单信息，含图片、翻译记录、操作日志、留言
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public CmcROrder getOrderJoin(String orderId);
	/**
	 * 获取翻译图片详细信息
	 * @param picId
	 * @param userId
	 * @return
	 * 修改历史：
	 */
	public TransPicDetailBean getPicDetail(Long userId, String picId);
	/**
	 * 获取翻译图片详细信息
	 * @param picId
	 * @return
	 * 修改历史：
	 */
	public TransPicDealDetailBean getPicDetail4Deal(String picId);
	/**
	 * 添加翻译图片
	 * @param bean
	 * @return
	 * 修改历史：
	 */
	public CmcROrder addPic(String picId, TransPicAddBean bean, TransPriceBean price, String picUrl, String thumbUrl, String serviceType, String platform, String version);
	/**
	 * 机器翻译转人工翻译
	 * @param picId
	 * @param userId
	 * @param requirement
	 * 修改历史：
	 */
	public void toHuman(Long userId, String picId, String requirement, double amount, double couponAmount);
	/**
	 * 保存翻译结果
	 * @param picId
	 * @param trans
	 * @param oper
	 * 修改历史：
	 */
	public void saveTrans(TransPicDealBean dealBean);
	/**
	 * 保存捡漏翻译结果
	 * @param picId
	 * @param trans
	 * @param oper
	 * 修改历史：
	 */
	public void savePickTrans(TransPicDealBean dealBean);
	/**
	 * 完成翻译
	 * @param picId
	 * @param trans
	 * @param oper
	 * 修改历史：
	 */
	public void completeTrans(TransPicDealBean dealBean);
	/**
	 * 完成翻译
	 * @param dealBean
	 * @param dealStatus
	 * 修改历史：
	 */
	public void finishTrans(TransPicDealBean dealBean, String dealStatus);
	/**
	 * 保存翻译结果
	 * @param dealBean
	 * @param dealStatus
	 */
	public void saveTransResult(TransPicDealBean dealBean, String dealStatus);
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
	 * 旋转图片
	 * @param picId
	 * @param degree
	 * @param oper
	 * @return
	 * 修改历史：
	 */
	public boolean rotatePicAndSave(String picId, Integer degree, Operator oper);
	/**
	 * 旋转图片
	 * @param picId
	 * @param degree
	 * @param oper
	 * @return
	 * 修改历史：
	 */
	public TransPicRotateResult rotatePic(String picId, Integer degree, Operator oper);
	/**
	 * 后台删除图片翻译信息
	 * @param picId
	 * 修改历史：
	 */
	public void delPic(String picId, Operator oper);
}
