package com.qcmz.cmc.proxy.trans;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.qcmz.cmc.cache.ProxyAccountMap;
import com.qcmz.cmc.cache.ProxyLangMap;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.entity.CmcProxyAccount;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 谷歌翻译，不用翻墙
 */
@Component
public class GoogleTransProxy extends AbstractTransProxy {
	@Autowired
	private ProxyLangMap proxyLangMap;
	@Autowired
	private ProxyAccountMap proxyAccountMap;
	
	private Logger logger = Logger.getLogger(GoogleTransProxy.class);
	
	/**
	 * 翻译服务地址
	 */
	private static String TRANS_SERVER = "https://translation.googleapis.com/language/translate/v2";

	private static String FORMAT = "text";

	public GoogleTransProxy() {
		super();
		proxyId = BusinessConstant.PROXYID_GOOGLE;
	}
	
	/**
	 * 翻译
	 * https://cloud.google.com/translate/docs/reference/translate?hl=zh-cn
	 * 谷歌接口：参数有误时返回[400]Bad Request
	 * @param from 源语言
	 * @param to 目标语言
	 * @param src 待翻译内容
	 * @return
	 * 修改历史：
	 */
	public String trans(String from, String to, String src) {
		StringBuilder sbHint = new StringBuilder().append(from).append("->").append(to).append("[").append(src).append("]");
		try {
			//帐户
			CmcProxyAccount account = proxyAccountMap.getAccount4Java(proxyId);
			
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("q", URLEncoder.encode(dealSrc(src), "utf-8"));
			paramsMap.put("source", proxyLangMap.getBean(proxyId, from).getMtcode());
			paramsMap.put("target", proxyLangMap.getBean(proxyId, to).getMtcode());
			paramsMap.put("format", FORMAT);
			paramsMap.put("key", account.getAccount());
			
//			String json = HttpUtil.post(TRANS_SERVER, HttpUtil.map2Params(paramsMap), SystemUtil.getProxy());
			String json = HttpUtil.post(TRANS_SERVER, HttpUtil.map2Params(paramsMap));

			GoogleTransResp resp = JSON.parseObject(json, GoogleTransResp.class);
			
			if(resp==null){
				logger.error("解析失败："+json);
			}else if(resp.getData()!=null 
					&& resp.getData().getTranslations()!=null 
					&& !resp.getData().getTranslations().isEmpty()){				
				return dealDst(resp.getData().getTranslations().get(0).getTranslatedText());
			}
		} catch (Exception e) {
			logger.error(sbHint+"翻译失败：", e);
		}
		return null;
	}
	
	/**
	 * 原文处理
	 * 中翻英时，如果原文带有@或#则部分内容会无法翻译，这些特殊字符替换翻译为后再恢复
	 * @param src
	 * @return
	 */
	private static String dealSrc(String src){
		if(StringUtil.notBlankAndNull(src)){
			return src.replace("@", " (,)").replace("#", "(.)"); 
		}
		return src;
	}
	
