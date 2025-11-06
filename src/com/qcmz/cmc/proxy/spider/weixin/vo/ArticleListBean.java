package com.qcmz.cmc.proxy.spider.weixin.vo;

public class ArticleListBean {
	/**
	 * 2651563500_1,2651563500_2,...
	 */
	private String aid;
	/**
	 * 2651563500
	 */
    private Long appmsgid;
    /**
     * 图片链接
     */
    private String cover;
    
    /**
     * 详情链接
     */
    private String link;
    /**
     * 标题
     */
    private String title;
    /**
     * 创建时间，秒
     */
    private Long createTtime;
    /**
     * 修改时间，秒
     */
    private Long updateTime;
    private String digest;
    private String itemidx;
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public Long getAppmsgid() {
		return appmsgid;
	}
	public void setAppmsgid(Long appmsgid) {
		this.appmsgid = appmsgid;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getItemidx() {
		return itemidx;
	}
	public void setItemidx(String itemidx) {
		this.itemidx = itemidx;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getCreateTtime() {
		return createTtime;
	}
	public void setCreateTtime(Long createTtime) {
		this.createTtime = createTtime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
}
