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
	
	var url = "transVideoAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function detailData(id) {
	var url = "transVideoAction!doDetail.do";
	var pars = "";
	if(id){
		pars = "orderId=" + id;
	}
	doEdit(url, pars);
}

//保存留言
function saveMsg(){
	var url = "orderAction!doAddMsg.do";
	var orderId = $("#orderId").val();
	var params = "orderId="+orderId+"&msg="+$("#msg").val();
	
	doOptCommon(url, params, "detailData('"+orderId+"')");
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

function initEditor(){
}