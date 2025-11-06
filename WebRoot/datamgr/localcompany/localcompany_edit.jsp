<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
	<head>
		<title>填写信息</title>
	</head>
	<body>
		<form name="editForm" action="#" id="editForm">
			<div style="height:5px"></div>
			<table width="100%" cellpadding="0" cellspacing="0" class="tableHeader" border="0" align="center">
				<tr>
				    <td width="2%">&nbsp;</td>
					<td colspan="2" align="left" class="text13blackb">填写公司信息</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td width="10%">用户编号：</td>
					<td>
						${entity.userid }
						<s:hidden id="userid" name="entity.userid"/>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>公司名称：</td>
					<td><s:textfield id="companyname" name="entity.companyname" maxlength="100" cssClass="input2"/></td>
				</tr>
				<tr>
				 	<td></td>
					<td>统一社会信用代码：</td>
					<td><s:textfield id="uscc" name="entity.uscc" maxlength="20" cssClass="input2"/></td>
				</tr>
				<tr>
				 	<td></td>
					<td>公司联系人：</td>
					<td><s:textfield id="contacts" name="entity.contacts" maxlength="50" cssClass="input2"/></td>
				</tr>
				<tr>
				 	<td></td>
					<td>公司联系人电话：</td>
					<td><s:textfield id="tel" name="entity.tel" maxlength="50" cssClass="input2"/></td>
				</tr>
				<tr>
				 	<td></td>
					<td>招聘联系人：</td>
					<td><s:textfield id="hr" name="entity.hr" maxlength="50" cssClass="input2"/></td>
				</tr>
				<tr>
				 	<td></td>
					<td>招聘联系人电话：</td>
					<td><s:textfield id="hrtel" name="entity.hrtel" maxlength="50" cssClass="input2"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>营业执照：</td>
					<td valign="middle">
						<s:if test="!entity.businesslicense.isEmpty()">
							<a href="${entity.businesslicense }" target="_blank">
							<img src="${entity.businesslicense }" style="vertical-align:middle;cursor: pointer;max-height: 50px"/>
							</a>
						</s:if>
						<s:else>无</s:else>
					</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>公司图标：</td>
					<td valign="middle">
						<s:textfield id="logo" name="entity.logo" maxlength="100" onchange="changeSrc(this);" cssClass="input2"/>
						<img id="logoImg" src="${entity.logo } "
							onclick="insertSinglePic(this.id);"  
							alt="点击换图" title="点击换图"
							style="vertical-align:middle;cursor: pointer;max-height: 40px">
						<script id="singleEditor" type="text/plain" style="display:none"/>
						<script type="text/javascript">
							var singleEditor = new InsertImage('singleEditor', 'localcompany', callback_insertSinglePic);
							function insertSinglePic(id){
								singleEditor.insertImage(id);
							}	
							function callback_insertSinglePic(t, result, actionData) {
								$("#"+actionData).attr("src", result[0].src);
								$("#"+actionData).prev("input").val(result[0].src);
							}
						</script>
					</td>
				</tr>
				<tr>
				 	<td></td>
					<td>公司介绍：</td>
					<td><s:textarea id="introduce" name="entity.introduce" cssClass="textarea2" /></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>认证状态：</td>
					<td>
						<dict:text field="localCompanyCertifyStatus" initvalue="${entity.certifystatus}"/>
						<s:if test="entity.certifyresult!=null && entity.certifyresult!=''">（${entity.certifyresult }）</s:if>
					</td>
				</tr>
				<tr>
				 	<td></td>
					<td>创建途径：</td>
					<td><dict:text field="localCompanyCreateWay" initvalue="${entity.createway}"/></td>
				</tr>
				<tr height="30">
					<td colspan="3" align="center">
						<s:hidden id="companyid" name="entity.companyid"/>
						<input type="button" class="btn2" value="保存" onClick="saveData();" />
						<s:if test="@com.qcmz.cmc.constant.DictConstant@DICT_LOCACOMPANY_CERTIFYSTATUS_VERIFYING==entity.certifystatus">
							<input type="button" class="btn2" value="通过认证" onClick="passCertify();" />
							<input type="button" class="btn2" value="驳回认证" onClick="showReject();" />
						</s:if>
						<input type="button" class="btn2" value="取消" onClick="backList();" />
						
						<div id="rejectDiv" style="position: absolute;width:500px;display: none;background-color: #ffffff">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableHeader">
						<tr>
							<td align="left" class="text13blackb" colspan="3" style="padding-left: 30px">驳回认证</td>
						</tr>
						<tr>
							<td width="30" align="right" class="textrse12">*</td>
							<td width="100">驳回原因：</td>
							<td><input type="text" id="rejectReason" style="width: 300px;"></td>
						</tr>
						<tr>
							<td colspan="3" align="center" height="40">
								<input type="button" class="btn2" value="确定驳回" onClick="rejectCertify();" />
								<input type="button" class="btn2" value="关闭" onClick="hideReject();" />
								<p>&nbsp;</p>
							</td>
						</tr>
						</table>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
