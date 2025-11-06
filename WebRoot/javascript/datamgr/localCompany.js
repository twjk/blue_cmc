function dataQuery(page) {
	if (page != null) {
		if(isNaN(page)){
			alert("输入的页码不正确！");
			return;
		}
		$("#nowpage").val(page);
	}else
		$("#nowpage").val(1);
	
	var url = "localCompanyAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);		
}

function editData(id) {
	var url = "localCompanyAction!doEdit.do";
	var pars = "";
	if(id){
		pars = "id=" + id;
	}
	doEdit(url, pars);
}

function validator() {
	initValidator();
	if(!jQuery.formValidator.pageIsValid('1')) return false;
	return true;
}

function initValidator(){
	$.formValidator.initConfig({autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});   
	$("#companyname").formValidator().inputValidator({min:1,max:100,onerror:"请正确填写公司名称"}); 
}

function saveData() {
	var url = "localCompanyAction!doSave.do";
	doSave(url);
}

function saveData4Pop(){
	doSave("localCompanyAction!doSave.do", 'saveDataSuccess()');
}

function saveDataSuccess(){
	window.opener.addCompanyCallback($("#companyname").val());
	window.close();
}

function showReject(){
	showBgiframe("rejectDiv",0,300);
}

function hideReject(){
	$("#rejectDiv").hide();
}

function passCertify(){
	if (confirm("确定通过公司认证吗？")) {
		var url = "localCompanyAction!doPassCertify.do";
		var params = "id="+$("#companyid").val();
		doOptCommon(url, params);
	}
}

function rejectCertify(){
	$.formValidator.initConfig({autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});   
	$("#rejectReason").formValidator().inputValidator({min:1,max:200,onerror:"请正确填写驳回原因"});

	if(jQuery.formValidator.pageIsValid('1')){
		var url = "localCompanyAction!doRejectCertify.do";
		var params = "id="+$("#companyid").val()+"&reason="+$("#rejectReason").val();
		doOptCommon(url, params);
	}
}


function deleteData(id) {
	var url = "localCompanyAction!doDelete.do";
	var pars = "id=" + id;
	doDelete(url, pars);
}

function initEditor(){

}

