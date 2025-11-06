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
	<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/transdeal.js"></script>
	<script type="text/javascript">
	var zoom = 1;
	var other = ${other};
	jQuery(document).ready(function(){
		var img = new Image();
		img.src="${order.cmcPtPic.picurl }";
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
				animationType: <s:if test="canTrans">"ltr-slide"</s:if><s:else>""</s:else>,
				pictures: ["picture1"],
				button: "moreblack",
				moreInfos:${ipictureJson},
				modify: <s:if test="canTrans">true</s:if><s:else>false</s:else>,
				initialize: false,
				zoom: zoom
			});
		}
	});
	</script>
	
	</head>
	<body>
		<div style="height:10px"></div>
		
		<table width="100%" cellpadding="0" cellspacing="0" class="tableHeader" border="0" align="center">
		<tr>
			<td align="left" class="text13blackb" colspan="3" style="padding-left: 30px">图片翻译</td>
		</tr>
		<tr>
			<td width="28%" valign="top">
				<table width="100%" cellpadding="0" cellspacing="0" border="0" align="center" style="height:29px;line-height: 29px;">
				<tr>
					<td width="30" align="right"></td>
					<td width="100">订单编号：</td>
					<td>${order.orderid}</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>用户编号：</td>
					<td>${order.userid }</td>
				</tr>
				<tr style="display: none">
					<td align="right"></td>
					<td>翻译途径：</td>
					<td><dict:text field="transWay" initvalue="${order.cmcPtPic.transway }"/></td>
				</tr>
				<tr style="display: none">
					<td align="right"></td>
					<td>用户需求：</td>
					<td>${order.requirement }</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>翻译语言：</td>
					<td>
						<s:if test="canTrans||canFinish">
							&nbsp;<dict:select id="from" field="textLang" initvalue="${order.fromlang}"/>
							&nbsp;<img onclick="exchangeLang();" style="vertical-align:middle;max-height: 16px;cursor: pointer;" src="<lt:contextPath/>/images/exchange.png">&nbsp;
							<dict:select id="to" field="textLang" initvalue="${order.tolang}"/>
						</s:if>
						<s:else>
							<dict:text field="textLang" initvalue="${order.fromlang}"/>
							&nbsp;->&nbsp;
							<dict:text field="textLang" initvalue="${order.tolang}"/>
						</s:else>
					</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>预计翻译时长：</td>
					<td>
						<s:if test="canFinish">
							<dict:select id="needTime" field="orderNeedTime" initvalue="${order.needtime }" inithead="0"/>
						</s:if>
						<s:else>${order.needTimeStr}
						</s:else>
					</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>实际翻译时长：</td>
					<td>${order.dealTimeStr }</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>原文：</td>
					<td><textarea style="width:100%;height: 80px;" readonly="readonly">${order.cmcPtPic.src }</textarea></td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>处理状态：</td>
					<td><dict:text field="orderDealStatus" initvalue="${order.dealstatus }"/></td>
				</tr>
				<s:if test="order.opername!=null && order.opername!=''">
				<tr>
					<td align="right"></td>
					<td>处理人：</td>
					<td id="opername" <s:if test="other">class="markR"</s:if>>${order.opername }</td>
				</tr>
				</s:if>
				<tr>
					<td align="right"></td>
					<td>创建时间：</td>
					<td><s:date name="order.createtime" format="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>完成时间：</td>
					<td><s:date name="order.finishtime" format="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>上传地点：</td>
					<td>${order.address }</td>
				</tr>				
				<tr>
					<td align="right"></td>
					<td height="40" colspan="2" align="center">
						<input type="hidden" id="orderId" value="${order.orderid}" />
						<input type="hidden" id="orderType" value="${order.ordertype}" />
						<s:if test="canStart">
							<input type="button" class="btn2" value="开始翻译" onClick="startTrans();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
						</s:if>
						<s:if test="canTrans">
							<input type="button" id="btnSave" class="btn2" value="保存" onClick="savePic();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
						</s:if>
						<s:if test="canFinish">
							<input type="button" id="btnFinish" class="btn2" value="保存&完成" onClick="finishPic();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
						</s:if>
						<s:if test="canCancel">
							<input type="button" class="btn2" value="拒单&退款" onClick="showCancel();" />
							<div id="cancelDiv" style="position: absolute;width:500px;display: none;background-color: #ffffff">
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableHeader">
								<tr>
									<td align="left" class="text13blackb" colspan="3" style="padding-left: 30px">取消翻译并退款用户</td>
								</tr>
								<tr>
									<td width="30" align="right" class="textrse12">*</td>
									<td width="100">拒单原因：</td>
									<td width="350"><input type="text" id="cancelReason" style="width: 340px;"></td>
								</tr>
								<tr>
									<td></td>
									<td>备选原因：</td>
									<td>
										<select id="selCancelReason" onchange="selectCancelReason();" style="width:340px;">
											<option value="">--请选择--</option>							
											<option value="感谢您使用出国翻译官。重复提交，请您重新确认，期待您的再次使用。">感谢您使用出国翻译官。重复提交，请您重新确认，期待您的再次使用。</option>
											<option value="感谢您使用出国翻译官，基于您的翻译需求，在所提交的图片中没有识别出相应字符，请您重新确认后提交，期待您的再次使用。">感谢您使用出国翻译官，基于您的翻译需求，在所提交的图片中没有识别出相应字符，请您重新确认后提交，期待您的再次使用。</option>
											<option value="感谢您使用出国翻译官。暂不支持该语种的中文翻译，期待您的再次使用。">感谢您使用出国翻译官。暂不支持该语种的中文翻译，期待您的再次使用。</option>
											<option value="感谢您使用出国翻译官。您所提供的图片不清晰无法完全识别，建议您重新提交清晰图片。">感谢您使用出国翻译官。您所提供的图片不清晰无法完全识别，建议您重新提交清晰图片。</option>
										</select>
									</td>
								</tr>
								<tr>
									<td colspan="3" align="center" height="40">
										<input type="button" class="btn2" value="确定拒单" onClick="saveCancel();" />
										<input type="button" class="btn2" value="关闭" onClick="hideCancel();" />
									</td>
								</tr>
								</table>
							</div>
							&nbsp;&nbsp;&nbsp;&nbsp;
						</s:if>
						<s:if test="canHandover">
							<input type="button" id="btnHandover" class="btn2" value="转单" onClick="handover();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
						</s:if>
						<input type="button" class="btn2" value="关闭" onClick="closeAndRefresh();" />
					</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td colspan="2" style="line-height: 20px;padding-top: 20px;">
						<p style="font-weight: bolder;margin: 2px; overflow: visible">留言：</p>
						<textarea id="msg" style="width:100%;height:40px;"></textarea>
						<input type="button" class="btn8" value="给客户留言" onClick="saveMsg();" style="margin: 5px 0 5px 0"/>
						<s:if test="!order.cmcRMsgs.isEmpty()">
							<hr style="margin-top:1px;"/>
							<s:iterator value="order.cmcRMsgs" status="sts" id="l">
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
						<s:iterator value="order.cmcRLogs" status="sts" id="l">
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
		    		<div id="picture1" picurl="${order.cmcPtPic.picurl }" style="position: relative; margin:0 auto 0;"></div>
				</div>
				<s:if test="canTrans">
					<span>图片顺时针旋转</span>
					<input type="text" id="degree" style="width: 50px;" value="180">
					<span>度</span>
					<input type="button" class="btn2" value="旋转&刷新" onClick="rotatePic();" />
				</s:if>
			</td>
		</tr>
		</table>
	</body>
</html>
