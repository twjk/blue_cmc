function dataQuery(page) {
	//对跳转的页码判断
	if (page != null) {
		if(isNaN(page)){
			alert("输入的页码不正确！");
			return;
		}
		$("#nowpage").val(page);
	}else
		$("#nowpage").val(1);
	
	var url = "dubberAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function editData(id) {
	var url = "dubberAction!doEdit.do";
	var pars = "";
	if(id){
		pars = "id=" + id;
	}
	doEdit(url, pars);
}

function saveData() {
	var url = "dubberAction!doSave.do";
	doSave(url);
}

function updateStatus(id, status){
	var url = "dubberAction!doUpdateStatus.do";
	var pars = "id=" + id + "&status="+status;
	doUpdate(url, pars);
}

function validator() {
	initValidator();
	if(!jQuery.formValidator.pageIsValid('1')) return false;
	return true;
}

function initValidator(){
	$.formValidator.initConfig({autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});   
    $("#title").formValidator().inputValidator({min:1,max:200,onerror:"请正确填写名称"});
}

function initEditor(){
	initCat();
}