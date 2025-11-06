package com.qcmz.cmc.ws.provide.vo;

public class CrowdSubjectBaseBean {
	/**
	 * 题目编号
	 */
	private Long subjectId;
	/**
	 * 题目分类
	 */
	private Integer subjectCat;
	/**
	 * 题型
	 */
	private Integer subjectType;
	/**
	 * 题目及格分数
	 */
	private Integer passScore;
	/**
	 * 题目奖励
	 */
	private Double subjectReward;
	/**
	 * 奖励类型
	 */
	private Integer rewardType;
	/**
	 * 奖励单位名称
	 */
	private String unitName;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 源语言名称
	 */
	private String fromLangName;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 目标语言名称
	 */
	private String toLangName;
	/**
	 * 内容类型
	 */
	private Integer contentType;
	/**
	 * 内容、原文
	 */
	private String content;
	/**
	 * 标注音频
	 */
	private String audio;
	/**
	 * 视频
	 */
	private String video;
	/**
	 * 视频来源
	 */
	private Integer videoSource;
	/**
	 * 图片
	 */
	private String pic;
	/**
	 * 音频采集次数
	 */
	private Integer recordTimes;
	/**
	 * 背景图片
	 */
	private String bgPic;
	/**
	 * 是否自动审核
	 */
	private boolean autoVerify;
	/**
	 * 是否自动审校
	 */
	private boolean autoQc;
	/**
	 * 广告参数，广告方|ios广告位编号;安卓广告位编号
	 */
	private String adParam;
	
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public Integer getSubjectCat() {
		return subjectCat;
	}
	public void setSubjectCat(Integer subjectCat) {
		this.subjectCat = subjectCat;
	}
	public Integer getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}
	public Integer getPassScore() {
		return passScore;
	}
	public void setPassScore(Integer passScore) {
		this.passScore = passScore;
	}
	public Double getSubjectReward() {
		return subjectReward;
	}
	public void setSubjectReward(Double subjectReward) {
		this.subjectReward = subjectReward;
	}
	public Integer getRewardType() {
		return rewardType;
	}
	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getFromLangName() {
		return fromLangName;
	}
	public void setFromLangName(String fromLangName) {
		this.fromLangName = fromLangName;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getToLangName() {
		return toLangName;
	}
	public void setToLangName(String toLangName) {
		this.toLangName = toLangName;
	}
	public Integer getContentType() {
		return contentType;
	}
	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAudio() {
		return audio;
	}
	public void setAudio(String audio) {
		this.audio = audio;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public Integer getVideoSource() {
		return videoSource;
	}
	public void setVideoSource(Integer videoSource) {
		this.videoSource = videoSource;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Integer getRecordTimes() {
		return recordTimes;
	}
	public void setRecordTimes(Integer recordTimes) {
		this.recordTimes = recordTimes;
	}
	public String getBgPic() {
		return bgPic;
	}
	public void setBgPic(String bgPic) {
		this.bgPic = bgPic;
	}
	public boolean isAutoVerify() {
		return autoVerify;
	}
	public void setAutoVerify(boolean autoVerify) {
		this.autoVerify = autoVerify;
	}
	public boolean isAutoQc() {
		return autoQc;
	}
	public void setAutoQc(boolean autoQc) {
		this.autoQc = autoQc;
	}
	public String getAdParam() {
		return adParam;
	}
	public void setAdParam(String adParam) {
		this.adParam = adParam;
	}
}
