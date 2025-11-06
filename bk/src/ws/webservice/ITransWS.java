package com.qcmz.cmc.ws.provide.webservice;

import com.qcmz.cmc.ws.provide.vo.TransFavAddReq;
import com.qcmz.cmc.ws.provide.vo.TransFavQryReq;
import com.qcmz.cmc.ws.provide.vo.TransLogReq;
import com.qcmz.cmc.ws.provide.vo.TransPermitReq;
import com.qcmz.cmc.ws.provide.vo.TransPermitResp;
import com.qcmz.cmc.ws.provide.vo.TransReq;
import com.qcmz.cmc.ws.provide.vo.TransResp;
import com.qcmz.framework.ws.vo.PageBeanResponse;
import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：翻译接口
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Jun 4, 2014 5:28:13 PM
 * 修改历史：
 */
public interface ITransWS {
	/**
	 * 校验翻译许可
	 * @param req
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Sep 4, 2014 3:37:40 PM
	 * 修改历史：
	 */
	public TransPermitResp canTrans(TransPermitReq req);
	/**
	 * 翻译，不校验接口权限，使用新接口translate
	 * @param req
	 * @return
	 * @version 1.0
	 * 创建日期：Sep 22, 2014 7:04:33 PM
	 * 修改历史：
	 */
	@Deprecated
	public TransResp trans(TransReq req);
	/**
	 * 翻译，校验接口权限，替换trans接口
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public TransResp translate(TransReq req);
	/**
	 * 保存翻译日志
	 * @param apiType
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Aug 29, 2014 2:16:15 PM
	 * 修改历史：
	 */
	public Response saveTransLog(TransLogReq req);
	/**
	 * 收藏翻译结果
	 * @param req
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Sep 28, 2014 10:00:17 AM
	 * 修改历史：
	 */
	public Response favTrans(TransFavAddReq req);
	/**
	 * 分页查询日常用语
	 * @param req
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Sep 28, 2014 12:20:24 PM
	 * 修改历史：
	 */
	public PageBeanResponse findDaily(TransFavQryReq req, String page, String pageSize);
	/**
	 * 分页查询用户收藏
	 * @param req
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Sep 28, 2014 12:20:24 PM
	 * 修改历史：
	 */
	public PageBeanResponse findUserFav(TransFavQryReq req, String page, String pageSize);
}
