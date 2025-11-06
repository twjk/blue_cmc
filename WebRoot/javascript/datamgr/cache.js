function reloadCache(){	
	var url="cacheAction!doRefresh.do";
	var pars = $("#editForm").serialize();
	doOptCommon(url,pars,"reload()");
}

function check(){
	checkEvent("beanid","checkAll");
}