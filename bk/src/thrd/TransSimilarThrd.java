package com.qcmz.cmc.thrd;

import com.qcmz.cmc.service.ITransSimilarService;
import com.qcmz.framework.thrd.AbstractThrd;

/**
 * 类说明：翻译相似度计数线程
 * 修改历史：
 */
public class TransSimilarThrd extends AbstractThrd {
	private ITransSimilarService transSimilarService;
	private String from;
	private String to;
	private Long proxyId;
	private Integer similar;
	private Integer count;
	
	public TransSimilarThrd(ITransSimilarService transSimilarService,
			String from, String to, Long proxyId, Integer similar, Integer count) {
		super();
		this.transSimilarService = transSimilarService;
		this.from = from;
		this.to = to;
		this.proxyId = proxyId;
		this.similar = similar;
		this.count = count;
	}

	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		transSimilarService.save(from, to, proxyId, similar, count);
	}

	public static void start(ITransSimilarService transSimilarService, String from, String to, Long proxyId,
			Integer similar, Integer count){
		TransSimilarThrd countThrd = new TransSimilarThrd(transSimilarService, from, to, proxyId, similar, count);
		Thread thrd = new Thread(countThrd);
		thrd.start();
	}
	
}
