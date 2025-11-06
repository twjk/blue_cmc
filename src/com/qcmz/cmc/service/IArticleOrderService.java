package com.qcmz.cmc.service;

import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.ws.provide.vo.ArticleSubBean;
import com.qcmz.cms.ws.provide.vo.ArticleItemBean;

public interface IArticleOrderService {
	/**
	 * 资讯订阅
	 * @param orderId
	 * @param bean
	 * @param item
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public CmcROrder subArticle(String orderId, ArticleSubBean bean, ArticleItemBean item, String serviceType, String subServiceType, Integer orderType, String platform, String version);
}
