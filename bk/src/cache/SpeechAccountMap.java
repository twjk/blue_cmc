package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.cmc.ws.provide.vo.SpeechAccountBean;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.util.EncodeUtil;

/**
 * 类说明：语音帐户缓存
 * 修改历史：
 */
@Component
public class SpeechAccountMap extends AbstractCacheMap {
	private Map<String, List<SpeechAccountBean>> map = null;
	
	private SpeechAccountBean hcicloud = null;
	private SpeechAccountBean ifltek_android = null;
	private SpeechAccountBean ifltek_ios = null;
	private SpeechAccountBean nuance = null;
	private SpeechAccountBean nuanceQiaoyu = null;
	private SpeechAccountBean nuanceChuxing = null;
	
	private static String AUTHACCOUNT_TRANS_ANDROID = "trans_android";
	private static String AUTHACCOUNT_TRANS_IOS = "trans";
	
	private Map<Integer, SpeechAccountBean> nuanceMap = new HashMap<Integer, SpeechAccountBean>();
//	private static String AUTHACCOUNT_TRANS_XIAOMI = "xiaomi";
	
	/** 
	 * 
	 * 修改历史：
	 */
	@Override @PostConstruct
	protected void init() {
		
		map = new HashMap<String, List<SpeechAccountBean>>();
		map.put(AUTHACCOUNT_TRANS_ANDROID, new ArrayList<SpeechAccountBean>());
		map.put(AUTHACCOUNT_TRANS_IOS, new ArrayList<SpeechAccountBean>());
		
		//灵云
		hcicloud = new SpeechAccountBean();
		hcicloud.setProxyId(1);
		hcicloud.setAccount(CmcUtil.encrypt("035d547d"));
		hcicloud.setKey(CmcUtil.encrypt("7fb8127ac72146b6bbb88b8f99d562bc"));
		hcicloud.setServer("api.hcicloud.com");
		hcicloud.setPort("8888");
		map.get(AUTHACCOUNT_TRANS_ANDROID).add(hcicloud);
		map.get(AUTHACCOUNT_TRANS_IOS).add(hcicloud);
		
		
		//科大讯飞
		ifltek_android = new SpeechAccountBean();
		ifltek_android.setProxyId(2);
		ifltek_android.setAccount(CmcUtil.encrypt("559231f5"));
		map.get(AUTHACCOUNT_TRANS_ANDROID).add(ifltek_android);
		
		ifltek_ios = new SpeechAccountBean();
		ifltek_ios.setProxyId(2);
		ifltek_ios.setAccount(CmcUtil.encrypt("535a0a36"));
		map.get(AUTHACCOUNT_TRANS_IOS).add(ifltek_ios);
		
		//Nuance qcmuzhi
		byte[] nuanceKey = {
				(byte) 0xc4, (byte) 0x1a, (byte) 0xc4, (byte) 0xce, (byte) 0xbd,
				(byte) 0x64, (byte) 0x91, (byte) 0x85, (byte) 0x64, (byte) 0x5b,
				(byte) 0xd6, (byte) 0x30, (byte) 0xb9, (byte) 0x59, (byte) 0xbf,
				(byte) 0x02, (byte) 0x4c, (byte) 0xc9, (byte) 0x65, (byte) 0x6a,
				(byte) 0xbd, (byte) 0xb2, (byte) 0x8c, (byte) 0xb4, (byte) 0x53,
				(byte) 0x32, (byte) 0xb6, (byte) 0x31, (byte) 0xbc, (byte) 0x62,
				(byte) 0xa5, (byte) 0x80, (byte) 0x7c, (byte) 0x69, (byte) 0x4e,
				(byte) 0x39, (byte) 0xce, (byte) 0xdf, (byte) 0xbf, (byte) 0x32,
				(byte) 0x3b, (byte) 0x95, (byte) 0x62, (byte) 0xf4, (byte) 0xef,
				(byte) 0x22, (byte) 0xf7, (byte) 0x3b, (byte) 0xeb, (byte) 0x5c,
				(byte) 0xa5, (byte) 0xa8, (byte) 0x0f, (byte) 0x31, (byte) 0x5d,
				(byte) 0x62, (byte) 0x63, (byte) 0x13, (byte) 0x93, (byte) 0x1f,
				(byte) 0x75, (byte) 0x38, (byte) 0x9f, (byte) 0xf2
			};
		nuance = new SpeechAccountBean();
		nuance.setProxyId(3);
		nuance.setAccount(CmcUtil.encrypt("NMDPPRODUCTION________________________________________________20140930030029"));
		nuance.setKey(CmcUtil.encrypt(nuanceKey));
		nuance.setServer("bwm.nmdp.nuancemobility.net");
		nuance.setPort("443");
		nuanceMap.put(1, nuance);
		
		//Nuance 巧语
		byte[] nuanceKeyQiaoyu = {
			(byte) 0x29, (byte) 0x55, (byte) 0x89, (byte) 0x55, (byte) 0xa6, (byte) 0xb7,
			(byte) 0xa9, (byte) 0x77, (byte) 0xfc, (byte) 0xfe, (byte) 0xce, (byte) 0xdc,
			(byte) 0x9a, (byte) 0x4c, (byte) 0x51, (byte) 0x88, (byte) 0xfc, (byte) 0xd9,
			(byte) 0x3e, (byte) 0x8c, (byte) 0x1e, (byte) 0xd5, (byte) 0xa1, (byte) 0x67,
			(byte) 0x12, (byte) 0xaa, (byte) 0x47, (byte) 0xce, (byte) 0x98, (byte) 0xca,
			(byte) 0x0c, (byte) 0xc1, (byte) 0x1f, (byte) 0x96, (byte) 0x16, (byte) 0xc5,
			(byte) 0x4d, (byte) 0xc0, (byte) 0x3c, (byte) 0x49, (byte) 0xbe, (byte) 0x0b,
			(byte) 0x25, (byte) 0x59, (byte) 0x57, (byte) 0x77, (byte) 0xe1, (byte) 0x53,
			(byte) 0x50, (byte) 0x1f, (byte) 0xdf, (byte) 0x6f, (byte) 0xf5, (byte) 0x80,
			(byte) 0xd6, (byte) 0x07, (byte) 0x2a, (byte) 0x1e, (byte) 0xd6, (byte) 0xc2,
			(byte) 0xe8, (byte) 0xe2, (byte) 0x84, (byte) 0x07
		};
		nuanceQiaoyu = new SpeechAccountBean();
		nuanceQiaoyu.setProxyId(3);
		nuanceQiaoyu.setAccount(CmcUtil.encrypt("NMDPPRODUCTION_LEI_LIANG________20150719231252"));
		nuanceQiaoyu.setKey(CmcUtil.encrypt(nuanceKeyQiaoyu));
		nuanceQiaoyu.setServer("dzj.nmdp.nuancemobility.net");
		nuanceQiaoyu.setPort("443");
		nuanceMap.put(2, nuanceQiaoyu);
		
		//Nuance 出行翻译官
		byte[] nuanceKeyChuxing = {
				(byte)0x9c, (byte)0x40, (byte)0x00, (byte)0x41, (byte)0x69, 
				(byte)0xeb, (byte)0x1c, (byte)0xf4, (byte)0xae, (byte)0x1a, 
				(byte)0xdc, (byte)0xf4, (byte)0xad, (byte)0xe6, (byte)0xd0, 
				(byte)0x24, (byte)0x39, (byte)0x9a, (byte)0xfc, (byte)0xcd, 
				(byte)0x7a, (byte)0x8c, (byte)0x56, (byte)0xee, (byte)0xba, 
				(byte)0x80, (byte)0x94, (byte)0xf8, (byte)0xc9, (byte)0x5a, 
				(byte)0xeb, (byte)0x66, (byte)0x9b, (byte)0xeb, (byte)0xa7, 
				(byte)0x04, (byte)0xd7, (byte)0xc4, (byte)0xf9, (byte)0x85, 
				(byte)0xde, (byte)0xd5, (byte)0x79, (byte)0x8c, (byte)0xbc, 
				(byte)0x49, (byte)0xb0, (byte)0xf5, (byte)0xe3, (byte)0xad, 
				(byte)0xc2, (byte)0x5d, (byte)0x39, (byte)0x19, (byte)0x4c, 
				(byte)0xf3, (byte)0x99, (byte)0xe0, (byte)0xeb, (byte)0x62, 
				(byte)0x3a, (byte)0xd7, (byte)0xc8, (byte)0xd5
			};
		nuanceChuxing = new SpeechAccountBean();
		nuanceChuxing.setProxyId(3);
		nuanceChuxing.setAccount(CmcUtil.encrypt("NMDPPRODUCTION_Lee_Xinyi_________________20160804225221"));
		nuanceChuxing.setKey(CmcUtil.encrypt(nuanceKeyChuxing));
		nuanceChuxing.setServer("jqy.nmdp.nuancemobility.net");
		nuanceChuxing.setPort("443");
		nuanceMap.put(3, nuanceChuxing);
		
		map.get(AUTHACCOUNT_TRANS_IOS).add(nuanceMap.get(TransConfig.NUANCEACCOUNT_IOS));
		map.get(AUTHACCOUNT_TRANS_ANDROID).add(nuanceMap.get(TransConfig.NUANCEACCOUNT_ANDROID));
//		map.put(AUTHACCOUNT_TRANS_XIAOMI, map.get(AUTHACCOUNT_TRANS_ANDROID));
	}

