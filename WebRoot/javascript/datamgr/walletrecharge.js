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
	
	var url = "walletRechargeAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

//同步支付结果
function synPay(orderId){
	var url = "walletRechargeAction!doSynPay.do";
	var pars = "orderId=" + orderId;
	doOptCommon(url, pars);
}

//退款
function refund(orderId){
	if (confirm("确定要将订单["+orderId+"]的钱款退给客户吗？")) {
		var url = "walletRechargeAction!doRefund.do";
		var pars = "orderId=" + orderId;
		doOptCommon(url, pars);	
	}
}

function initEditor(){
}