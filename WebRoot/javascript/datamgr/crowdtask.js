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
	
	var url = "crowdTaskAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function editData(id) {
	var url = "crowdTaskAction!doEdit.do";
	var pars = "";
	if(id){
		pars = "id=" + id;
	}
	doEdit(url, pars);
}

//选择备选题目
function selOptionSubject(){
	var template = $("#selSubject").val();
	var subject = '';
	if(template!=''){
		subject = template.replace('XXX', getSelectedText('selSubjectSource'));
	}
	$("#subjectContent").val(subject);
}

//从粘贴板替换题目链接,https或本地可用
//https://blog.csdn.net/lalala_dxf/article/details/128374242
//https://blog.csdn.net/FE_dev/article/details/120043150
function pasteSubjectUrl(){
	navigator.clipboard.readText()
	  .then(text => {
		  $("#subjectUrl").val(text);
	  })
	  .catch(err => {
	    console.error('Failed to read clipboard contents: ', err);
	  });
}

//清除题目链接
function clearSubjectUrl(){
	$("#subjectUrl").val('');
}

//选择奖励活动
function selActivity(){
	OpenWindow("选择奖励活动","rewardActivityAction!doSelect.do",700,300);
}
//选择奖励活动回调
function selActivityCallback(actid, title){
	$("#actId").val(actid);
	$("#actTitle").text(title);
}
//取消奖励活动
function cancelActivity(){
	$("#actId").val("");
	$("#actTitle").text("无");
}

function saveData() {
	var url = "crowdTaskAction!doSave.do";
	doSave(url);
}

function validator() {
	initValidator();
	if(!jQuery.formValidator.pageIsValid('1')) return false;
	return true;
}

function validator_update() {
	initValidator_update();
	if(!jQuery.formValidator.pageIsValid('1')) return false;
	return true;
}

function initValidator(){
	$.formValidator.initConfig({autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});   
    $("#taskTitle").formValidator().inputValidator({min:1,max:50,onerror:"请正确填写任务名称"});
    $("#peopleNum").formValidator().inputValidator({min:1,max:5,onerror:"请正确填写参与人数"});
    $("#subjectReward").formValidator().inputValidator({min:1,max:5,onerror:"请正确填写题目奖励金额"});
    $("#subjectContent").formValidator().inputValidator({min:1,max:200,onerror:"请正确填写题目"});
    $("#endTime").formValidator().inputValidator({min:1,max:100,onerror:"请正确填写结束时间"});
    $("#description").formValidator().inputValidator({min:1,max:2000,onerror:"请正确填写任务描述"});
}

function initValidator_update(){
	$.formValidator.initConfig({autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});   
    $("#taskTitle").formValidator().inputValidator({min:1,max:50,onerror:"请正确填写任务名称"});
    $("#peopleNum").formValidator().inputValidator({min:1,max:5,onerror:"请正确填写参与人数"});
    $("#subjectReward").formValidator().inputValidator({min:1,max:5,onerror:"请正确填写题目奖励金额"});
    $("#subjectContent").formValidator().inputValidator({min:1,max:200,onerror:"请正确填写题目"});
    $("#endTime").formValidator().inputValidator({min:1,max:100,onerror:"请正确填写结束时间"});
    $("#description").formValidator().inputValidator({min:1,max:2000,onerror:"请正确填写任务描述"});
}

function updateData(){
	if(validator_update()){
		var url = "crowdTaskAction!doUpdate.do";
		var pars = $('#editForm').serialize();
		doOptCommon(url,pars);
	}
	
}

function updateStatus(id, status, taskfreq, parentid){
	var url = "crowdTaskAction!doUpdateStatus.do";
	var pars = "id=" + id + "&status="+status;
	if(status=='0' && taskfreq=='2' && parentid=='0'){
		if (confirm("天天领父任务停用后，系统不再自动创建每天任务，确定要停用该父任务么？")) {
			doOptCommonQ(url, pars);
		}
	}else{
		doOptCommonQ(url, pars);
	}
}

//展示取消浮层
function showCancel(taskid, title){
	$("#cancelTaskId").val(taskid);
	$("#cancelTaskName").text(taskid+"-"+title);
	showBgiframe("cancelDiv",0,300);
	document.getElementById("btnCancel").focus();
}

//隐藏取消浮沉
function hideCancel(){
	$("#cancelDiv").hide();
	$("#cancelTaskId").val("");
	$("#cancelTaskName").text("");
	$("#cancelReason").text("超时未完成");
}

//取消
function saveCancel(){
	var url = "userCrowdTaskAction!doCancelBatch.do";
	var params = "taskId=" + $("#cancelTaskId").val()
			   + "&reason=" + $("#cancelReason").val()
			   + "&daysAgo=" + $("#daysAgo").val()
			   ;
	btnDisabled();
	$.ajax({type:'post',url:url, data:params, complete:function(result){
		var resp = result.responseText;
		if (!isNaN(resp)) {
			alert('操作成功！\n\n取消未完成用户任务数：'+resp);
			refreshList();
		} else {
			alert('操作失败！\n\n原因：'+resp);
		}
		btnEnabled();
	}});
}

function initEditor(){
}