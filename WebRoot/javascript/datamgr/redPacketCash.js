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
	
	var url = "redPacketCashAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function editData(id){
	var pars = "id="+id;
	doEdit("redPacketCashAction!doEdit.do", pars);
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
	if (confirm("系统会自动将钱款汇入用户微信零钱，确定付款吗？")) {
		doOptCommon("redPacketCashAction!doTransfer.do", "id="+$("#cashid").val());
	}
}

function rejectCash(){
	$.formValidator.initConfig({autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});   
	$("#rejectReason").formValidator().inputValidator({min:1,max:200,onerror:"请正确填写驳回原因"});

	if(jQuery.formValidator.pageIsValid('1')){
		var params = "id="+$("#cashid").val()+"&reason="+$("#rejectReason").val();
		doOptCommon("redPacketCashAction!doRejectCash.do", params);
	}
}

function initEditor(){
}
