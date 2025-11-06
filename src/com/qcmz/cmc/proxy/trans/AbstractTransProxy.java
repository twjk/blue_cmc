package com.qcmz.cmc.proxy.trans;

import com.qcmz.cmc.proxy.AbstractProxy;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public abstract class AbstractTransProxy extends AbstractProxy {
	/**
	 * 翻译
	 * @param from 源语言
	 * @param to 目标语言
	 * @param src 待翻译内容
	 * @return
	 * 修改历史：
	 */
	public abstract String trans(String from, String to, String src);
}
