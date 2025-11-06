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
	
	var url = "userCrowdTaskAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function detailData(id) {
	var url = "userCrowdTaskAction!doDetail.do";
	var pars = "utId=" + id;
	doEdit(url, pars);
}

//展示取消浮层
function showCancel(utId, nick, title){
	$("#cancelUser").text(nick);
	$("#cancelTaskName").text(title+"-"+utId);
	$("#cancelUtId").val(utId);
	showBgiframe("cancelDiv",0,300);
}

//隐藏取消浮沉
function hideCancel(){
	$("#cancelDiv").hide();
	$("#cancelUser").text("");
	$("#cancelTaskName").text("");
	$("#cancelUtId").val("");
	$("#cancelReason").text("超时未完成");
}

//取消
function saveCancel(){
	var url = "userCrowdTaskAction!doCancel.do";
	var pars = "utId="+$("#cancelUtId").val()+"&reason="+$("#cancelReason").val();
	doOptCommon(url, pars);
}

//取消
function finishTask(utId){
	if (confirm("确定要强制完结用户任务【"+utId+"】吗？")) {
		var url = "userCrowdTaskAction!doFinish.do";
		var pars = "utId=" + utId;
		doOptCommonQ(url, pars);
	}
}

//用户加入黑名单
function addBlacklist(userId, nick){
	if (confirm("确定要将用户【"+nick+"】加入黑名单吗？加入黑名单，系统自动取消用户未完成任务且无法还原！")) {
		var url = "crowdTaskBlacklistAction!doAdd.do";
		var pars = "userId=" + userId;
		doOptCommonQ(url, pars);
	}
}

//用户移出黑名单
function deleteBlacklist(userId){
	var url = "crowdTaskBlacklistAction!doDelete.do";
	var pars = "userId=" + userId;
	doOptCommonQ(url, pars);
}

function initEditor(){
}