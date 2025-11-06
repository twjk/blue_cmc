package com.qcmz.cmc.comparator;

import java.util.Comparator;

import com.qcmz.cmc.proxy.spider.weixin.vo.ArticleListBean;

/**
 * 类说明：appmsgid倒序,aid正序
 * 修改历史：
 */
public class WeixinArticleListComparator implements Comparator<ArticleListBean> {
	/** 
	 * @param o1
	 * @param o2
	 * @return
	 */
	@Override
	public int compare(ArticleListBean o1, ArticleListBean o2) {
		if(o1.getAppmsgid()>o2.getAppmsgid()){
			return 1;
		}else if(o1.getAppmsgid()<o2.getAppmsgid()){
			return -1;
		}else{
			return o2.getAid().compareTo(o1.getAid());
		}
	}

}
