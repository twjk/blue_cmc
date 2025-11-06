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
				<th style="width:14%">订单</th>
				<th style="width:10%">用户</th>
				<th style="width:20%">商品</th>
				<th style="width:18%">总额/优惠/抵扣/应付/已付（元）</th>
				<th style="width:16%">创建/提交/完成时间</th>
				<th style="width:6%">处理状态</th>
				<th style="width:8%">下单平台</th>
				<th style="width:8%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="detailData('${orderid}', ${ordertype })">
				<td>
					${orderid}
					<br/>
					<dict:text field="serviceType" initvalue="${servicetype }"/>
				</td>
				<td>${userid }<br/>${user.nick }</td>
				<td>
					<s:iterator value="cmcRItems" id="item">
					${item.itemname}<br/>
					</s:iterator>
					（<dict:text field="orderType" initvalue="${ordertype }"/>）
				</td>
				<td>
					<fmt:formatNumber type="currency" pattern="0.00" value="${amount}"/>
				 	/
				 	<fmt:formatNumber type="currency" pattern="0.00" value="${couponamount}"/>
				 	/
				 	<fmt:formatNumber type="currency" pattern="0.00" value="${walletamount}"/>
				 	/
				 	<fmt:formatNumber type="currency" pattern="0.00" value="${payableamount}"/>
				 	/
				 	<fmt:formatNumber type="currency" pattern="0.00" value="${payamount}"/>
				 	<br/>
				 	<dict:text field="tradeWay" initvalue="${tradeway}"/>
				 </td>
				<td>
					<s:date name="%{createtime}" format="yyyy-MM-dd HH:mm:ss"/>
					<br/>
					<s:date name="%{waittime}" format="yyyy-MM-dd HH:mm:ss"/>
					<br/>
					<s:date name="%{finishtime}" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td><dict:text field="orderDealStatus" initvalue="${dealstatus}"/></td>
				<td>
					${platform}<br/>${version}
				</td>
				<td style="text-align: left">
					<a href="javascript:detailData('${orderid}', ${ordertype });"><img src="<lt:contextPath/>/images/detail.gif" alt="详情" title="详情"></a>
					<s:if test="@com.qcmz.cmc.constant.DictConstant@DICT_ORDER_DEALSTATUS_WAITPAY==dealstatus">
						&nbsp;&nbsp;
						<a href="javascript:synPay('${orderid}');"><img src="<lt:contextPath/>/images/refresh.png" alt="同步支付结果" title="同步支付结果" width="16" height="16"></a>
					</s:if>					
					<s:elseif test="@com.qcmz.cmc.util.OrderUtil@canForceRefund(walletamount, payamount, tradeway)">
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
