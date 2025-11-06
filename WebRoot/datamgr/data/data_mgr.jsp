<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
  <head>
	<title>数据维护</title>
	<jsp:include page="/common/head.jsp" />
	<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/datamgr.js"></script>
  </head>
  
  <body>
  	<div id="funcNavigator" class="navDiv" align="left" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
		<tr>
		    <td width="1%">&nbsp;</td>
		    <td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
		    <td width="98%" class="text12black">&nbsp;现在您的位置：系统管理  &gt;&gt; 数据维护</td>
		</tr>
		</table>
	</div>	
	<br/>
	<form id="editForm" name="editForm" action="#">
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th width="98%">同城信息采集</th>
			</tr>			
			<tr>
				<td></td>
				<td>
					<input type="button" class="btn2" value="采集" onClick="doSpiderLocalTask();" 
						<s:if test="!@com.qcmz.cmc.config.SystemConfig@isDebug()">disabled="disabled"</s:if> />
					<span class="hint">（使用chrome插件，本地调用）</span>
					<%--
					<br/>
					<input type="button" class="btn2" value="下载图片" onClick="doDownloadJobArticlePic();" 
						<s:if test="!@com.qcmz.cmc.config.SystemConfig@isRelease()"> disabled="disabled"</s:if> />
					<span class="hint">（下载就业信息外部图片，正式环境下才能下载）</span>
					--%>
				</td>
			</tr>
		</table>
		<br/>
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th width="98%" colspan="2">同城采集分拣</th>
			</tr>
			<tr>
				<td class="required">*</td>
				<td width="10%">采集日期：</td>
				<td><input id="spdDate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/></td>
			</tr>
			<tr height="30">
				<td align="right"></td>
				<td colspan="2">
					<input type="button" class="btn2" value="分拣" onClick="doSpiderSorting();" />
					<span class="hint">（利用AI网页分析后将采集结果转入就业精选或就业信息）</span>
					<br/>
					<input type="button" class="btn2" value="转就业信息" onClick="doToJobArticle();" />
					<span class="hint">（根据标题将采集结果分类后转入就业信息）</span>
				</td>
			</tr>
		</table>
		<br/>
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th width="98%">就业精选数据完善</th>
			</tr>			
			<tr>
				<td></td>
				<td>
					<input type="button" class="btn2" value="完善" onClick="doRefineLocalTask();" 
					<span class="hint">（根据reward赋值最小薪酬、最大薪酬、基准薪酬）</span>
				</td>
			</tr>
		</table>
		<br/>
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th width="98%" colspan="2">创建每日一句静态页</th>
			</tr>			
			<tr height="30">
				<td align="right"></td>
				<td colspan="2">
					<input id="btnSyn" type="button" class="btn2" value="创建静态页" onClick="doHtmlDaySentence();" />
				</td>
			</tr>
		</table>
		<br/>
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th width="98%" colspan="2">译员佣金结算</th>
			</tr>
			<tr>
				<td class="required">*</td>
				<td width="10%">日期：</td>
				<td>
					<input id="commission_start" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
					&nbsp;-&nbsp;
					<input id="commission_end" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>译员用户名：</td>
				<td><input id="commission_operCd" value=""/></td>
			</tr>
			<tr height="30">
				<td align="right"></td>
				<td colspan="2">
					<input type="button" class="btn2" value="结 算" onClick="doOrderCommission();" />
					<span class="hint">（只处理待结算的订单）</span>
					<br/>
					<input type="button" class="btn2" value="重新结算" onClick="doOrderReCommission();" />
					<span class="hint">（处理待结算和已结算的订单，适用于结算出错或者结算规则变更）</span>
				</td>
			</tr>
		</table>
		<br/>
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th width="98%" colspan="2">提取众包任务数据</th>
			</tr>
			<tr>
				<td class="required">*</td>
				<td width="10%">任务编号：</td>
				<td>
					<input id="crowdtask_extract_taskIds" value="30"/>
					<span class="hint">（多个用,分隔，数字[36]，文本[30]）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>完成日期：</td>
				<td>
					<input id="crowdtask_extract_finishtime_start" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
					&nbsp;-&nbsp;
					<input id="crowdtask_extract_finishtime_end" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>最后处理用户任务编号：</td>
				<td><input id="crowdtask_extract_lastId" value=""/></td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>存储路径：</td>
				<td>
					<input id="crowdtask_extract_baesDir" value="c:/test/crowdtask/txt"/>
					<span class="hint">（本地[c:/test/crowdtask/number]）</span>
				</td>
			</tr>
			<tr height="30">
				<td align="right"></td>
				<td colspan="2">
					<input type="button" class="btn2" value="提取" onClick="doExtractCrowdTask();" />
				</td>
			</tr>
		</table>
		<br/>
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th width="98%" colspan="2">语音识别</th>
			</tr>
			<tr>
				<td class="required">*</td>
				<td width="10%">语言：</td>
				<td><dict:select id="speech_asr_lang" field="speechLang" /></td>
			</tr>
			<tr>
				<td></td>
				<td>代理：</td>
				<td>
					<select id="speech_asr_proxyId">
						<option value="">请选择</option>
						<option value="13">百度</option>
						<option value="10">Google</option>
						<option value="11">微软Azure</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>文件路径：</td>
				<td>
					<input id="speech_asr_baesDir" value="C:/test/hcicloud/英语/wav"/>
					<span class="hint">（识别结果文件名：语音文件名_代理Code.txt）</span>
				</td>
			</tr>
			<tr height="30">
				<td align="right"></td>
				<td colspan="2">
					<input type="button" class="btn2" value="识别" onClick="doAsr();" />
				</td>
			</tr>
		</table>
		<br/>
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th width="98%" colspan="2">奖励金提现</th>
			</tr>
			<tr>
				<td></td>
				<td width="10%">最小提现编号：</td>
				<td><input id="reward_cash_transfer_minCashId"/></td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>最大提现编号：</td>
				<td>
					<input id="reward_cash_transfer_maxCashId"/>
					<span class="hint">（避免手工与自动重复给用户打款）</span>
				</td>
			</tr>		
			<tr height="30">
				<td align="right"></td>
				<td colspan="2">
					<input type="button" class="btn2" value="向用户付款" onClick="doRewardCashTransfer();" />
					<span class="hint">（批量处理奖励金提现向用户打款）</span>
				</td>
			</tr>
		</table>
		<br/>
	</form>
  </body>
</html>
