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
	
	var url = "jobArticleAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function editData(id) {
	var url = "jobArticleAction!doEdit.do";
	var pars = "";
	if(id){
		pars = "id=" + id;
	}
	doEdit(url, pars);
}

function selActivity(){
	OpenWindow("选择活动","rewardActivityAction!doSelect.do",700,300);
}

function selActivityCallback(actid, title){
	$("#actid").val(actid);
	$("#actTitle").text(title);
}

function cancelActivity(){
	$("#actid").val("");
	$("#actTitle").text("无");
}

function saveData() {
	var url = "jobArticleAction!doSave.do";
	doSave(url);
}

function updateStatus(id, status){
	var url = "jobArticleAction!doUpdateStatus.do";
	var pars = "id=" + id + "&status="+status;
	doUpdate(url, pars);
}

function deleteData(id) {
	var url = "jobArticleAction!doDelete.do";
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
    $("#title").formValidator().inputValidator({min:1,max:200,onerror:"请正确填写名称"});
    $("#cat1").formValidator().inputValidator({min:1,onerror:"请选择分类"});
    $("#sortindex").formValidator({empty:false}).regexValidator({regexp:"intege4",datatype:"enum",onerror:"请正确填写排序"});
}

function initEditor(){
	initCat();
}