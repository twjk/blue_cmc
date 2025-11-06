//采集同城任务
function doSpiderLocalTask(){
	if (confirm("需要较长时间，确定要采集同城任务吗？")) {
		var url="dataMgrAction!doSpiderLocalTask.do";
		var pars = "";
		doOptCommon(url,pars,"reload()");
	}
}

//就业精选数据完善
function doRefineLocalTask(){
	if (confirm("需要较长时间，确定要完善就业精选数据吗？")) {
		var url="dataMgrAction!doRefineLocalTask.do";
		var pars = "";
		doOptCommon(url,pars,"reload()");
	}
}


//同城信息采集结果分拣
function doSpiderSorting(){
	if (confirm("需要较长时间，确定要分拣同城信息采集结果吗？")) {
		var url="dataMgrAction!doSpiderSorting.do";
		var pars = "spdDate="+$("#spdDate").val();
		doOptCommon(url,pars,"reload()");
	}
}

//同城信息采集结果转就业信息
function doToJobArticle(){
	if (confirm("需要较长时间，确定要将同城信息采集结果转就业信息吗？")) {
		var url="dataMgrAction!doToJobArticle.do";
		var pars = "spdDate="+$("#spdDate").val();
		doOptCommon(url,pars,"reload()");
	}
}

//下载就业信息图片
function doDownloadJobArticlePic(){
	if (confirm("需要较长时间，确定要下载就业信息外部图片吗？")) {
		var url="dataMgrAction!doDownloadJobArticlePic.do";
		var pars = "";
		doOptCommon(url,pars,"reload()");
	}
}

//重新生成每日一句静态页
function doHtmlDaySentence(){
	if (confirm("需要较长时间，确定要重新生成每日一句静态页吗？")) {
		var url="dataMgrAction!doHtmlDaySentence.do";
		var pars = "";
		doOptCommon(url,pars,"reload()");
	}
}

//佣金结算
function doOrderCommission(){
	if (confirm("需要较长时间，确定要结算佣金吗？")) {
		var url="dataMgrAction!doOrderCommission.do";
		var pars = "start="+$("#commission_start").val()
				 + "&end="+$("#commission_end").val()
				 + "&operCd="+$("#commission_operCd").val()
				 ;
		doOptCommon(url,pars,"reload()");
	}
}

//佣金重新结算
function doOrderReCommission(){
	if (confirm("需要较长时间，确定要重新结算佣金吗？")) {
		var url="dataMgrAction!doOrderReCommission.do";
		var pars = "start="+$("#commission_start").val()
				 + "&end="+$("#commission_end").val()
				 + "&operCd="+$("#commission_operCd").val()
				 ;
		doOptCommon(url,pars,"reload()");
	}
}

//提取众包任务
function doExtractCrowdTask(){
	if (confirm("需要较长时间，确定要提取众包任务数据吗？")) {
		var url="dataMgrAction!doExtractCrowdTask.do";
		var pars = "taskIds="+$("#crowdtask_extract_taskIds").val()
				 + "&start="+$("#crowdtask_extract_finishtime_start").val()
				 + "&end="+$("#crowdtask_extract_finishtime_end").val()
				 + "&baseDir="+$("#crowdtask_extract_baesDir").val()
				 + "&lastId="+$("#crowdtask_extract_lastId").val()
				 ;
		doOptCommon(url,pars,"reload()");
	}
}

//奖励金提现打款
function doRewardCashTransfer(){
	if (confirm("需要较长时间，确定要处理奖励金提现的向用户打款吗？")) {
		var url="dataMgrAction!doRewardCashTransfer.do";
		var pars = "maxId="+$("#reward_cash_transfer_maxCashId").val()
				 + "&lastId="+$("#reward_cash_transfer_minCashId").val()
		;
		doOptCommon(url,pars,"reload()");
	}
}

//语音识别
function doAsr(){
	if (confirm("需要较长时间，确定要语音识别吗？")) {
		var url="dataMgrAction!doAsr.do";
		var pars = "lang="+$("#speech_asr_lang").val()
				 + "&proxyId="+$("#speech_asr_proxyId").val()
				 + "&baseDir="+$("#speech_asr_baesDir").val()
				 ;
		doOptCommon(url,pars,"reload()");
	}
}