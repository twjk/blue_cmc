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
	
	var url = "rewardCashAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function editData(id){
	var pars = "id="+id;
	doEdit("rewardCashAction!doEdit.do", pars);
}

function showReject(){
	showBgiframe("rejectDiv",0,300);
}

function hideReject(){
	$("#rejectDiv").hide();
}

function selectRejectReason(){
	$("#rejectReason").val($("#selRejectReason").val());
}

function transfer(){
	if (confirm("系统会自动将钱款汇给用户，确定付款吗？")) {
		var id = $("#cashid").val();
		var url = "rewardCashAction!doTransfer.do";
		var params = "id="+id;
		btnDisabled();
		$.ajax({type:'post',url:url, data:params, complete:function(result){
			if (result.responseText == 'SUCCESS') {
				alert('操作成功！');
			} else {
				var reason = result.responseText;
				alert('操作失败！\n\n原因：'+reason);
			}
			btnEnabled();
			editData(id);
		}});
	}
}

function rejectCash(){
	$.formValidator.initConfig({autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});   
	$("#rejectReason").formValidator().inputValidator({min:1,max:200,onerror:"请正确填写驳回原因"});

	if(jQuery.formValidator.pageIsValid('1')){
		var params = "id="+$("#cashid").val()+"&reason="+$("#rejectReason").val();
		doOptCommon("rewardCashAction!doRejectCash.do", params);
	}
}

function initEditor(){
}
