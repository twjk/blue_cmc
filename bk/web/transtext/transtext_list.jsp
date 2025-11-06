<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
    <head>
		<title></title>
	</head>
	<body>
	<div>
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0">
			<tr>
				<th style="width:10%">订单</th>
				<th style="width:8%">用户</th>
				<th style="width:6%">模式/语言</th>
				<th style="width:16%">原文</th>
				<th style="width:16%">译文</th>
				<th style="width:12%">总额/优惠/抵扣/应付/已付</th>
				<th style="width:8%">创建/提交时间</th>
				<th style="width:6%">开始/完成时间</th>
				<th style="width:5%">处理</th>
				<th style="width:5%">下单平台</th>
				<th style="width:5%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="detailData('${orderid}')">
				<td>${orderid}</td>
				<td>${userid }<br/>${user.nick }</td>
				<td>
					<dict:text field="transModel" initvalue="${transmodel }"/>
					<br/>
					<dict:text field="textLang" initvalue="${fromlang}"/>
					->
					<dict:text field="textLang" initvalue="${tolang}"/>
				</td>				
				<td style="padding-right: 3px;">${src }</td>
				<td style="padding-left: 3px;padding-right: 3px">${dst }</td>
				<td>
					<fmt:formatNumber type="currency" pattern="0.00" value="${cmcROrder.amount}"/>
				 	/
				 	<fmt:formatNumber type="currency" pattern="0.00" value="${cmcROrder.couponamount}"/>
				 	/
				 	<fmt:formatNumber type="currency" pattern="0.00" value="${cmcROrder.walletamount}"/>
				 	/
				 	<fmt:formatNumber type="currency" pattern="0.00" value="${cmcROrder.payableamount}"/>
				 	/
				 	<fmt:formatNumber type="currency" pattern="0.00" value="${cmcROrder.payamount}"/>
				 	<br/>
				 	<dict:text field="tradeWay" initvalue="${cmcROrder.tradeway}"/>
				 </td>
				<td>
					<s:date name="%{cmcROrder.createtime}" format="yyyy-MM-dd HH:mm:ss"/>
					<br/>
					<s:date name="%{cmcROrder.waittime}" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<s:date name="%{cmcROrder.opertime}" format="MM-dd HH:mm:ss"/>
					<br/>
					<s:date name="%{cmcROrder.finishtime}" format="MM-dd HH:mm:ss"/>
				</td>
				<td>
					<dict:text field="orderDealStatus" initvalue="${cmcROrder.dealstatus}"/>
					<br/>
					${cmcROrder.opername}
				</td>
				<td>
					${cmcROrder.platform}<br/>${cmcROrder.version}
				</td>
				<td style="text-align: left">
					<a href="javascript:detailData('${orderid}');"><img src="<lt:contextPath/>/images/detail.gif" alt="详情" title="详情"></a>
					<s:if test="@com.qcmz.cmc.constant.DictConstant@DICT_ORDER_DEALSTATUS_SUBMITTED==cmcROrder.dealstatus">
						&nbsp;&nbsp;
						<a href="javascript:synPay('${orderid}');"><img src="<lt:contextPath/>/images/refresh.png" alt="同步支付结果" title="同步支付结果" width="16" height="16"></a>
					</s:if>
					<s:elseif test="cmcROrder.payamount>0 && @com.qcmz.cmc.constant.DictConstant@DICT_ORDER_DEALSTATUS_CANCEL==cmcROrder.dealstatus">
						&nbsp;&nbsp;
						<a href="javascript:synRefund('${orderid}');"><img src="<lt:contextPath/>/images/refresh.png" alt="同步退款结果" title="同步退款结果" width="16" height="16"></a>
					</s:elseif>
					<s:elseif test="@com.qcmz.cmc.util.OrderUtil@canForceRefund(cmcROrder.walletamount, cmcROrder.payamount, cmcROrder.tradeway)">
						&nbsp;&nbsp;
						<a href="javascript:refund('${orderid}');"><img src="<lt:contextPath/>/images/refund.png" alt="退款" title="退款" width="16" height="16"></a>
					</s:elseif>					
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	
	<div id="userArea" title="用户信息" style="margin:0 auto 0" onclick="hideUser();"></div>	
	<script type="text/javascript">
		function showUser(uid){
			if($("#userInfo"+uid).is(':visible')){
				hideUser();
				return;
			}
			hideUser();
			if($("#userInfo"+uid).html()!=''){
				$("#userInfo"+uid).show();
			}else{
				var url = "userAction!showUserInfo.do";
				var params = "uid=" + uid;			
				$.ajax({type:'post',url:url, data:params, complete:function(result){
					$("#userInfo"+uid).html(result.responseText);
					$("#userInfo"+uid).show();
				}});
			}
		}
		
		function hideUser(){
			$("div[tag='userInfo']").hide();
		}
	</script>
	</body>
</html>