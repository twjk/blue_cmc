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
	
	var url = "transMachineAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function editData(id) {
	var url = "transMachineAction!doEdit.do";
	var pars = "";
	if(id){
		pars = "id=" + id;
	}
	doEdit(url, pars);
}


function saveData() {
	var url = "transMachineAction!doSave.do";
	doSave(url);
}

function deleteData(id) {
	var url = "transMachineAction!doDelete.do";
	var pars = "id=" + id;
	doDelete(url, pars);
}


function validator() {
	initValidator();
	if(!jQuery.formValidator.pageIsValid('1')) return false;
	return true;
}

function initValidator(){
	$.formValidator.initConfig({autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});   
    $("#tmcode").formValidator().inputValidator({min:17,max:17,onerror:"请正确填写翻译机编码"});   
}

function initEditor(){
}
