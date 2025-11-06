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
	
	var url = "dubOrderDealAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function editData(id, orderType) {
	var url = "dubOrderDealAction!doEdit.do";
	var pars = "orderId=" + id + "&orderType="+orderType;
	doEdit(url, pars);
}

//展示取消浮沉
function showCancel(){
	$("#cancelDiv").show();
}

//隐藏取消浮沉
function hideCancel(){
	$("#cancelDiv").hide();
}

//取消
function saveCancel(){
	var reason = $("#cancelReason").val(); 
	if(reason.length<2){
		alert('请填写取消原因');
		return;
	}
	var orderId = $("#orderId").val();
	var orderType = $("#orderType").val();
	var url = "orderAction!doCancel.do";
	var params = "orderId=" + orderId + "&reason=" + reason;
	if(orderType==1){
		doOptCommon(url, params, 'closeAndRefresh()');
	}else{
		doOptCommon(url, params);
	}
}

function selectCancelReason(){
	$("#cancelReason").val($("#selCancelReason").val());
}

//开始处理
function startDub(){
	var orderId = $("#orderId").val();
	var orderType = $("#orderType").val();
	var url = "orderAction!doAccept.do";
	var params = "orderId="+orderId;
	doOptCommonQ(url, params, "editData('"+orderId+"', "+orderType+")");
}

//完成处理
function finishOrder(){
	if(other && !otherConfirm()){
		return;
	}

	var url = "orderAction!doFinish.do";
	var params = "orderId="+$("#orderId").val();

	doOptCommon(url, params);
}

//修改他人操作的订单时，询问确认
function otherConfirm(){
	return confirm("本单由【"+$("#opername").text()+"】处理，确定要继续操作么？");
}

//保存留言
function saveMsg(){
	var orderId = $("#orderId").val();
	var orderType = $("#orderType").val();
	var url = "orderAction!doAddMsg.do";
	var params = "orderId="+orderId+"&msg="+encodeURIComponent($("#msg").val());
	
	doOptCommon(url, params, "editData('"+orderId+"', "+orderType+")");
}

function initEditor(){
}