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
	
	var url = "daySentenceAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function editData(id) {
	var url = "daySentenceAction!doEdit.do";
	var pars = "";
	if(id){
		pars = "id=" + id;
	}
	doEdit(url, pars);
}

function saveData() {
	var url = "daySentenceAction!doSave.do";
	doSave(url);
}

function deleteData(id) {
	var url = "daySentenceAction!doDelete.do";
	var pars = "id=" + id;
	doDelete(url, pars);
}

function updateStatus(id, status){
	var url = "daySentenceAction!doUpdateStatus.do";
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
    $("#smallpic").formValidator().inputValidator({min:1,max:200,onerror:"请正确上传小图"});
    $("#pic").formValidator().inputValidator({min:1,max:200,onerror:"请正确上传图片"});
}

function initEditor(){
	if($("#sentid").val()==''){
		var d = new Date();
		var s = d.getFullYear().toString() + '-'+addzero(d.getMonth() + 1)+"-"+addzero(d.getDate())+" 08:00:00";
		$("#releasetime").val(s);		
	}
}

function addzero(v) {
	if (v < 10) {
		return '0' + v;
	}
	return v.toString();
}

//创建静态页
function createHtml(id) {
	var url = "daySentenceAction!doHtml.do";
	var pars = "id=" + id;
	doOptCommon(url, pars);
}