	private static String dealDst(String dst){
		if(StringUtil.isBlankOrNull(dst)) return dst;
	
		dst = dst.replace("(, ) ", "@").replace("(,) ", "@").replace("(,)", "@")
				 .replace("(. )", "#").replace("(.)", "#")
				 ;
		
		dst = dst.replace("[嘻嘻]", "[hee hee]")
				.replace("[嘻laugh]", "[hee hee]")				
				.replace("[哈哈]", "[ha ha]")
				.replace("[呵呵]", "[he he]")
				.replace("[馋嘴]", "[glutton]")
				.replace("[耶]", "[yay]")				
				.replace("[鬼脸二]", "[grimace II]")
				.replace("[亲亲]", "[kiss]")
				.replace("[热吻]", "[hot kiss]")
				.replace("[亲]", "[dear]")
				.replace("[飞吻]", "[kiss]")
				.replace("[心]", "[heart]")				
				.replace("[花心]", "[fa]")
				.replace("[黄心]", "[yellow heart]")
				.replace("[绿心]", "[green heart]")
				.replace("[红心]", "[red heart]")
				.replace("[紫心]", "[purple heart]")
				.replace("[蓝心]", "[blue heart]")				
				.replace("[心碎]", "[heartbreak]")
				.replace("[花痴]", "[flower nut]")
				.replace("[星星]", "[stars]")
				.replace("[星光]", "[starlight]")
				.replace("[笑]", "[laughter]")
				.replace("[大笑]", "[laughter]")
				.replace("[笑笑]", "[laughter]")
				.replace("[狂笑]", "[laughter]")
				.replace("[爱心发光]", "[love shines]")
				.replace("[爱心]", "[love]")
				.replace("[可爱]", "[lovely]")
				.replace("[爱你]", "[love you]")
				.replace("[爱]", "[love]")
				.replace("[强壮]", "[strong]")
				.replace("[强强]", "[strong]")
				.replace("[胜利]", "[victory]")
				.replace("[卡门]", "[carmen]")
				.replace("[礼花]", "[fireworks]")
				.replace("[烟花]", "[fireworks]")
				.replace("[蛋糕]", "[cake]")
				.replace("[微博蛋糕]", "[microblog cake]")
				.replace("[礼物]", "[gift]")
				.replace("[猪头]", "[pig head]")
				.replace("[鼓掌]", "[applause]")
				.replace("[抱抱]", "[hug]")
				.replace("[小嘴]", "[small mouth]")
				.replace("[萌]", "[meng]")
				.replace("[萌翻]", "[meng]")
				.replace("[傻笑]", "[giggle]")
				.replace("[惊恐]", "[panic]")
				.replace("[吐舌头]", "[tongue sticking]")
				.replace("[鄙视]", "[despise]")
				.replace("[哼]", "[humph]")
				.replace("[哼]", "[humph]")
				.replace("[哼]", "[hum hum]")
				.replace("[哼哼]", "[hum hum]")
				.replace("[Right 哼哼]", "[right hum hum]")
				.replace("[right 哼哼]", "[right hum hum]")
				.replace("[右哼哼]", "[right hum hum]")
				.replace("[Left 哼哼]", "[left hum hum]")
				.replace("[left 哼哼]", "[left hum hum]")
				.replace("[左哼哼]", "[left hum hum]")
				.replace("[张嘴]", "[open mouth]")
				.replace("[神马]", "[what]")
				.replace("[浮云]", "[cloud]")
				.replace("[衰]", "[decline]")
				.replace("[怒]", "[angry]")
				.replace("[怒吼]", "[angry]")
				.replace("[噢耶]", "[oh yeah]")
				.replace("[囧]", "[oops]")
				.replace("[黑线]", "[black line]")
				.replace("[晕]", "[gosh]")
				.replace("[瞌睡]", "[sleepy]")
				.replace("[困]", "[sleepy]")
				.replace("[困困]", "[sleepy]")
				.replace("[睡]", "[sleep]")
				.replace("[打哈气]", "[sleepy]")
				.replace("[打气气]", "[sleepy]")
				.replace("[打气]", "[come on]")
				.replace("[加油啊]", "[come on]")
				.replace("[骷髅]", "[skull]")
				.replace("[喵呜]", "[meow]")
				.replace("[太阳]", "[sun]")
				.replace("[月亮]", "[moon]")
				.replace("[幽灵]", "[ghost]")
				.replace("[猫]", "[cat]")
				.replace("[吐吐]", "[vomiting]")
				.replace("[吐]", "[vomit]")
				.replace("[嘘]", "[Shh]")
				.replace("[得意]", "[proud]")
				.replace("[花]", "[flower]")
				.replace("[花花]", "[flower]")
				.replace("[春暖花开]", "[spring blossoms]")
				.replace("[玫瑰]", "[rose]")
				.replace("[揪耳]", "[tug of ear]")
				.replace("[嘘]", "[Shh]")
				.replace("[四叶草]", "[clover]")
				.replace("[威武]", "[mighty]")
				.replace("[欢欢]", "[huanhuan]")
				.replace("[乐乐]", "[lele]")
				.replace("[打气]", "[cheer]")
				.replace("[赞]", "[awesome]")
				.replace("[抓狂]", "[crazy]")
				.replace("[狂狂]", "[crazy]")
				.replace("[呲牙]", "[bared teeth]")
				.replace("[吼吼]", "[roar]")				
				.replace("[咆哮]", "[roar]")
				.replace("[大惊]", "[frightened]")
				.replace("[太开心]", "[too happy]")
				.replace("[太快乐]", "[too happy]")
				.replace("[喜]", "[happy]")
				.replace("[猥琐]", "[wretched]")
				.replace("[擂鼓]", "[drums]")
				.replace("[雪]", "[snow]")
				.replace("[泪]", "[tears]")
				.replace("[飙泪]", "[tears]")
				.replace("[飙泪中]", "[tears]")
				.replace("[飙 Tears]", "[tears]")
				.replace("[大哭]", "[Cry]")
				.replace("[呜呜]", "[whining]")
				.replace("[骂骂]", "[scolding]")
				.replace("[怒骂]", "[scolding]")
				.replace("[笑眯眯]", "[smiling]")
				.replace("[熊猫]", "[panda]")
				.replace("[瞎眼]", "[blind]")
				.replace("[眨眼]", "[blinking]")
				.replace("[白眼]", "[white eye]")
				.replace("[媚眼]", "[glad eye]")
				.replace("[舔]", "[licking]")
				.replace("[兔子]", "[rabbit]")
				.replace("[兔]", "[rabbit]")				
				.replace("[酷]", "[cool]")
				.replace("[汗]", "[sweat]")
				.replace("[手套]", "[gloves]")
				.replace("[小风]", "[breeze]")
				.replace("[给力]", "[awesome]")
				.replace("[饭团]", "[rice ball]")
				.replace("[贬低]", "[belittle]")
				.replace("[红桃]", "[red peach]")
				.replace("[来]", "[come]")
				.replace("[昂]", "[ang]")
				.replace("[钱]", "[money]")
				.replace("[有钱]", "[rich]")
				.replace("[招财]", "[inviting wealth]")
				.replace("[金元宝]", "[gold ingot]")
				.replace("[奥]", "[oh]")
				.replace("[害羞]", "[shy]")
				.replace("[委屈]", "[grievance]")
				.replace("[做鬼脸]", "[make faces]")
				.replace("[奥特曼]", "[Altman]")
				.replace("[福到啦]", "[blessing]")
				.replace("[龙啸]", "[dragon whistle]")
				.replace("[霹雳]", "[thunderbolt]")
				.replace("[咖啡]", "[coffee]")
				.replace("曰", " said ")
				.replace("艹", " Fuck! ")
				.replace("嘿嘿", " hey ")
				.replace("嘿", " hey ")
				.replace("嘻嘻", " hee hee ")
				.replace("嘻", " hee ")
				.replace("吼吼", " roar ")
				.replace("吼", " roar ")
				.replace("呜呜", " whining ")
				.replace("呜", " whining ")
				.replace("嘎嘎", " quack ")
				.replace("嘎", " quack ")
				.replace("哼", " hum ")
				.replace("囧", " oops ")
				.replace("奥", " oh ")
				.replace("蛤", " ha ")
				.replace("哎", " hey ")
				.replace("唉", " alas ")
				.replace("呃", " uh ")
				.replace("呗", "")
				;
		return dst;
	}
	
	static class GoogleTransResp{
		private GoogleData data;
		public GoogleData getData() {
			return data;
		}
		public void setData(GoogleData data) {
			this.data = data;
		}
	}
	
	static class GoogleData{
		private List<GoogleTransBean> translations;
		public List<GoogleTransBean> getTranslations() {
			return translations;
		}
		public void setTranslations(List<GoogleTransBean> translations) {
			this.translations = translations;
		}
	}
	
	static class GoogleTransBean{
		private String translatedText;
		public String getTranslatedText() {
			return translatedText;
		}
		public void setTranslatedText(String translatedText) {
			this.translatedText = translatedText;
		}
	}	
}
