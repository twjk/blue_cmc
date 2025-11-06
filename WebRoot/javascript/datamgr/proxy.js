function saveProxyFunc(){
	var url="proxyFuncAction!doSave.do";
	var pars = $("#editForm").serialize();
	doOptCommon(url,pars,"reload()");
}