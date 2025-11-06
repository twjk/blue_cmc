<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  	<head>
	<title></title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" media="screen" href="<lt:contextPath/>/javascript/iPicture-1.0.0/css/jQuery.iPicture.css"/>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/iPicture-1.0.0/js/jquery-1.6.2.min.js"></script>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/iPicture-1.0.0/js/jquery-ui-1.8.16.custom.min.js"></script>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/iPicture-1.0.0/js/jQuery.iPicture.js"></script>
	<script type="text/javascript">
	var zoom = 1;
	jQuery(document).ready(function(){
		var img = new Image();
		img.src="${entity.picurl }";
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
				animationType: "",
				pictures: ["picture1"],
				button: "moreblack",
				moreInfos:${ipictureJson},
				modify: false,
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
			<td align="left" class="text13blackb" colspan="3" style="padding-left: 30px">图片翻译订单详细</td>
		</tr>
		<tr>
			<td width="28%" valign="top">
				<table width="100%" cellpadding="0" cellspacing="0" border="0" align="center" style="height:29px;line-height: 29px;">
				<tr>
					<td width="30" align="right"></td>
					<td width="120">图片编号：</td>
					<td>${entity.picid }</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>用户编号：</td>
					<td>${entity.userid }</td>
				</tr>
				<tr style="display: none">
					<td align="right"></td>
					<td>翻译途径：</td>
					<td><dict:text field="transWay" initvalue="${entity.transway }"/></td>
				</tr>
				<tr style="display: none">
					<td align="right"></td>
					<td>用户需求：</td>
					<td>${entity.cmcROrder.requirement }</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>翻译语言：</td>
					<td>
						<dict:text field="textLang" initvalue="${entity.fromlang}"/>
						&nbsp;->&nbsp;
						<dict:text field="textLang" initvalue="${entity.tolang}"/>					
					</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>原文：</td>
					<td>
						<textarea style="width:100%;height: 60px;" readonly="readonly">${entity.src }</textarea>
					</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>上传地点：</td>
					<td>${entity.cmcROrder.address }</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>总额/优惠/抵扣：</td>
					<td>
						<fmt:formatNumber type="currency" pattern="0.00" value="${entity.cmcROrder.amount}"/>
					 	/
					 	<fmt:formatNumber type="currency" pattern="0.00" value="${entity.cmcROrder.couponamount}"/>
					 	/
					 	<fmt:formatNumber type="currency" pattern="0.00" value="${entity.cmcROrder.walletamount}"/>
					</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>应付/已付（元）：</td>
					<td>
						<fmt:formatNumber type="currency" pattern="0.00" value="${entity.cmcROrder.payableamount}"/>
					 	/
					 	<fmt:formatNumber type="currency" pattern="0.00" value="${entity.cmcROrder.payamount}"/>
					</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>支付途径：</td>
					<td><dict:text field="tradeWay" initvalue="${entity.cmcROrder.tradeway}"/> </td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>创建时间：</td>
					<td><s:date name="entity.cmcROrder.createtime" format="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>提交时间：</td>
					<td><s:date name="entity.cmcROrder.waittime" format="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>处理状态：</td>
					<td><dict:text field="orderDealStatus" initvalue="${entity.cmcROrder.dealstatus }"/></td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>处理人：</td>
					<td>${entity.cmcROrder.opername }</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>处理时间：</td>
					<td>
						<s:date name="entity.cmcROrder.opertime" format="yyyy-MM-dd HH:mm:ss" />
						~
						<s:date name="entity.cmcROrder.finishtime" format="HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>校对状态：</td>
					<td><dict:text field="checkStatus" initvalue="${entity.cmcROrder.checkstatus }"/></td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>校对人：</td>
					<td>${entity.cmcROrder.checkname }</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>校对时间：</td>
					<td>
						<s:date name="entity.cmcROrder.checkstarttime" format="yyyy-MM-dd HH:mm:ss" />
						~
						<s:date name="entity.cmcROrder.checkfinishtime" format="HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td colspan="3" align="center" height="40">
						<input type="hidden" id="picId" name="picId" value="${entity.picid}" />
						<s:if test="@com.qcmz.cmc.util.TransPicUtil@canDelete(entity.cmcROrder.dealstatus)">
							<input type="button" id="btnSave" class="btn2" value="删除" onClick="deleteData('${entity.picid }');" />&nbsp;&nbsp;&nbsp;&nbsp;
						</s:if>
						<input type="button" class="btn2" value="返回" onClick="backList();" />
					</td>
				</tr>
				<tr>
					<td align="right"></td>
					<td colspan="2" style="height:20px;line-height: 20px;">
						<p style="font-weight: bolder;margin: 2px; overflow: visible">留言：</p>
						<textarea id="msg" style="width:100%;height:40px;"></textarea>
						<input type="button" class="btn2" value="给客户留言" onClick="saveMsg();" style="margin: 5px 0 5px 0"/>
						<s:if test="!entity.cmcROrder.cmcRMsgs.isEmpty()">
							<hr style="margin-top:1px;"/>
							<s:iterator value="entity.cmcROrder.cmcRMsgs" status="sts" id="l">
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
					<td colspan="2" style="height:20px;line-height: 20px;padding-top: 20px">
						<p style="font-weight: bolder;margin: 2px; overflow: visible">操作历史：</p>
						<hr style="margin-top:1px;"/>
						<s:iterator value="entity.cmcROrder.cmcRLogs" status="sts" id="l">
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
			<td width="70%" valign="top">
				<div id="iPicture" style="margin:0 auto 0;">
		    		<div id="picture1" picurl="${entity.picurl }" style="background-color: #393737; position: relative; margin:0 auto 0;"></div>
				</div>
			</td>
			
		</tr>
		</table>		
	</body>
</html>
