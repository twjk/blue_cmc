package com.qcmz.cmc.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.qcmz.bdc.ws.provide.locator.EmojiWS;
import com.qcmz.bdc.ws.provide.vo.EmojiBean;
import com.qcmz.framework.util.AuthUtil;
import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：表情缓存
 * @author 李炳煜
 * 修改历史：
 */
@Component
public class EmojiMap {
	/**
	 * 表情缓存，<unicode, 表情信息>
	 */
	private static Map<String, EmojiBean> map;
	
	/** 
	 * 初始化
	 * @author 李炳煜
	 * 修改历史：
	 */
	private static void init() {
		Map<String, EmojiBean> temp = new HashMap<String, EmojiBean>();
		Request req = new Request();
		req.setAuthAccount(AuthUtil.AUTH_ACCOUNT);
		req.setAuthToken(AuthUtil.getAuthToken());
		List<EmojiBean> list = EmojiWS.findEmoji(req);
		for (EmojiBean bean : list) {
			temp.put(bean.getUnicode(), bean);
		}
		map = temp;
	}
	
	/**
	 * 重载缓存
	 * @author 李炳煜
	 * 修改历史：
	 */
	public static void refresh(){
		init();
	} 
	
	/**
	 * 获取表情信息
	 * @param unicode
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public static EmojiBean getBean(String unicode){
		if(map==null){
			init();
		}
		return map.get(unicode);
	}
	
	/**
	 * emoji转图片
	 * @param width
	 * @param height
	 * @param imgpath
	 * @param input
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Oct 30, 2014 8:36:22 PM
	 * 修改历史：
	 */
	public static String convert(String input) {
		if(input==null || "".equals(input)) return input;
		
        StringBuilder result = new StringBuilder();
        EmojiBean emoji = null;
        int[]codePoints = toCodePointArray(input);
        for(int i = 0; i < codePoints.length; i++){
        	String key1 = Integer.toHexString(codePoints[i]);
            if(i + 1 < codePoints.length){
            	String key2 = Integer.toHexString(codePoints[i+1]);
            	String key = key1+"-"+key2;
            	emoji = getBean(key);
                if(emoji!=null || "fe0f".equals(key2)){  //处理 iphone5 xxxx-fe0f.png
                    result.append("<img style=\"vertical-align: bottom;\" src=\""+emoji.getPicurl()+"\" alt=\"表情\"/>");
                    i++;
                    continue;
                }
            }
            
            emoji = getBean(key1);
            if(emoji!=null){
                result.append("<img style=\"vertical-align: bottom;\" src=\""+emoji.getPicurl()+"\" alt=\"表情\"/>");
                continue;
            }  
            if("1f611".equals(key1)){  //处理空格
                result.append(" ");
                continue;
            }  
            result.append(Character.toChars(codePoints[i]));
        }  
        return result.toString();
	}
	
	/**
	 * 替换emoj表情
	 * @param input
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Oct 30, 2014 8:40:36 PM
	 * 修改历史：
	 */
	public static String filter(String input, String replace){
		if(input==null || "".equals(input)) return input;
		
		StringBuilder result = new StringBuilder();
		EmojiBean emoji = null;
		
        int[]codePoints = toCodePointArray(input);
        for(int i = 0; i < codePoints.length; i++){
        	String key1 = Integer.toHexString(codePoints[i]);
            if(i + 1 < codePoints.length){
            	String key2 = Integer.toHexString(codePoints[i+1]);
            	String key = key1+"-"+key2;
                
            	emoji = getBean(key);
                if(emoji!=null || "fe0f".equals(key2)){  //处理 iphone5 xxxx-fe0f.png
                	result.append(replace);
                    i++;
                    continue;
                }
            }
            
            emoji = getBean(key1);
            if(emoji!=null){
            	result.append(replace);
                continue;
            }  
            if("1f611".equals(key1)){  //处理空格
                result.append(" ");
                continue;
            }  
            result.append(Character.toChars(codePoints[i]));
        }  
        return result.toString();
	}
	
	/**
	 * 过滤掉emoj表情
	 * @param input
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public static String filter(String input){
		return filter(input, "");
	}
	
	
	private static int[] toCodePointArray(String str) {  
        char[] ach = str.toCharArray();  
        int len = ach.length;  
        int[] acp = new int[Character.codePointCount(ach, 0, len)];  
        int j = 0;  
  
        for (int i = 0, cp; i < len; i += Character.charCount(cp)) {  
            cp = Character.codePointAt(ach, i);  
            acp[j++] = cp;  
        }  
        return acp;  
    }  

}
