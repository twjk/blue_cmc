package com.qcmz.cmc.util;

import java.util.Date;

import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.FilePathUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：用户工具类
 * 修改历史：
 */
public class UserUtil {
	public static String getTransPicBasePath(String orderId, Date createTime){
		String date = DateUtil.formatDateTime2(createTime);
		return new StringBuilder()
			.append("transpic")
			.append("/").append(date.substring(0, 8))
			.append("/").append(date.subSequence(8, 10))
			.append("/").append(OrderUtil.encryptOrderId(orderId))
			.toString()
		;
	}
	public static String getTransPicBasePath(String orderId){
		return getTransPicBasePath(orderId, new Date());
	}
	
	
	/**
	 * 获取用户翻译图片临时路径
	 * @param userId
	 * @param fileName
	 * @return
	 * 修改历史：
	 */
	public static String getTransPicTempPath(Long userId, String fileName){
		return new StringBuilder(FilePathUtil.getAbsolutePath("files"))
			.append(FilePathUtil.FILE_SEP).append("temp")
			.append(FilePathUtil.FILE_SEP).append("u")
			.append(FilePathUtil.FILE_SEP).append(userId)
			.append(FilePathUtil.FILE_SEP).append(fileName)
			.toString()
		;
	}
	
	/**
	 * 获取用户翻译图片临时路径
	 * @param userId
	 * @param fileName
	 * @return
	 * 修改历史：
	 */
	public static String getTransPicTempBasePath(Long userId){
		return new StringBuilder(FilePathUtil.getAbsolutePath("files"))
			.append(FilePathUtil.FILE_SEP).append("temp")
			.append(FilePathUtil.FILE_SEP).append("u")
			.append(FilePathUtil.FILE_SEP).append(userId)
			.toString()
		;
	}
	
	/**
	 * 获取众包任务路径
	 * @param userId not null
	 * @param taskId not null
	 * @param utId
	 * @return
	 */
	public static String getCrowdTaskBasePath(Long userId, Long taskId, String utId){
		String date = DateUtil.formatDateTime2(new Date());
		return new StringBuilder()
			.append("crowdtask")
			.append("/").append(taskId)
			.append("/").append(date.substring(0, 8))
			.append("/").append(date.subSequence(8, 10))
			.append("/").append(userId)
			.append("/").append(StringUtil.notBlankAndNull(utId)?utId:"pretask")
			.toString();
	}
	
	/**
	 * 获取众包任务路径
	 * @param taskId not null
	 * @return
	 */
	public static String getCrowdTaskPath(Long taskId){
		return new StringBuilder().append("crowdtask").append("/").append(taskId).toString();
	}
	
	/**
	 * 获取众包任务题目路径(和ueditor配置一致)
	 * @param orderId
	 * @return
	 */
	public static String getCrowdTaskOrderBasePath(String orderId){
		return new StringBuilder()
			.append("crowdtasksubject")
			.append("/").append(DateUtil.formatDate7(new Date()))
			.toString()
		;
	}
	
	/**
	 * 获取图像识别文件路径，yyMMdd/HH/时间戳+6位随机数.fileType
	 * @param fileType
	 * @return
	 */
	public static String getImageRecognitionFilePath(String fileType){
		Date now = new Date();
		String date = DateUtil.formatDateTime2(now);
		return new StringBuilder()
			.append("imagerecognition")
			.append("/").append(date.substring(2, 8))
			.append("/").append(date.subSequence(8, 10))
			.append("/").append(FilePathUtil.newFileName(fileType))
			.toString();
	}
	
	/**
	 * 获取图像识别文件路径
	 * @return
	 */
	public static String getImageRecognitionBasePath(){
		String date = DateUtil.formatDateTime2(new Date());
		return new StringBuilder()
			.append("imagerecognition")
			.append("/").append(date.substring(2, 8))
			.append("/").append(date.subSequence(8, 10))
			.toString();
	}
	
	/**
	 * 获取图像识别临时文件路径
	 * @param fileName
	 * @return
	 * 修改历史：
	 */
	public static String getImageRecognitionAbsTempFilePath(String filePath){
		return new StringBuilder(FilePathUtil.getAbsolutePath("files"))
			.append(FilePathUtil.FILE_SEP).append("temp")
			.append(FilePathUtil.FILE_SEP).append(filePath)
			.toString();
	}
	
	/**
	 * 红包音频目录
	 * @param packetId
	 * @param receiveUserId
	 * @return
	 */
	public static String getRedPacketVoiceBasePath(String packetId){
		return new StringBuilder()
			.append("redpacket/v")
			.append("/").append(DateUtil.formatDate7(new Date()))
			.append("/").append(packetId)
			.toString()
		;
	}
}
