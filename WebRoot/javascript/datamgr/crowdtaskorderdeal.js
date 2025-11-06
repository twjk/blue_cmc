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
	
	var url = "crowdTaskOrderDealAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function editData(id) {
	var url = "crowdTaskOrderDealAction!doEdit.do";
	var pars = "orderId=" + id;
	doEdit(url, pars);
}

//创建任务
function addTask(){
	$("#editForm").show();
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

//取消创建任务
function cancelAddTask(){
	$("#editForm").hide();
}

//保存
function saveTask() {
	var url = "crowdTaskAction!doSave.do";
	doSave(url, 'reloadEditData()');
}

function reloadEditData(){
	editData($("#orderId").val());
}

function validator() {
	initValidator();
	if(!jQuery.formValidator.pageIsValid('1')) return false;
	return true;
}

function initValidator(){
	$.formValidator.initConfig({autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});   
    $("#taskTitle").formValidator().inputValidator({min:1,max:50,onerror:"请正确填写任务名称"});
    $("#peopleNum").formValidator({empty:false}).regexValidator({regexp:"intege",datatype:"enum",onerror:"请正确填写人数"});
    $("#subjectReward").formValidator({empty:false}).regexValidator({regexp:"num1",datatype:"enum",onerror:"请正确填写题目奖励金额"});
    $("#subjectContent").formValidator().inputValidator({min:1,max:200,onerror:"请正确填写题目"});
    $("#endTime").formValidator().inputValidator({min:1,max:100,onerror:"请正确填写结束时间"});
    $("#description").formValidator().inputValidator({min:1,max:1000,onerror:"请正确填写任务描述"});
    $("#sortIndex").formValidator({empty:false}).regexValidator({regexp:"intege",datatype:"enum",onerror:"请正确填写排序"});
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
function acceptOrder(){
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