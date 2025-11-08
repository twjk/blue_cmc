<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.qcmz.cmc.config.SystemConfig"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt"%>
<html>
  <head>
	<title></title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/dialog.js"></script>
	<style type="text/css">
		html, body {
			height: 100%;
			margin: 0;
			padding: 0;
			overflow: hidden;
		}
		body {
			display: flex;
			flex-direction: column;
			position: relative;
		}
		#funcNavigator {
			flex-shrink: 0;
		}
		#searchArea {
			flex-shrink: 0;
		}
		#editorArea {
			flex-shrink: 0;
		}
		#resultArea {
			flex: 1;
			min-height: 0;
			position: relative;
			overflow: hidden;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			// 动态调整resultArea高度，使其充满剩余空间
			function adjustResultAreaHeight() {
				try {
					var windowHeight = window.innerHeight || $(window).height();
					var navigatorHeight = $('#funcNavigator').outerHeight(true) || 32;
					var searchAreaHeight = $('#searchArea').outerHeight(true) || 100;
					var editorAreaHeight = $('#editorArea').is(':visible') ? $('#editorArea').outerHeight(true) : 0;
					var padding = 20;
					var resultAreaHeight = windowHeight - navigatorHeight - searchAreaHeight - editorAreaHeight - padding;
					
					if (resultAreaHeight > 300) {
						$('#resultArea').css({
							'height': resultAreaHeight + 'px',
							'min-height': '400px'
						});
					}
				} catch(e) {
					console.error('调整resultArea高度失败:', e);
				}
			}
			
			// 延迟执行，确保DOM完全加载
			setTimeout(function() {
				adjustResultAreaHeight();
			}, 100);
			
			$(window).on('resize', function() {
				setTimeout(adjustResultAreaHeight, 50);
			});
			
			// 监听editorArea显示/隐藏
			if (window.MutationObserver) {
				var observer = new MutationObserver(function(mutations) {
					setTimeout(adjustResultAreaHeight, 50);
				});
				var editorArea = document.getElementById('editorArea');
				if (editorArea) {
					observer.observe(editorArea, { 
						attributes: true, 
						attributeFilter: ['style', 'class'],
						childList: false,
						subtree: false
					});
				}
			}
		});
		
		// 将adjustResultAreaHeight函数暴露为全局函数，供getList调用
		window.adjustResultAreaHeight = function() {
			try {
				var windowHeight = window.innerHeight || $(window).height();
				var navigatorHeight = $('#funcNavigator').outerHeight(true) || 32;
				var searchAreaHeight = $('#searchArea').outerHeight(true) || 100;
				var editorAreaHeight = $('#editorArea').is(':visible') ? $('#editorArea').outerHeight(true) : 0;
				var padding = 20;
				var resultAreaHeight = windowHeight - navigatorHeight - searchAreaHeight - editorAreaHeight - padding;
				
				if (resultAreaHeight > 300) {
					$('#resultArea').css({
						'height': resultAreaHeight + 'px',
						'min-height': '400px'
					});
				}
			} catch(e) {
				console.error('调整resultArea高度失败:', e);
			}
		};
	</script>
  </head>
  
  <body>
  	<div id="funcNavigator" class="navDiv" align="left" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
		<tr>
		    <td width="1%">&nbsp;</td>
		    <td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
		    <td width="98%" class="text12black">&nbsp;现在您的位置：对话管理  &gt;&gt; 对话维护</td>
		</tr>
		</table>
	</div>	
	<!-- 查询区域 -->
	<div id="searchArea" style="width:98%; margin:10px auto 0">
		<form name="qryForm" id="qryForm" action="#">
		<table border="0" align="center" cellpadding="0" cellspacing="4" width="100%" class="condition" >
		    <tr height="6"><td colspan="6"></td></tr>
			<tr>
				<td class="textRight" width="8%">用户编号：</td>
				<td class="textLeft" width="25%"><input id="qry_userid" name="userid" /></td>
				<td class="textRight" width="8%">处理状态：</td>
				<td class="textLeft"  width="25%"><dict:select id="qry_dealstatus" name="dealstatus" field="dialogDealStatus" initvalue="1" inithead="0"/></td>
				<td class="textRight" width="9%"></td>
				<td class="textLeft" width="25%">
					<input class="btn2" id="btnQuery" type="button" value="查 询" onClick="dataQuery();" />
					<input class="btn2" type="reset" value="重 置"/>
				</td>
			</tr>
			<tr height="6"><td colspan="6"></td></tr>
	  </table>
	  </form>
	</div>
	
	<!-- 数据编辑区域  -->
	<div id="editorArea" class="editDiv" style="display:none; margin:0 auto 0"></div>
	
	<!-- 查询结果区域  -->
	<div id="resultArea"></div>
  </body>
</html>
