//提取众包任务
function doImportLib(){
	if (confirm("需要较长时间，确定要导入题库吗？")) {
		var url="crowdTaskDataMgrAction!doImportLib.do";
		var pars = "dirPath="+$("#crowdtask_importlib_dirPath").val()
				 + "&libName="+$("#crowdtask_importlib_libName").val()
				 ;
		doOptCommon(url,pars,"reload()");
	}
}

//删除众包任务的用户任务文件
function doDeleteUserCrowdTaskFile(){
	if (confirm("需要较长时间，确定要删除众包任务文件吗？")) {
		var url="crowdTaskDataMgrAction!doDeleteUserCrowdTaskFile.do";
		var pars = "start="+$("#usercrowdtask_deletefile_endtime_start").val()
				 + "&end="+$("#usercrowdtask_deletefile_endtime_end").val()
				 ;
		doOptCommon(url,pars,"reload()");
	}
}