package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.ws.provide.vo.TransResult;

@Component
public class TransTestProcess {
	@Autowired
	private TransProcess transProcess;
	
	private static List<String> srcList = new ArrayList<String>();
	static{
		srcList.add("出国翻译官");
		srcList.add("你好");
		srcList.add("你好吗");
		srcList.add("早上好");
		srcList.add("晚上好");
		srcList.add("中国");
		srcList.add("我爱你");
		srcList.add("晚安");
		srcList.add("谢谢");
		srcList.add("不客气");
		srcList.add("吃饭");
		srcList.add("对不起");
		srcList.add("香蕉");
		srcList.add("苹果");
		srcList.add("爸爸");
		srcList.add("妈妈");
		srcList.add("好");
		srcList.add("美国");
		srcList.add("你叫什么名字");
		srcList.add("生日快乐");
		srcList.add("你是谁");
		srcList.add("我喜欢你");
		srcList.add("多少钱");
		srcList.add("这个多少钱");
		srcList.add("不用谢");
		srcList.add("漂亮");
		srcList.add("你在哪里");
		srcList.add("很高兴认识你");
		srcList.add("红色");
		srcList.add("白色");		
	}
	
	public void test(){
		int i = 0;
		for (String src : srcList) {
			i++;
			System.out.println("============"+i+"================");
			TransResult result = transProcess.trans("zh-cn", "ug", src);
			
			if(!result.getDst().equals(src)){
				System.out.println("原文："+src);
				System.out.println("译文："+result.getDst());
				
				src = result.getDst();
				result = transProcess.trans("ug", "zh-cn", src);
				System.out.println("回译原文："+src);
				System.out.println("回译译文："+result.getDst());
			}
		}
		
	}
	
}
