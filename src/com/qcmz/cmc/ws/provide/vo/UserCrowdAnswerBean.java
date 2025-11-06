package com.qcmz.cmc.ws.provide.vo;

/**
 * 用户答案信息
 */
public class UserCrowdAnswerBean {
	/**
	 * 用户答案编号
	 */
	private Long uaId;
	/**
	 * 题目编号
	 */
	private Long subjectId;
	/**
	 * 用户选中选项编号
	 */
	private Long optionId;
	/**
	 * 选项值
	 */
	private String optionValue;
	/**
	 * 用户填写的更多内容
	 */
	private String moreContent;
	/**
	 * 用户录音
	 */
	private String voice;
	/**
	 * 用户录音时长
	 */
	private Integer voiceDuration;
	/**
	 * 用户译文
	 */
	private String dst;
	/**
	 * 用户答案得分
	 */
	private Integer answerScore;
	/**
	 * 图片
	 */
	private String pic;
	public Long getUaId() {
		return uaId;
	}
	public void setUaId(Long uaId) {
		this.uaId = uaId;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public Long getOptionId() {
		return optionId;
	}
	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	public String getMoreContent() {
		return moreContent;
	}
	public void setMoreContent(String moreContent) {
		this.moreContent = moreContent;
	}
	public String getVoice() {
		return voice;
	}
	public void setVoice(String voice) {
		this.voice = voice;
	}
	public Integer getVoiceDuration() {
		return voiceDuration;
	}
	public void setVoiceDuration(Integer voiceDuration) {
		this.voiceDuration = voiceDuration;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	public Integer getAnswerScore() {
		return answerScore;
	}
	public void setAnswerScore(Integer answerScore) {
		this.answerScore = answerScore;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
}
