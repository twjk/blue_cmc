package com.qcmz.cmc.process;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.entity.CmcSLock;
import com.qcmz.cmc.service.ILockService;
import com.qcmz.framework.util.IPUtil;

@Component
public class LockProcess {
	@Autowired
	private ILockService lockService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 添加定时任务锁
	 * 1.添加时，锁有效期设置为2小时
	 * 2.待处理完业务再更新实际有效期
	 * 3.为了避免异常宕机导致未能完成更新实际有效期或者解锁
	 * 	 对于当前服务器直接解锁
	 *   其他服务器，通过new Socket(lock.getCreateip(), SERVER_PORT)检查服务是否正常，不正常时解锁（由于服务器不在同一局域网内，并且未能获取外网IP，故该功能未实现）
	 * @param lockType
	 * @param lockKey
	 * @return
	 * 修改历史：
	 */
	public Long addLock4Job(String lockType, String lockKey){
		try {
			String ip = IPUtil.LOCAL_IP;
			CmcSLock lock = lockService.getLock(lockType, lockKey);
			boolean locked = false;
			if(lock!=null){
				locked = true;
				if(ip.equals(lock.getCreateip())
						|| lock.getExpiretime().getTime()<System.currentTimeMillis()){
					//当前服务器直接解锁
					lockService.deleteLock(lock.getLockid());
					locked = false;
				}
			}
			if(!locked){
				try {
					return lockService.addLock(lockType, lockKey, 7200, ip);
				} catch (Exception e) {}
			}
		} catch (Exception e) {
			StringBuilder sbLog = new StringBuilder("加锁失败：").append(lockType).append("-").append(lockKey);
			logger.error(sbLog.toString(), e);
		}
		return null;
	}
	
	/**
	 * 添加锁，以避免同一时间内重复操作
	 * 1.添加时，锁有效期设置为30秒
	 * 2.待处理完业务解锁；
	 * @param lockType
	 * @param lockKey
	 * @return
	 * 修改历史：
	 */
	public Long addLock4Unique(String lockType, String lockKey){
		try {
			String ip = IPUtil.LOCAL_IP;
			CmcSLock lock = lockService.getLock(lockType, lockKey);
			if(lock==null){
				return lockService.addLock(lockType, lockKey, 30, ip);
			}
		} catch (Exception e) {
			StringBuilder sbLog = new StringBuilder("加锁失败：").append(lockType).append("-").append(lockKey);
			logger.error(sbLog.toString(), e);
		}
		return null;
	}
	
	/**
	 * 更新失效时间
	 * @param lockId
	 * @param validTime，秒
	 */
	public void updateExpireTime(Long lockId, int validTime){
		lockService.updateExpireTime(lockId, validTime);
	}
	
	/**
	 * 更新失效时间，10分钟后
	 * @param lockId
	 */
	public void updateExpireTime(Long lockId){
		lockService.updateExpireTime(lockId, 600);
	}
}
