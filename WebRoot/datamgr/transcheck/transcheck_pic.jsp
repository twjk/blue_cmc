<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
  	<head>
	<title></title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" media="screen" href="<lt:contextPath/>/javascript/iPicture-1.0.0/css/jQuery.iPicture.css"/>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/iPicture-1.0.0/js/jquery-1.6.2.min.js"></script>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/iPicture-1.0.0/js/jquery-ui-1.8.16.custom.min.js"></script>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/iPicture-1.0.0/js/jQuery.iPicture.js"></script>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/transcheck.js"></script>
	<script type="text/javascript">
	var zoom = 1;
	var other = ${other};
	jQuery(document).ready(function(){
		var img = new Image();
		img.src="${pic.picurl }";
		img.onload = function(){
			var imgWidth = this.width; 		//图片实际宽度
			var imgHeight = this.height;	//图片实际高度
			
			var clientWidth = $(window).width();
			var clientHeight = $(window).height();

			//图片适应
			var arr = calScale(imgWidth, imgHeight, clientWidth*0.7, clientHeight-80);	
			var width = arr[0];
			var height = arr[1];
			zoom = width/imgWidth;			
			$("#picture1").css("width",width).css("height",height);
			
			$("#iPicture").iPicture({
				animation: true,
				animationBg: "bgblack",
				animationType: <s:if test="canFinishCheck">"ltr-slide"</s:if><s:else>""</s:else>,
				pictures: ["picture1"],
				button: "moreblack",
				moreInfos:${ipictureJson},
				modify: <s:if test="canFinishCheck">true</s:if><s:else>false</s:else>,
				initialize: false,
				zoom: zoom
			});
		}
	});
	</script>
	
	</head>
	<body>
		<div style="height:10px"></div>
		<form name="editForm" action="#" id="editForm">
		<table width="100%" cellpadding="0" cellspacing="0" class="tableHeader" border="0" align="center">
		<tr>
			<td align="left" class="text13blackb" colspan="3" style="padding-left: 30px">图片翻译校对</td>
		</tr>
		<tr>
			<td width="28%" valign="top">
				<table width="100%" cellpadding="0" cellspacing="0" border="0" align="center" style="height:29px;line-height: 29px;">
				<tr>
					<td width="30" align="right"></td>
					<td width="100">订单编号：</td>
					<td>${pic.orderid }</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>用户编号：</td>
					<td>${pic.userid }</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>翻译语言：</td>
					<td>
						<s:if test="canFinishCheck">
							&nbsp;<dict:select id="from" name="from" field="textLang" initvalue="${pic.cmcROrder.fromlang}" cssClass="select2"/>
							&nbsp;<img onclick="exchangeLang();" style="vertical-align:middle;max-height: 16px;cursor: pointer;" src="<lt:contextPath/>/images/exchange.png">&nbsp;
							<dict:select id="to" name="to" field="textLang" initvalue="${pic.cmcROrder.tolang}" cssClass="select2"/>
						</s:if>
						<s:else>
							<dict:text field="textLang" initvalue="${pic.cmcROrder.fromlang}"/>
							&nbsp;->&nbsp;
							<dict:text field="textLang" initvalue="${pic.cmcROrder.tolang}"/>
						</s:else>
										
					</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>上传地点：</td>
					<td>${pic.cmcROrder.address }</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>处理状态：</td>
					<td><dict:text field="orderDealStatus" initvalue="${pic.cmcROrder.dealstatus }"/></td>
				</tr>
				<s:if test="pic.cmcROrder.opername!=null && pic.cmcROrder.opername!=''">
				<tr>
					<td align="right"></td>
					<td>处理人：</td>
					<td>${pic.cmcROrder.opername }</td>
				</tr>
				</s:if>
				<tr>
					<td align="right"></td>
					<td>处理时间：</td>
					<td>
						<s:date name="pic.cmcROrder.opertime" format="yyyy-MM-dd HH:mm:ss" />
						~
						<s:date name="pic.cmcROrder.finishtime" format="HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>校对状态：</td>
					<td><dict:text field="checkStatus" initvalue="${pic.cmcROrder.checkstatus }"/></td>
				</tr>
				<s:if test="pic.cmcROrder.opername!=null && pic.cmcROrder.opername!=''">
				<tr>
					<td align="right"></td>
					<td>校对人：</td>
					<td id="checkname" <s:if test="other">class="markR"</s:if>>${pic.cmcROrder.checkname }</td>
				</tr>
				</s:if>
				<tr>
					<td align="right"></td>
					<td>校对时间：</td>
					<td>
						<s:date name="pic.cmcROrder.checkstarttime" format="yyyy-MM-dd HH:mm:ss" />
						~
						<s:date name="pic.cmcROrder.checkfinishtime" format="HH:mm:ss" />
					</td>
				</tr>			
				<tr>
					<td align="right"></td>
					<td height="40" colspan="2" align="center">
						<input type="hidden" id="picId" name="picId" value="${pic.picid}" />
						<input type="hidden" id="orderId" name="orderId" value="${pic.orderid}" />							
						<input type="hidden" id="orderType" name="orderType" value="${pic.cmcROrder.ordertype}" />
						<input type="hidden" id="transResult" name="transResult"/>							
						<s:if test="canStartCheck">
							<input type="button" class="btn2" value="开始校对" onClick="startCheck();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
						</s:if>
						<s:if test="canFinishCheck">
							<input type="button" id="btnSave" class="btn2" value="保存校对" onClick="saveCheckPic();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" id="btnFinish" class="btn3" value="保存&完成校对" onClick="finishCheckPic();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
						</s:if>
						<input type="button" class="btn2" value="返回列表" onClick="refreshList();" />
					</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td colspan="2" style="line-height: 20px;;padding-top: 20px">
						<p style="font-weight: bolder;margin: 2px; overflow: visible">留言：</p>
						<textarea id="msg" style="width:100%;height:40px;"></textarea>
						<input type="button" class="btn3" value="给客户留言" onClick="saveMsg();" style="margin: 5px 0 5px 0"/>
						<s:if test="!pic.cmcROrder.cmcRMsgs.isEmpty()">
							<hr style="margin-top:1px;"/>
							<s:iterator value="pic.cmcROrder.cmcRMsgs" status="sts" id="l">
								<s:date name="createtime" format="MM-dd HH:mm:ss" />
								-- ${msgname }
								-- ${msg }
								<hr/>
							</s:iterator>
						</s:if>
					</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td colspan="2" style="line-height: 20px;padding-top: 20px">
						<p style="font-weight: bolder;margin: 2px">操作历史：</p>
						<hr style="margin-top:1px;"/>
						<s:iterator value="pic.cmcROrder.cmcRLogs" status="sts" id="l">
							<s:date name="opertime" format="MM-dd HH:mm:ss" />
							 -- ${opername }
							 -- <dict:text field="orderDealStatus" initvalue="${dealstatus}"/>
							 <s:if test="log!=null">
							 -- ${log }
							 </s:if>
							 <hr/>
						</s:iterator>
					</td>
				</tr>
				</table>
			</td>
			<td width="2%"></td>
			<td width="70%" style="text-align: center" valign="top">
				<div id="iPicture" style="margin:0 auto 0;">
		    		<div id="picture1" picurl="${pic.picurl }" style="position: relative; margin:0 auto 0;"></div>
				</div>
				<s:if test="canFinishCheck">
					<span>图片顺时针旋转</span>
					<input type="text" id="degree" style="width: 50px;" value="180">
					<span>度</span>
					<input type="button" class="btn2" value="旋转&刷新" onClick="rotatePic();" />
				</s:if>
			</td>
		</tr>
		</table>
		</form>
	</body>
</html>
