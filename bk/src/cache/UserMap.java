package com.qcmz.cmc.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.umc.ws.provide.locator.UserWS;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Component
public class UserMap extends AbstractCacheMap{
	/**
	 * 用户简单信息缓存，<userId, UserSimpleBean>
	 */
	private static Map<Long, UserSimpleBean> userSimpleMap = new HashMap<Long, UserSimpleBean>();
	/**
	 * 用户简单信息缓存，<serviceType|fullMobile, UserSimpleBean>
	 */
	private static Map<String, UserSimpleBean> mobileUserSimpleMap = new HashMap<String, UserSimpleBean>();
	
	@Override
	protected void init() {
		userSimpleMap.clear();
		mobileUserSimpleMap.clear();
	}

	/**
	 * 获取用户信息
	 * @param userIds
	 * @return
	 */
	public static Map<Long, UserSimpleBean> findUser(Set<Long> userIds){
		Map<Long, UserSimpleBean> result = new HashMap<Long, UserSimpleBean>();
		
		if(userIds==null ||userIds.isEmpty()) return result;
		
		Set<Long> umcUserIds = new HashSet<Long>();
		UserSimpleBean bean = null;
		for (Long userId : userIds) {
			bean = userSimpleMap.get(userId); 
			if(bean!=null){
				result.put(userId, bean);
			}else{
				umcUserIds.add(userId);
			}
		}
		
		if(!umcUserIds.isEmpty()){
			List<UserSimpleBean> users = UserWS.findUserSimple(userIds);
			for (UserSimpleBean user : users) {
				result.put(user.getUid(), user);
				
				userSimpleMap.put(user.getUid(), user);				
				if(StringUtil.notBlankAndNull(user.getFullMobile())){
					mobileUserSimpleMap.put(getKey2(user.getServiceType(), user.getFullMobile()), user);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 获取用户信息
	 * @param userId not null
	 * @return
	 */
	public static UserSimpleBean getUser(Long userId){		
		UserSimpleBean user = userSimpleMap.get(userId); 
		if(user!=null) return user;
		
		user = UserWS.getUserSimple(userId);
		if(user!=null){
			userSimpleMap.put(user.getUid(), user);
			if(StringUtil.notBlankAndNull(user.getFullMobile())){
				mobileUserSimpleMap.put(getKey2(user.getServiceType(), user.getFullMobile()), user);
			}
		}
		return user;
	}
	
	/**
	 * 获取用户信息
	 * @param serviceType not null
	 * @param fullMobile not null
	 * @return
	 */
	public static UserSimpleBean getUser(String serviceType, String fullMobile){
		if(StringUtil.isBlankOrNull(serviceType) || StringUtil.isBlankOrNull(fullMobile)) return null;
		
		if(fullMobile.length()==11 && !fullMobile.contains("-")){
			fullMobile = "86-"+fullMobile;
		}
		
		String key2 = getKey2(serviceType, fullMobile);
		UserSimpleBean user = mobileUserSimpleMap.get(key2); 
		if(user!=null) return user;
		
		user = UserWS.getUserSimple(serviceType, fullMobile);
		if(user!=null){
			userSimpleMap.put(user.getUid(), user);
			mobileUserSimpleMap.put(key2, user);
		}
		
		return user;
	}
	
	private static String getKey2(String serviceType, String mobile){
		return new StringBuilder(serviceType).append("|").append(mobile).toString();
	}
	
	@Override
	public void update(Object obj) {
	}

	@Override
	public void delete(Object obj) {
	}

}
