<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
  	<head>
		<title></title>
	</head>
	<body>
		<form name="editForm" action="#" id="editForm">
			<div style="height:10px"></div>
			<table width="100%" cellpadding="0" cellspacing="0" class="tableHeader" border="0" align="center">
				<tr>
				    <td width="2%">&nbsp;</td>
					<td colspan="2" align="left" class="text13blackb">填写信息</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td width="10%">付款方：</td>
					<td>
						<dict:radio name="entity.payside" field="transComboPaySide" cssClass="raddefault"/>
					</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>用户编号：</td>
					<td>
						<input id="userid" name="entity.userid">
						<span class='hint'>（填写用户编号，套餐直接分给该用户；否则生成兑换码兑换）</span>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>翻译套餐：</td>
					<td>
						<select id="selCombo" name="entity.comboid" onchange="selectCombo();">
							<option value="">--请选择--</option>
							<s:iterator value="combos" status="cst" id="combo">
								<option value="${comboId }" priceUnit="${priceUnit }">${title }（单价：￥${unitPrice}）</option>
							</s:iterator>
						</select>
					</td>
				</tr>
				<tr id="trPackage" hidden="hidden">
				 	<td align="right"></td>
					<td>特价套餐：</td>
					<td>
						<ul id="pkgList" class="raddefault">
							<li><input type="radio" name="entity.pkgid" value="" onclick="checkPackage();"/>不选套餐</li>
							<s:iterator value="combos" status="cst" id="combo">
								<s:iterator value="pkgs" status="pst" id="pkg">
								<li comboId="${comboId}" hidden="hidden">
									<input type="radio" name="entity.pkgid" value="${pkgId}" onclick="checkPackage();"/>${pkgTitle }（￥${price }）&nbsp;</li>
								</s:iterator>
							</s:iterator>
						</ul>
					</td>
				</tr>
				<tr id="trNum">
				 	<td align="right"></td>
					<td>自选天/次数：</td>
					<td>
						<input id="num" name="entity.num"/>
						<input id="noPkg" type="text" style="width:1px;border: 1px #ffffff" readonly="readonly">
					</td>
				</tr>
				<tr id="trStartTime">
				 	<td align="right"></td>
					<td>起始日期：</td>
					<td>
						<input name="entity.starttime" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
						<span class='hint'>（不填写则默认为当前日期）</span>
					</td>
				</tr>
				<tr>
				 	<td align="right" colspan="3" height="10"></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>份数：</td>
					<td>
						<s:select name="copies" value="%{copies }" list="{1,2,3,4,5,6,7,8,9}"></s:select>
					</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>添加结果：</td>
					<td id="saveResult"></td>
				</tr>
				<tr height="30">
					<td colspan="3" align="center">
						<input type="button" class="btn2" value="保存" onClick="saveData();" />
						<input type="button" class="btn2" value="取消" onClick="backList();" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
