package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.proxy.trans.HcicloudTransProxy;

@Component
public class TestProcess {
	@Autowired
	private HcicloudTransProxy hcicloudTransProxy;
	
	private static List<String> srcList = new ArrayList<String>();
	static{
		/*
		srcList.add("今天天气不错");
		srcList.add("出国翻译官");
		srcList.add("你好");
		srcList.add("早上好");
		srcList.add("中国");
		srcList.add("我爱你");
		srcList.add("晚安");
		srcList.add("谢谢");
		srcList.add("你叫什么名字");
		srcList.add("你吃饭了吗");
		srcList.add("生日快乐");
		srcList.add("我喜欢你");
		srcList.add("这个多少钱");
		srcList.add("很高兴认识你");
		srcList.add("你在哪里");
		srcList.add("大家好");
		srcList.add("饼干");
		srcList.add("非常感谢");
		srcList.add("香蕉");
		srcList.add("It's a nice day today");
		srcList.add("Global translator");
		srcList.add("Hello");
		srcList.add("Good morning");
		srcList.add("China");
		srcList.add("Good night");
		srcList.add("Thank you");
		srcList.add("What's your name");
		srcList.add("Have you eaten");
		srcList.add("Happy Birthday");
		srcList.add("I love you");
		srcList.add("How much is this");
		srcList.add("Nice to meet you");
		srcList.add("Where are you?");
		srcList.add("Hello everyone");
		srcList.add("Biscuits");
		srcList.add("Thank you very much");
		srcList.add("Banana");
		 */
		srcList.add("Welcome to Thailand! Enjoy Voice and Data Roaming. To reach a local number, pls dial: +66<phone no.>. To make calls to an international location, pls dial: + <country code> <area code> <phone no.>. Have a pleasant stay with AIS.");
	}
	public void testHcicloudTrans(){
		String from = "en";
		String to = "zh-cn";
		String dst = null;
		
		for (String src : srcList) {
			dst = hcicloudTransProxy.trans(from, to, src);
			System.out.println(src+" >>>  " + dst);
		}
	}
}
