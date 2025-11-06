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
	
	var url = "transLibAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	if($("#sortHitCount").attr("class")=="sortby"){
		pars = pars + "&orderby=hitcount desc";
	}
	doQuery(url, pars);
}

function sortHitCount(){
	if($("#sortHitCount").attr("class")=="sortby"){
		$("#sortHitCount").attr("class","unsortby");
	}else{
		$("#sortHitCount").attr("class","sortby");
	}
	dataQuery();
}

function changeTtsMethod(){
	var ttsMethod = getRadValue("entity.ttssrc");
	if(ttsMethod==2){		//指定译文合成
		$("#ttstext").show();
	}else{					//其他
		$("#ttstext").hide();
	}
}

function editData(libClass, id) {
	var url = "transLibAction!doEdit.do";
	var pars = "libClass="+libClass;
	if(id){
		pars = pars + "&id=" + id;
	}
	doEdit(url, pars);
}

function saveData() {
	var url = "transLibAction!doSave.do";
	doSave(url);
}

function updateStatus(libClass, id, status){
	var url = "transLibAction!doUpdateStatus.do";
	var pars = "libClass=" + libClass + "&id=" + id + "&status="+status;
	doOptCommonQ(url, pars);
}

function updateCheckStatus(libClass, id, checkstatus){
	var url = "transLibAction!doUpdateCheckStatus.do";
	var pars = "libClass=" + libClass + "&id=" + id + "&checkStatus="+checkstatus;
	doOptCommonQ(url, pars);
}

function deleteData(libClass, id) {
	var url = "transLibAction!doDelete.do";
	var pars = "libClass=" + libClass + "&id=" + id;
	doDelete(url, pars);
}

function refreshCache(){
	var url="cacheAction!doRefresh.do";
	var pars = "beanid=transLibMap";
	doOptCommon(url,pars);
}

function validator() {
	initValidator();
	if(!jQuery.formValidator.pageIsValid('1')) return false;
	return true;
}

function initValidator(){
	$.formValidator.initConfig({autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});   
    $("#src").formValidator().inputValidator({min:1,max:250,onerror:"请正确填写原文"});
    $("#dst").formValidator().inputValidator({min:1,max:250,onerror:"请正确填写译文"});
}

function initEditor(){
	initCat();
	changeTtsMethod();
}
