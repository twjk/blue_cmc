//保存配置
function saveConfig() {
	var url="configAction!doSave.do";
	var pars = $("#editForm").serialize();
	doOptCommon(url,pars,"reload()");
}