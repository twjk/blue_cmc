package com.qcmz.cmc.entity;

/**
 * CmcTransWord entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CmcTransWord implements java.io.Serializable {

	// Fields

	private Long wordid;
	private String langcode;
	private String word;
	private Long transcount;
	private String country;

	// Constructors

	/** default constructor */
	public CmcTransWord() {
	}

	/** minimal constructor */
	public CmcTransWord(String word, Long transcount) {
		this.word = word;
		this.transcount = transcount;
	}

	/** full constructor */
	public CmcTransWord(String langcode, String word, Long transcount,
			String country) {
		this.langcode = langcode;
		this.word = word;
		this.transcount = transcount;
		this.country = country;
	}

	// Property accessors

	public Long getWordid() {
		return this.wordid;
	}

	public void setWordid(Long wordid) {
		this.wordid = wordid;
	}

	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Long getTranscount() {
		return this.transcount;
	}

	public void setTranscount(Long transcount) {
		this.transcount = transcount;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}