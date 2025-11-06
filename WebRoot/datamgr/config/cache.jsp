<%@page import="com.qcmz.umc.ws.provide.locator.UserMap"%>
<%@page import="com.qcmz.srm.client.cache.UserCache"%>
<%@page import="com.qcmz.srm.client.cache.GroupUserCache"%>
<%@page import="com.qcmz.srm.client.util.SrmClient"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.qcmz.cmc.constant.BeanConstant"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
	<head>
		<title>缓存管理</title>
		<jsp:include page="/common/head.jsp" />
		<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/cache.js"></script>
	</head>
	<body topmargin="0" leftmargin="0" class="editLayer">
	<div id="funcNavigator" class="navDiv" align="left">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
  		<tr><td width="1%">&nbsp;</td>
    		<td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
    		<td width="98%" class="text12black">&nbsp;现在您的位置：系统管理  &gt;&gt; 缓存管理</td>
    	</tr>
	  </table>
	</div>
	<br/>
	<form id="editForm" name="editForm" action="#">
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th width="98%">缓存管理</th>
			</tr>
			<tr>
				<td></td>
				<td>
					<ul class="chkdefault">
						<li><input type="checkbox" id="dict" name="beanid" value="<%=BeanConstant.BEANID_CACHE_DICT %>" class="checkbox">字典（分类/语言/功能/图像识别分类）</li>
						<li><input type="checkbox" id="evalMap" name="beanid" value="<%=BeanConstant.BEANID_CACHE_EVAL %>" class="checkbox">评价字典</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<ul class="chkdefault">
						<li><input type="checkbox" id="cat" name="beanid" value="<%=BeanConstant.BEANID_CACHE_CAT %>" class="checkbox">分类</li>
						<li><input type="checkbox" id="paramMap" name="beanid" value="<%=BeanConstant.BEANID_CACHE_PARAM %>" class="checkbox">在线参数</li>
						<li><input type="checkbox" id="staticTemplate" name="beanid" value="<%=BeanConstant.BEANID_CACHE_STATICTEMPLATE %>" class="checkbox">静态化模板</li>
						<li><input type="checkbox" id="holiday" name="beanid" value="<%=BeanConstant.BEANID_CACHE_HOLIDAY %>" class="checkbox">节假日</li>
						<li><input type="checkbox" id="srmClient" name="beanid" value="<%=SrmClient.BEANID_CACHE_SRMCLIENT %>" class="checkbox">权限用户（用于接口鉴权和用户密码解密）</li>
						<li><input type="checkbox" id="operatorCache" name="beanid" value="<%=UserCache.BEANID_CACHE_USERCACHE %>" class="checkbox">权限用户（操作员）</li>
						<li><input type="checkbox" id="groupUser" name="beanid" value="<%=GroupUserCache.BEANID_CACHE_GROUPUSERCACHE %>" class="checkbox">权限组用户</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<ul class="chkdefault">
						<li><input type="checkbox" id="lang" name="beanid" value="<%=BeanConstant.BEANID_CACHE_LANG %>" class="checkbox">语言</li>					
						<li><input type="checkbox" id="transPicLang" name="beanid" value="<%=BeanConstant.BEANID_CACHE_TRANSPICLANG %>" class="checkbox">图片翻译语言列表</li>
						<li><input type="checkbox" id="transTextLang" name="beanid" value="<%=BeanConstant.BEANID_CACHE_TRANSTEXTLANG %>" class="checkbox">文本人工翻译语言列表</li>
						<li><input type="checkbox" id="transVideoLang" name="beanid" value="<%=BeanConstant.BEANID_CACHE_TRANSVIDEOLANG %>" class="checkbox">视频人工翻译语言列表</li>
						<li><input type="checkbox" id="funcCap" name="beanid" value="<%=BeanConstant.BEANID_CACHE_FUNCCAP %>" class="checkbox">功能能力（含ASR/TTS/OCR/语言检测）</li>
						<li><input type="checkbox" id="transCap" name="beanid" value="<%=BeanConstant.BEANID_CACHE_TRANSCAP %>" class="checkbox">翻译能力（含翻译语言列表）</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<ul class="chkdefault">
						<li><input type="checkbox" id="proxy" name="beanid" value="<%=BeanConstant.BEANID_CACHE_PROXY %>" class="checkbox">代理</li>
						<li><input type="checkbox" id="proxyAccount" name="beanid" value="<%=BeanConstant.BEANID_CACHE_PROXYACCOUNT %>" class="checkbox">代理帐户</li>
						<li><input type="checkbox" id="proxyLang" name="beanid" value="<%=BeanConstant.BEANID_CACHE_PROXYLANG %>" class="checkbox">代理语言</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<ul class="chkdefault">
						<li><input type="checkbox" id="translib" name="beanid" value="<%=BeanConstant.BEANID_CACHE_TRANSLIB %>" class="checkbox">译库</li>
						<li><input type="checkbox" id="theme" name="beanid" value="<%=BeanConstant.BEANID_CACHE_THEME %>" class="checkbox">主题</li>
						<li><input type="checkbox" id="daySentence" name="beanid" value="<%=BeanConstant.BEANID_CACHE_DAYSENTENCE %>" class="checkbox">每日一句</li>
						<li><input type="checkbox" id="transResultMap" name="beanid" value="<%=BeanConstant.BEANID_CACHE_TRANSRESULT %>" class="checkbox">翻译结果</li>
						<li><input type="checkbox" id="keyword" name="beanid" value="<%=BeanConstant.BEANID_CACHE_KEYWORD %>" class="checkbox">关键词</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<ul class="chkdefault">
						<li><input type="checkbox" id="articleItemMap" name="beanid" value="<%=BeanConstant.BEANID_CACHE_ARTICLEITEM %>" class="checkbox">资讯商品</li>
						<li><input type="checkbox" id="crowdTaskMap" name="beanid" value="<%=BeanConstant.BEANID_CACHE_CROWDTASK %>" class="checkbox">众包任务</li>
						<li><input type="checkbox" id="crowdTaskLibMap" name="beanid" value="<%=BeanConstant.BEANID_CACHE_CROWDTASK_LIB %>" class="checkbox">众包任务前置任务题库</li>
						<li><input type="checkbox" id="crowdTaskBlacklistMap" name="beanid" value="<%=BeanConstant.BEANID_CACHE_CROWDTASK_BLACKLIST %>" class="checkbox">众包任务黑名单</li>
						<li><input type="checkbox" id="crowdTaskSubjectCatMap" name="beanid" value="<%=BeanConstant.BEANID_CACHE_CROWDTASK_SUBJECTCAT %>" class="checkbox">众包任务题目分类</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<ul class="chkdefault">
						<li><input type="checkbox" id="transprice" name="beanid" value="<%=BeanConstant.BEANID_CACHE_TRANSPRICE %>" class="checkbox">翻译价格</li>
						<li><input type="checkbox" id="transCombo" name="beanid" value="<%=BeanConstant.BEANID_CACHE_TRANSCOMBO %>" class="checkbox">翻译套餐</li>
						<li><input type="checkbox" id="sceneMap" name="beanid" value="<%=BeanConstant.BEANID_CACHE_SCENE %>" class="checkbox">翻译场景</li>
						<li><input type="checkbox" id="tm" name="beanid" value="<%=BeanConstant.BEANID_CACHE_TM %>" class="checkbox">翻译机信息</li>
						<li><input type="checkbox" id="user" name="beanid" value="<%=UserMap.BEANID_CACHE_USERMAP %>" class="checkbox">会员用户</li>
						<li><input type="checkbox" id="appuserForbid" name="beanid" value="<%=BeanConstant.BEANID_CACHE_APPUSERFORBID %>" class="checkbox">禁止翻译应用用户</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<ul class="chkdefault">
						<li><input type="checkbox" name="beanid" value="<%=BeanConstant.BEANID_CACHE_WXPAYACCOUNT %>" class="checkbox">微信支付账号</li>
						<li><input type="checkbox" name="beanid" value="<%=BeanConstant.BEANID_CACHE_ALIPAYACCOUNT %>" class="checkbox">支付宝账号</li>
						<li><input type="checkbox" name="beanid" value="<%=BeanConstant.BEANID_CACHE_PRODUCT %>" class="checkbox">产品及其商品</li>
						<li><input type="checkbox" name="beanid" value="<%=BeanConstant.BEANID_CACHE_IRCAT %>" class="checkbox">图像识别分类</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<ul class="chkdefault">
						<li><input type="checkbox" name="beanid" value="<%=BeanConstant.BEANID_CACHE_LOCALCOMPANY %>" class="checkbox">同城公司</li>
						<li><input type="checkbox" name="beanid" value="<%=BeanConstant.BEANID_CACHE_LOCALSOURCE %>" class="checkbox">同城来源</li>
						<li><input type="checkbox" name="beanid" value="<%=BeanConstant.BEANID_CACHE_REWARDACTIVITY %>" class="checkbox">奖励活动</li>
					</ul>
				</td>
			</tr>
			<tr height="30">
				<td colspan="2" align="center">
					<input type="checkbox" id="checkAll" onClick="check();" class="checkbox"/>所有
					<input type="button" class="btn2" value="重 载" onClick="reloadCache()" />
				</td>
			</tr>
		</table>	 
	</form>
	<br/>
	</body>
</html>