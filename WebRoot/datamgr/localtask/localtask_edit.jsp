<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.qcmz.cmc.config.SystemConfig"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
	<head>
		<title>填写信息</title>
		<script type="text/javascript" src="<%=SystemConfig.BDC_SERVER %>/ws/geog.js"></script>
		<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/localCompanySel.js"></script>
		<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/localSourceSel.js"></script>
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
					<td></td>
					<td width="10%">来源：</td>
					<td>
						<input type="text" id="sourcename" autoname="entity.sourceid" name="source.sourcename" class="input2"/>
						<s:hidden id="sourceid" name="entity.sourceid"/>
					</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>链接：</td>
					<td>
						<s:textfield id="link" name="entity.link" cssClass="input2"/>
						<a id="browse" href="javascript:browseLink();">查看</a>
					</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>公司：</td>
					<td>
						<input type="text" id="companyname" autoname="entity.companyid" name="company.companyname" class="input2"/>
						<s:hidden id="companyid" name="entity.companyid"/>
						&nbsp;
						<a href="javascript:addCompany();">增加公司</a>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>岗位：</td>
					<td>
						<dict:select id="worktimetype" field="workTimeType" name="entity.worktimetype" initvalue="${entity.worktimetype }" cssClass="select2"/>
						<s:textfield id="title" name="entity.title" cssStyle="width:296px"/>
					</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>薪酬：</td>
					<td>
						<dict:select id="rewardType" name="entity.rewardtype" initvalue="${entity.rewardtype }" field="rewardType" inithead="0" cssClass="select2"/>
						<s:textfield id="minreward" name="entity.minreward" cssClass="input3"/>
						-
						<s:textfield id="maxreward" name="entity.maxreward" cssClass="input3"/>
						元
					</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>薪酬描述：</td>
					<td><s:textfield id="reward" name="entity.reward" cssClass="input2"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>学历要求：</td>
					<td>
						<dict:select id="minedu" name="entity.minedu" initvalue="${entity.minedu }" field="localEdu" inithead="0" cssClass="select2"/>
						<s:textfield id="edu" name="entity.edu" cssStyle="width:296px"/>
					</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>工作经验：</td>
					<td>
						<s:textfield id="exp" name="entity.exp"/>
						，至少
						<s:textfield id="minexp" name="entity.minexp" cssClass="input3"/>年
					</td>
				</tr>
				<tr>
				 	<td align="right" style="margin-bottom: "></td>
					<td>人数：</td>
					<td><s:textfield id="peoplenum" name="entity.peoplenum"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>岗位职责：</td>
					<td><s:textarea id="job" name="entity.job" cssClass="textarea5"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>任职要求：</td>
					<td><s:textarea id="jobrequire" name="entity.jobrequire" cssClass="textarea5" cssStyle="margin-top:5px"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>联系方式：</td>
					<td><s:textfield id="reward" name="entity.contact" cssClass="input2"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>工作城市：</td>
					<td>
						<s:textfield id="countryname" autoname="entity.countrycode" value="中国" cssStyle="display:none"/>
						<input type="hidden" id="countrycode" name="entity.countrycode" value="cn"/>						
						<s:textfield id="cityname" name="entity.cityname" autoname="entity.cityid"/>
						<s:hidden id="cityid" name="entity.cityid"/>
					</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>工作地址：</td>
					<td><s:textfield id="address" name="entity.address" cssClass="input2"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>图片：</td>
					<td>
						<style>
						<!--
							#picUL{list-style: none;padding: 0px;margin: 0px;}
							#picUL li{width:200px;float: left; margin: 2px 5px 2px 5px;padding:1px; border:#E5E5E5 solid 1px;line-height: 20px;text-align: left;}
							#picUL img{height:100px;max-width:200px;cursor: pointer;}
							#picUL .input{width:160px;margin-top:5px;}
							#picUL .input2{width:50px;margin-top:5px;}
							#picUL .input3{width:115px;margin-top:5px;}
							#picUL .btn{width:40px;}
							#picUL .red{color:#FF0000}
							#picUL .imgDiv{height: 100px;text-align: center;}
						-->
						</style>
						<a href="javascript:insertMultiPic();">增加图片</a>
						<ul id="picUL">
						<s:iterator value="entity.cmcLtPics" status="st" id="bean">
							<li>
								<div class="imgDiv">
									<img id="pic${st.index }" src="${picurl }"  onclick="insertMultiPic(this.id);" alt="点击更换图片" title="点击更换图片" /><br/>
								</div>
								地址：<input type="text" name="entity.cmcLtPics[${st.index }].picurl" value="${picurl }" class="input<s:if test="%{@com.qcmz.framework.util.FileServiceUtil@isOuterFileUrl(picurl)}"> red</s:if>"/><br/>
								排序：<input type="text" name="entity.cmcLtPics[${st.index }].sortindex" value="${sortindex }" class="input2"/>
								&nbsp;&nbsp;<a onclick="javascript:delPic_picList(this);" href="javascript:void(0);">删除</a>
								&nbsp;&nbsp;<a href="${picurl }" target="_blank">查看</a>
							</li>
						</s:iterator>
					</ul>
					<input type="hidden" id="picSeq" value="${fn:length(entity.cmcLtPics)}"/>
					<li id="picTemplate" style="display: none">
						<div class="imgDiv">
							<img onclick="insertMultiPic(this.id);" alt="点击更换图片" title="点击更换图片" height="100"/><br/>
						</div>	
						地址：<input type="text" name="picUrl" value="" class="input"/>
						排序：<input type="text" name="sortIndex" value="99" class="input2"/>
						&nbsp;&nbsp;<a onclick="javascript:delPic_picList(this);" href="javascript:void(0);">删除</a>&nbsp;&nbsp;
					</li>
					<script id="multiEditor" type="text/plain" style="display:none"/>
					<script type="text/javascript">
						//如果已经存在则先销毁
						if(multiEditor!=null && multiEditor!=undefined){
							UE.getEditor("multiEditor").destroy();
						}
						var multiEditor = new InsertImage('multiEditor', 'localtaskpic', callback_insertMultiPic);
						function insertMultiPic(id){
							multiEditor.insertImage(id);
						}
						
						//图片回调
						function callback_insertMultiPic(t, result, actionData) {
							if(actionData==null || actionData==''){
							    for(var i in result){
							    	addPic_picList(result[i].src);
							    }
							}else{
								$("#"+actionData).attr("src", result[0].src);
								$("#"+actionData).parent().parent().find("[name$='picurl']").val(result[0].src);
							}
						}
						
						function addPic_picList(src){
							var seq = $("#picSeq").val()*1;
							
							$("#picTemplate").clone(true).appendTo($("#picUL"));
							var li = $("#picUL li").last();
							$(li).removeAttr("id").removeAttr("style");
							$(li).find("img").attr("id","pic"+seq).attr("src",src);
							$(li).find(":text[name='picUrl']").attr("name","entity.cmcLtPics["+seq+"].picurl").val(src);
							$(li).find(":text[name='sortIndex']").attr("name","entity.cmcLtPics["+seq+"].sortindex");
						
							$("#picSeq").val(seq+1);
						}
						
						//删除图片
						function delPic_picList(obj){
							$(obj).parent().remove();
						}
					</script>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>排序：</td>
					<td>
						<input id="sortindex" name="entity.sortindex" value="${entity==null?999:entity.sortindex}" maxlength="5"/>
						<span class="hint">（ 0至99999，默认999，值越小排序越靠前）</span>
					</td>
				</tr>
				<tr>
				 	<td></td>
					<td>特定奖励活动：</td>
					<td>
						<s:hidden id="actid" name="entity.actid"/>
						<span id="actTitle">
							<s:if test="entity.activity!=null">
								<s:if test="entity.activity.status==0">
								（<dict:text dataSource="xml" table="common" field="status" initvalue="${entity.activity.status }"/>）
								</s:if>
								${entity.activity.title }
							</s:if>
							<s:else>无</s:else>
						</span>
						&nbsp;&nbsp;
						<a href="javascript:selActivity();">选择活动</a>
						&nbsp;&nbsp;
						<a href="javascript:cancelActivity();">取消活动</a>
					</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>状态：</td>
					<td>
						<s:if test="entity.userid>0">
							<dict:text field="localTaskStatus" initvalue="${entity==null?1:entity.status}"/>
							<s:if test="entity.verifyresult!=null && entity.verifyresult!=''">（${entity.verifyresult }）</s:if>
							<s:hidden id="status" name="entity.status"/>
						</s:if>
						<s:else>
							<dict:radio field="localTaskStatus4Edit" name="entity.status" initvalue="${entity==null?1:entity.status}" cssClass="raddefault"/>
						</s:else>
					</td>
				</tr>
				<tr>
				 	<td></td>
					<td>发布时间：</td>
					<td><s:date name="entity.publishtime" format="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr>
				 	<td></td>
					<td>创建时间：</td>
					<td><s:date name="entity.createtime" format="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr>
				 	<td></td>
					<td>创建途径：</td>
					<td><dict:text field="localTaskCreateWay" initvalue="${entity.createway}"/></td>
				</tr>
				<tr height="30">
					<td colspan="3" align="center">
						<s:hidden id="taskid" name="entity.taskid"/>
						<input type="button" class="btn2" value="保存" onClick="saveData();" />
						<s:if test="@com.qcmz.cmc.constant.DictConstant@DICT_LOCALTASK_STATUS_VERIFYING==entity.status">
							<input type="button" class="btn2" value="通过审核" onClick="passVerify();" />
							<input type="button" class="btn2" value="驳回审核" onClick="showReject();" />
						</s:if>
						<input type="button" class="btn2" value="取消" onClick="backList();" />
						
						<div id="rejectDiv" style="position: absolute;width:500px;display: none;background-color: #ffffff">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableHeader">
						<tr>
							<td align="left" class="text13blackb" colspan="3" style="padding-left: 30px">驳回审核</td>
						</tr>
						<tr>
							<td width="30" align="right" class="textrse12">*</td>
							<td width="100">驳回原因：</td>
							<td><input type="text" id="rejectReason" style="width: 300px;"></td>
						</tr>
						<tr>
							<td colspan="3" align="center" height="40">
								<input type="button" class="btn2" value="确定驳回" onClick="rejectVerify();" />
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
