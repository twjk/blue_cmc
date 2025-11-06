package com.qcmz.cmc.ws.provide.vo;

import java.io.File;

import com.qcmz.framework.annotation.JsonIgore;
import com.qcmz.framework.ws.vo.Request;

public class RedPacketReceiveReq extends Request {
	/**
	 * 红包编号
	 */
	private String packetId;
	/**
	 * 领红包用户编号
	 */
	private Long receiverId;
	/**
	 * 文件
	 */
	private File file;
	public Long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}
	public String getPacketId() {
		return packetId;
	}
	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}
	@JsonIgore
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
}
