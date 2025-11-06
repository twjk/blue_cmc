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
	
	var url = "localTaskAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function editData(id) {
	var url = "localTaskAction!doEdit.do";
	var pars = "";
	if(id){
		pars = "id=" + id;
	}
	doEdit(url, pars);
}

function browseLink(){
	var url = $("#link").val();
	if(url!=null && url!=""){
		OpenWindowRight('查看', url, 600, 500);
	}
}

function selActivity(){
	OpenWindow("选择奖励活动","rewardActivityAction!doSelect.do",700,300);
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
	var url = "localTaskAction!doSave.do";
	var taskid = $("#taskid").val();
	if(taskid!=''){
		doSave(url);
		return;
	}
	
	if(validator()){
		var params = $('#editForm').serialize();
		btnDisabled();
		$.ajax({type:'post',url:url, data:params, complete:function(result){
			if (result.responseText == 'SUCCESS') {
				var again = confirm("操作成功，是否继续添加？");
				if (again) {
					btnEnabled();
					$("#title").val("");
					$("#job").val("");
					$("#jobrequire").val("");
					$("#picUL").empty();
				}else{
					refreshList();
				}
			} else {
				var reason = result.responseText;
				alert('操作失败！\n\n原因：'+reason);
			}
			btnEnabled();
		}});
	}
}

function addCompany(){
	OpenWindow("增加公司",contextPath+"/datamgr/localcompany/localcompany_add.jsp",770,600);
}

function addCompanyCallback(sourcename){
	$("#companyname").val(sourcename);
	initCompany();
}

function passVerify(){
	if (confirm("确定通过岗位审核吗？")) {
		var url = "localTaskAction!doPassVerify.do";
		var params = "id="+$("#taskid").val();
		doOptCommon(url, params);
	}
}

function showReject(){
	showBgiframe("rejectDiv",0,850);
}

function hideReject(){
	$("#rejectDiv").hide();
}

function rejectVerify(){
	$.formValidator.initConfig({autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});   
	$("#rejectReason").formValidator().inputValidator({min:1,max:200,onerror:"请正确填写驳回原因"});

	if(jQuery.formValidator.pageIsValid('1')){
		var url = "localTaskAction!doRejectVerify.do";
		var params = "id="+$("#taskid").val()+"&reason="+$("#rejectReason").val();
		doOptCommon(url, params);
	}
}

function updateStatus(id, status){
	var url = "localTaskAction!doUpdateStatus.do";
	var pars = "id=" + id + "&status="+status;
	doUpdate(url, pars);
}

function deleteData(id) {
	var url = "localTaskAction!doDelete.do";
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
    $("#title").formValidator().inputValidator({min:1,max:100,onerror:"请正确填写岗位"});
    $("#starttime").formValidator().inputValidator({min:1,max:20,onerror:"请正确填写有效期开始时间"});
    $("#endtime").formValidator().inputValidator({min:1,max:20,onerror:"请正确填写结束时间"});
    $("#sortindex").formValidator({empty:false}).regexValidator({regexp:"intege4",datatype:"enum",onerror:"请正确填写排序"});
}

function initEditor(){
	browseLink();
}