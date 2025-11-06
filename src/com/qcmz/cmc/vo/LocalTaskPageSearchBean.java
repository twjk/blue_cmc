package com.qcmz.cmc.vo;

public class LocalTaskPageSearchBean extends LocalTastSearchBean{
	private int pageSize;
	private String moreBaseId;
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getMoreBaseId() {
		return moreBaseId;
	}
	public void setMoreBaseId(String moreBaseId) {
		this.moreBaseId = moreBaseId;
	}
}
