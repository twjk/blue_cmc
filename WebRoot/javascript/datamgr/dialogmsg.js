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
	
	var url = "dialogMsgAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function detailData(id) {
	var url = "dialogAction!doDetail.do";
	var pars = "dialogId=" + id;
	doEdit(url, pars);
}

//保存消息
function saveMsg(){
	var dialogId = $("#dialogId").val();
	var msg = $("#msg").val();
	if(msg=='' && !confirm("消息内容为空，确定要无回复处理吗？")){
		return;
	}
	
	var url = "dialogMsgAction!doAddMsg.do";
	var params = "dialogId="+dialogId+"&msg="+encodeURIComponent(msg);
	
	doOptCommon(url, params, "detailData('"+dialogId+"')");
}

function initEditor(){
	
}