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
	
	var url = "productAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function editData(id) {
	var url = "productAction!doEdit.do";
	var pars = "";
	if(id){
		pars = "id=" + id;
	}
	doEdit(url, pars);
}

function saveData() {
	$("#pic").val($("#selPic").attr("src"));
	var url = "productAction!doSave.do";
	doSave(url);
}

function deleteData(id) {
	var url = "productAction!doDelete.do";
	var pars = "id=" + id;
	doDelete(url, pars);
}

function updateStatus(id, status){
	var url = "productAction!doUpdateStatus.do";
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
    $("#title").formValidator().inputValidator({min:1,max:200,onerror:"请正确填写标题"});
    $("#src").formValidator().inputValidator({min:1,max:500,onerror:"请正确填写原文"});
    $("#dst").formValidator().inputValidator({min:1,max:500,onerror:"请正确填写译文"});
    $("#pic").formValidator().inputValidator({min:1,max:200,onerror:"请正确上传图片"});
}

function initEditor(){
}