	public static void main(String[] args) {
		byte[] nuanceKey = {
				(byte)0x9c, (byte)0x40, (byte)0x00, (byte)0x41, (byte)0x69, 
				(byte)0xeb, (byte)0x1c, (byte)0xf4, (byte)0xae, (byte)0x1a, 
				(byte)0xdc, (byte)0xf4, (byte)0xad, (byte)0xe6, (byte)0xd0, 
				(byte)0x24, (byte)0x39, (byte)0x9a, (byte)0xfc, (byte)0xcd, 
				(byte)0x7a, (byte)0x8c, (byte)0x56, (byte)0xee, (byte)0xba, 
				(byte)0x80, (byte)0x94, (byte)0xf8, (byte)0xc9, (byte)0x5a, 
				(byte)0xeb, (byte)0x66, (byte)0x9b, (byte)0xeb, (byte)0xa7, 
				(byte)0x04, (byte)0xd7, (byte)0xc4, (byte)0xf9, (byte)0x85, 
				(byte)0xde, (byte)0xd5, (byte)0x79, (byte)0x8c, (byte)0xbc, 
				(byte)0x49, (byte)0xb0, (byte)0xf5, (byte)0xe3, (byte)0xad, 
				(byte)0xc2, (byte)0x5d, (byte)0x39, (byte)0x19, (byte)0x4c, 
				(byte)0xf3, (byte)0x99, (byte)0xe0, (byte)0xeb, (byte)0x62, 
				(byte)0x3a, (byte)0xd7, (byte)0xc8, (byte)0xd5
			};
		String str = EncodeUtil.byte2HexStr(nuanceKey);
		System.out.println(str);
		byte[] aa = EncodeUtil.hexStr2Bytes(str);
		System.out.println(nuanceKey[0]+","+aa[0]);
	}
	
	/**
	 * 获取所有帐户信息
	 * @return
	 * 修改历史：
	 */
	public List<SpeechAccountBean> findAll(String authAccount){
		if(map==null) 
			init();
		return map.get(authAccount);
	}
	
	/** 
	 * @param obj
	 * 修改历史：
	 */
	@Override
	public void delete(Object obj) {
	}


	/** 
	 * @param obj
	 * 修改历史：
	 */
	@Override
	public void update(Object obj) {
	}

}
