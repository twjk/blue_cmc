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
				<th style="width:8%">语言</th>
				<th style="width:20%">原文</th>
				<th style="width:20%">译文</th>
				<th></th>
				<th style="width:10%">提交/完成时间</th>
				<th style="width:6%">处理进度/状态</th>
				<th style="width:6%">处理人/评价</th>
				<th style="width:8%">订单金额/佣金</th>
				<th style="width:4%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${orderid}', ${ordertype})" >
				<td>
					${orderid}
					<br/>
					<dict:text field="orderType" initvalue="${ordertype }"/>
				</td>
				<td>${userid }<br/>${user.nick }</td>
				<td>
					<dict:text field="textLang" initvalue="${fromlang}"/>
					&nbsp;->&nbsp;
					<dict:text field="textLang" initvalue="${tolang}"/>
				</td>
				<td style="padding-right: 3px;">
					<s:if test="cmcPtPic!=null">
						<img src="${cmcPtPic.thumburl}" style="max-height: 70px; max-width: 150px">
					</s:if>
					<s:elseif test="cmcTtTrans!=null">
						<s:property value="@com.qcmz.framework.util.StringUtil@abbreviate(cmcTtTrans.src, 80)" escapeHtml="true"/>
					</s:elseif>
				</td>
				<td style="padding-left: 3px;padding-right: 3px">
					<s:if test="cmcPtPic!=null">
						<s:property value="@com.qcmz.framework.util.StringUtil@abbreviate(cmcPtPic.dst, 80)" escapeHtml="true"/>
					</s:if>
					<s:elseif test="cmcTtTrans!=null">
						<s:property value="@com.qcmz.framework.util.StringUtil@abbreviate(cmcTtTrans.dst, 80)" escapeHtml="true"/>
					</s:elseif>
				</td>
				<td align="right"><s:if test="appointflag==1"><img src="<lt:contextPath/>/images/appoint.png" alt="预约" title="预约" width="16" height="16" style="vertical-align: middle"></s:if></td>
				<td>
					<s:date name="%{waittime}" format="yyyy-MM-dd HH:mm:ss"/>
					<br/>
					<s:date name="%{finishtime}" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<dict:text field="dealProgress" initvalue="${dealprogress}"/>
					<br/>
					<dict:text field="orderDealStatus" initvalue="${dealstatus}"/>				
				</td>
				<td>
					${opername}
					<s:if test="%{cmcREval!=null}">
						<br/>
						<span onmouseover="showEvalTag(this,'${cmcREval.evaltag }')" onmouseout="hideEvalTag(this)" class="eval">${cmcREval.levelname }</span>
					</s:if>
				</td>
				<td>
					<s:if test="cmcUCombo!=null">
				 		${cmcUCombo.combotitle }
				 	</s:if>
					<s:if test="dealprogress=='03'">
					 	<s:if test="cmcUCombo==null">
							￥<fmt:formatNumber type="currency" pattern="0.00" value="${amount}"/>
						</s:if>
						<br/>
						<s:if test="commissionamount==null">
							<dict:text initvalue="${commissionstatus }" field="commissionstatus" dataSource="xml" table="common"/>
						</s:if>
						<s:else>
							￥<fmt:formatNumber type="currency" pattern="0.00" value="${commissionamount}"/>	
						</s:else>
					</s:if>
				</td>
				<td>
					<a href="javascript:editData('${orderid}', ${ordertype} );"><img src="<lt:contextPath/>/images/edit.gif" alt="翻译" title="翻译"></a>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
		<div id="evalTagDiv" style="position: absolute; width:200px;height:50px; text-align:center;font-size:12px; padding:2px; background-color: white; border: 1px solid #cccccc;display: none;"></div>
		<script type="text/javascript">
		function showEvalTag(obj, evaltag) {
			$("#evalTagDivTemp").remove();
			var evalDiv = $("#evalTagDiv").clone().attr("id","evalTagDivTemp");
			$(evalDiv).text(evaltag);
			$(evalDiv).insertAfter($(obj));						
	        $(evalDiv).show();
	    }
	    function hideEvalTag(obj) {
	    	$("#evalTagDivTemp").remove();
	    }
		</script>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
