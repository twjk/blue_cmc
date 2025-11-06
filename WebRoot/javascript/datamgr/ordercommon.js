//订单详细
function detailData(id, orderType) {
	var url = "orderAction!doDetail.do";
	var pars = "orderId=" + id + "&orderType="+orderType;
	doEdit(url, pars);
}

//保存留言
function saveMsg(){
	var orderId = $("#orderId").val();
	var orderType = $("#orderType").val();
	var url = "orderAction!doAddMsg.do";
	var params = "orderId="+orderId+"&msg="+encodeURIComponent($("#msg").val());
	
	doOptCommon(url, params, "detailData('"+orderId+"', "+orderType+")");
}


//同步支付结果
function synPay(orderId){
	var url = "orderAction!doSynPay.do";
	var pars = "orderId=" + orderId;
	doOptCommon(url, pars);
}

//同步退款结果
function synRefund(orderId){
	var url = "orderAction!doSynRefund.do";
	var pars = "orderId=" +orderId;
	doOptCommon(url, pars);
}

//退款
function refund(orderId){
	if (confirm("确定要将订单["+orderId+"]的钱款退给客户吗？")) {
		var url = "orderAction!doRefund.do";
		var pars = "orderId=" + orderId;
		doOptCommon(url, pars);	
	}
}