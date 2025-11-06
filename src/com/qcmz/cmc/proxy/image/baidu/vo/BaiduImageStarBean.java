package com.qcmz.cmc.proxy.image.baidu.vo;

import java.util.ArrayList;
import java.util.List;

public class BaiduImageStarBean {
	/**
	 * 内层错误提示码，底层服务失败才返回，成功不返回
	 */
	private String error_code;
	/**
	 * 内层错误提示信息，底层服务失败才返回，成功不返回
	 */
	private String error_msg;
	/**
	 * 结果具体命中的模型：0:百度官方违禁图库、1：色情识别、2：暴恐识别、3：恶心图识别、4:广告检测、5：政治敏感识别、6：图像质量检测、7：用户图像黑名单、8：用户图像白名单、10：用户头像审核、11：百度官方违禁词库、12：图文审核、13:自定义文本黑名单、14:自定义文本白名单、15:EasyDL自定义模型、16：敏感旗帜标志识别、21：违禁识别、24：直播场景审核
	 */
	private Integer type;
	/**
	 * 审核子类型，此字段需参照type主类型字段决定其含义：
	 */
	private Integer subType;
	/**
	 * 不合规项描述信息
	 */
	private String msg;
	/**
	 * 不合规项置信度
	 */
	private String probability;
	/**
	 * 敏感人物列表数组，只有敏感人物审核不通过才有
	 */
	private List<BaiduImageStarStarBean> stars = new ArrayList<BaiduImageStarStarBean>();
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSubType() {
		return subType;
	}
	public void setSubType(Integer subType) {
		this.subType = subType;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getProbability() {
		return probability;
	}
	public void setProbability(String probability) {
		this.probability = probability;
	}
	public List<BaiduImageStarStarBean> getStars() {
		return stars;
	}
	public void setStars(List<BaiduImageStarStarBean> stars) {
		this.stars = stars;
	}
}
