package com.qcmz.cmc.entity;

/**
 * CmcIrRecognition entity. @author MyEclipse Persistence Tools
 */

public class CmcIrRecognition implements java.io.Serializable {

	// Fields

	private Long recogid;
	private Long imageid;
	private CmcIrImage cmcIrImage;
	private String content;
	private String score;

	// Constructors
	/** default constructor */
	public CmcIrRecognition() {
	}

	// Property accessors
	public Long getRecogid() {
		return this.recogid;
	}

	public void setRecogid(Long recogid) {
		this.recogid = recogid;
	}

	public Long getImageid() {
		return imageid;
	}

	public void setImageid(Long imageid) {
		this.imageid = imageid;
	}

	public CmcIrImage getCmcIrImage() {
		return this.cmcIrImage;
	}

	public void setCmcIrImage(CmcIrImage cmcIrImage) {
		this.cmcIrImage = cmcIrImage;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}