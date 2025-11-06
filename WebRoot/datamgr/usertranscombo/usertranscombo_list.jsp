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
				<th style="width:5%">编号</th>
				<th style="width:5%;">付款方</th>
				<th style="width:10%">用户昵称</th>
				<th style="width:20%;">套餐名称</th>
				<th style="width:8%;">总量/余量</th>
				<th style="width:5%;">已用次数</th>
				<th style="width:15%">有效期/服务途径</th>
				<th style="width:14%;">兑换码</th>
				<th style="width:11%">创建时间</th>
				<th style="width:7%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="detailData('${ucid}')">
				<td>${ucid }</td>
				<td><dict:text field="transComboPaySide" initvalue="${payside }"/></td>
				<td>${userid}<br/>${user.nick }</td>
				<td>${combotitle }</td>
				<td>
					${num }/${usableNumReal } ${cmcCombo.unitname }					
				</td>
				<td>${usedtimes }</td>
				<td>
					<s:date name="%{starttime}" format="yyyy-MM-dd"/>~<s:date name="%{endtime}" format="yyyy-MM-dd"/>
					<br/>
					<dict:text field="transComboServiceWay" initvalue="${cmcCombo.serviceway }"/>
				</td>
				<td>
					${exchangecode }
					<br/>
					<dict:text field="exchangestatus" initvalue="${exchangestatus }" table="common" dataSource="xml"/>
				</td>
				<td><s:date name="%{createtime}" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<a href="javascript:detailData('${ucid}');"><img src="<lt:contextPath/>/images/detail.gif" alt="详情" title="详情"></a>
					<s:if test="exchangestatus==0">
						&nbsp;
						<a href="javascript:invalidData('${ucid}');"><img src="<lt:contextPath/>/images/invalid.png" alt="作废" title="作废"></a>
						&nbsp;
						<a href="javascript:deleteData('${ucid}');"><img src="<lt:contextPath/>/images/delet.gif" alt="删除" title="删除"></a>
					</s:if>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>