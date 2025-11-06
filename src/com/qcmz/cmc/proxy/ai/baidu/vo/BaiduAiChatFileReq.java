package com.qcmz.cmc.proxy.ai.baidu.vo;

import java.util.ArrayList;
import java.util.List;

public class BaiduAiChatFileReq {
	private String model = "ernie-3.5-8k";	//模型ID，支持ernie-3.5-8k、ernie-4.0-turbo-8k、ernie-4.5-turbo-32k
	private List<Message> messages = new ArrayList<Message>();	//prompt
	private boolean stream = false;			//插件是否以流式接口的形式返回数据 false一次性返回
	private String[] plugins = new String[]{"ChatFilePlus"}; 	//需要调用的插件
	private PluginOption plugin_options = new PluginOption();	//插件配置
	
	public BaiduAiChatFileReq() {
		super();
	}
	
	public BaiduAiChatFileReq(String model, String message, String url) {
		super();
		this.model = model;
		this.messages.add(new Message(message));
		this.plugin_options.plugin_args.ChatFilePlus.body.files.add(new BaiduFile[]{new BaiduFile(url)});
	}

	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public String[] getPlugins() {
		return plugins;
	}
	public void setPlugins(String[] plugins) {
		this.plugins = plugins;
	}
	public boolean isStream() {
		return stream;
	}
	public void setStream(boolean stream) {
		this.stream = stream;
	}
	public PluginOption getPlugin_options() {
		return plugin_options;
	}
	public void setPlugin_options(PluginOption plugin_options) {
		this.plugin_options = plugin_options;
	}
	static class Message{
		private String role = "user";
		private String content;
		
		public Message() {
			super();
		}
		public Message(String content) {
			super();
			this.content = content;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}
	static class PluginOption{
		private PluginArg plugin_args = new PluginArg();
		public PluginArg getPlugin_args() {
			return plugin_args;
		}
		public void setPlugin_args(PluginArg plugin_args) {
			this.plugin_args = plugin_args;
		}
	}
	static class PluginArg{
		//不用get/set否则转json变量ChatFilePlus会变成chatFilePlus
		public ChatFilePlus ChatFilePlus = new ChatFilePlus();
	}
	static class ChatFilePlus{
		private BaiduBody body = new BaiduBody();
		public BaiduBody getBody() {
			return body;
		}
		public void setBody(BaiduBody body) {
			this.body = body;
		}
	}
	static class BaiduBody{
		private List<BaiduFile[]> files = new ArrayList<BaiduFile[]>();
		public List<BaiduFile[]> getFiles() {
			return files;
		}
		public void setFiles(List<BaiduFile[]> files) {
			this.files = files;
		}
	}
	static class BaiduFile{
		private String type = "link";
		private String url;
		private String name;
		
		public BaiduFile() {
			super();
		}
		public BaiduFile(String url) {
			super();
			this.url = url;
			this.name = url;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
}
