function saveFuncLang(){
	var url="funcLangAction!doSave.do";
	var pars = $("#editForm").serialize();
	doOptCommon(url,pars,"reload()");
}

function checkAll(obj){
   	$("#ul"+obj.value).find(":checkbox[name='funclang']").each(function(){
   		this.checked = obj.checked;   		
   	});
}