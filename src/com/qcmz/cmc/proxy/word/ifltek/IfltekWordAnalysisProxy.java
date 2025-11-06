package com.qcmz.cmc.proxy.word.ifltek;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.qcmz.framework.util.HttpUtil;

/**
 * 类说明：
 * 官网：http://www.xfyun.cn/index.php/services/ltp?tab_index=0
 * 语言分析介绍及词性标记等：http://www.xfyun.cn/index.php/services/ltp?tab_index=1 
 * 	v代表动词、n代表名词、c代表连词、d代表副词、wp代表标点符号 u代词
 * API:http://www.xfyun.cn/index.php/services/ltp?tab_index=2
 * 修改历史：
 */
public class IfltekWordAnalysisProxy{
	
	private static Logger logger = Logger.getLogger(IfltekWordAnalysisProxy.class);
	
	private static String SERVER = "http://ltpapi.voicecloud.cn/analysis/";
	private static String APIKEY = "0144G3R5D6B4y484v0b5BdCnrX3y7F3DYmsLrxwZ";
	
	
	/**
	 * v代表动词、n代表名词、c代表连词、d代表副词、wp代表标点符号
	 * @param text
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public static List<String> analysis(String text){
		List<String> result = new ArrayList<String>();
		String resp = null;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("api_key", APIKEY);
			map.put("format", "xml");	//xml(XML格式)，json(JSON格式)，conll(CONLL格式)，plain(简洁文本格式)
			map.put("pattern", "pos");	//分析模式，可选值包括ws(分词)，pos(词性标注)，ner(命名实体识别)，dp(依存句法分析)，srl(语义角色标注),all(全部任务)
			map.put("text", URLEncoder.encode(text,"utf-8"));
			resp = HttpUtil.post(SERVER, HttpUtil.map2Params(map));
			
			Document doc = DocumentHelper.parseText(resp);
			Element root = doc.getRootElement();
			
			String pos = null;
			String word = null;
			List<Element> list = root.selectNodes("//word");
			for (Element element : list) {
				pos = element.attribute("pos").getValue();
				if("wp".equals(pos) || "u".equals(pos)){
					continue;	//抛弃标点符号/代词
				}
				word = element.attribute("cont").getValue();
				if(word==null || "".equals(word.trim())){
					continue;
				}
				result.add(word);
			}
			
		} catch (DocumentException e) {
			logger.error("解析xml失败："+resp, e);
		} catch (Exception e) {
			logger.error("分词失败："+text, e);
		}
		return result;
	}
}
