package com.qcmz.cmc.vo;

import java.io.Serializable;

import com.qcmz.framework.util.log.BeanDesc;

/**
 * 类说明：和译库的字段完全一致，用于封装译库信息
 * 修改历史：
 */
public class TransLibraryEntity implements Serializable{

	// Fields
	@BeanDesc(desc="编号")
	private Long libid;
	@BeanDesc(desc="译库类型")
	private String libtype;
	@BeanDesc(desc="源语言")
	private String fromlang;
	@BeanDesc(desc="原文")
	private String src;
	@BeanDesc(desc="目标语言")
	private String tolang;
	@BeanDesc(desc="译文")
	private String dst;
	@BeanDesc(desc="双向互译")
	private Integer twoway;
	@BeanDesc(desc="分类")
	private String cat;
	@BeanDesc(desc="分类名称")
	private String catname;
	@BeanDesc(desc="语音文件")
	private String voice;
	@BeanDesc(desc="合成方式")
	private Integer ttssrc = 0;
	@BeanDesc(desc="指定合成文本")
	private String ttstext;
	@BeanDesc(desc="备注")
    private String remark;
	@BeanDesc(desc="命中数")
	private Long hitcount = 0L;
	@BeanDesc(desc="相似度")
	private Integer similar = 0;
	@BeanDesc(desc="状态")
	private Integer status;
	@BeanDesc(desc="核对状态")
	private Integer checkstatus;
	@BeanDesc(desc="实体名")
	private String libClass;
	@BeanDesc(desc="来源")
	private String sourceid;

	public TransLibraryEntity() {
	}

	public TransLibraryEntity(String libClass, Long libid, String libtype, String fromlang,
			String src, String tolang, String dst, Integer twoway, String cat, String catname,
			String voice, Integer ttssrc, String ttstext, String remark
			, Long hitcount, Integer similar, Integer status, Integer checkstatus, String sourceid) {
		super();
		this.libClass = libClass;
		this.libid = libid;
		this.libtype = libtype;
		this.fromlang = fromlang;
		this.src = src;
		this.tolang = tolang;
		this.dst = dst;
		this.twoway = twoway;
		this.cat = cat;
		this.catname = catname;
		this.voice = voice;
		this.ttssrc = ttssrc;
		this.ttstext = ttstext;
		this.remark = remark;
		this.hitcount = hitcount;
		this.similar = similar;
		this.status = status;
		this.checkstatus = checkstatus;
		this.sourceid = sourceid;
	}
	
	public String getLibClass() {
		return libClass;
	}

	public void setLibClass(String libClass) {
		this.libClass = libClass;
	}

	public Long getLibid() {
		return this.libid;
	}

	public void setLibid(Long libid) {
		this.libid = libid;
	}

	public String getLibtype() {
		return this.libtype;
	}

	public void setLibtype(String libtype) {
		this.libtype = libtype;
	}

	public String getFromlang() {
		return this.fromlang;
	}

	public void setFromlang(String fromlang) {
		this.fromlang = fromlang;
	}

	public String getSrc() {
		return this.src;
	}

	public void setSrc(String src) {
		if(src!=null){
			this.src = src.trim();
		}else{
			this.src = src;
		}
	}

	public String getTolang() {
		return this.tolang;
	}

	public void setTolang(String tolang) {
		this.tolang = tolang;
	}

	public String getDst() {
		return this.dst;
	}

	public void setDst(String dst) {
		if(dst!=null){
			this.dst = dst.trim();
		}else{
			this.dst = dst;
		}
	}

	public Integer getTwoway() {
		return twoway;
	}

	public void setTwoway(Integer twoway) {
		this.twoway = twoway;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getCatname() {
		return catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public Long getHitcount() {
		return hitcount;
	}

	public void setHitcount(Long hitcount) {
		this.hitcount = hitcount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCheckstatus() {
		return checkstatus;
	}

	public void setCheckstatus(Integer checkstatus) {
		this.checkstatus = checkstatus;
	}

	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	public Integer getSimilar() {
		return similar;
	}

	public void setSimilar(Integer similar) {
		this.similar = similar;
	}

	public Integer getTtssrc() {
		return ttssrc;
	}

	public void setTtssrc(Integer ttssrc) {
		this.ttssrc = ttssrc;
	}

	public String getTtstext() {
		return ttstext;
	}

	public void setTtstext(String ttstext) {
		this.ttstext = ttstext;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}