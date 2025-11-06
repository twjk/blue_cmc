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
	
	var url = "rewardAccountAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function updateStatus(id, status){
	var url = "rewardAccountAction!doUpdateStatus.do";
	var pars = "userId=" + id + "&status="+status;
	doOptCommonQ(url, pars);
}


//可用余额
function sortBalance(){
	if($("#sortBalance").attr("class")=="sortby"){
		$("#sortBalance").attr("class","unsortby");
		$("#orderby").val("");
	}else{
		$("#sortBalance").attr("class","sortby");
		$("#orderby").val("balance desc");
	}
	$("#sortTotal").attr("class","unsortby");
	dataQuery();
}

//累计奖励
function sortTotal(){
	if($("#sortTotal").attr("class")=="sortby"){
		$("#sortTotal").attr("class","unsortby");
		$("#orderby").val("");
	}else{
		$("#sortTotal").attr("class","sortby");
		$("#orderby").val("rewardtotal desc");
	}
	$("#sortBalance").attr("class","unsortby");
	dataQuery();
}


function detailData(id) {
	var url = "rewardAccountAction!doDetail.do";
	var pars = "userId=" + id;
	doDetail(url, pars);
	$("#searchArea").hide();
}

function queryIncome(){
	$("#incexp").val(1);
	queryBill();
}

function queryExpenses(){
	$("#incexp").val(2);
	queryBill();
}

function queryBill(page){
	//对跳转的页码判断
	if (page != null) {
		if(isNaN(page)){
			alert("输入的页码不正确！");
			return;
		}
		$("#nowpage").val(page);
	}else
		$("#nowpage").val(1);
	
	var incexp = $("#incexp").val();
	
	var url = "rewardAccountAction!doQueryBill.do";
	var pars = "userid="+$("#userId").val() + "&incexp="+incexp+"&pageSize=20" + (page != null ? "&page=" + page : "");
	doAjax(url, pars, function(result){
		if(incexp==1){
			$("#incomeList").html(result.responseText);
		}else{
			$("#expensesList").html(result.responseText);
		}
		listStyle();
	});
}

//手工发放
function addOffline(){
	var url = "rewardAccountAction!doEditOffline.do";
	var pars = "";	
	doEdit(url, pars);
}

//保存手工发放
function saveOffline() {
	if(validator()){
		var url = "rewardAccountAction!doSaveOffline.do";
		var params = $('#editForm').serialize();
		btnDisabled();
		$.ajax({type:'post',url:url, data:params, complete:function(result){
			if ($.isNumeric(result.responseText)) {
				alert('操作成功！');
				detailData(result.responseText)
			} else {
				var reason = result.responseText;
				alert('操作失败！\n\n原因：'+reason);
			}
			btnEnabled();
		}});
	}
}

function validator() {
	initValidator();
	if(!jQuery.formValidator.pageIsValid('1')) return false;
	return true;
}

function initValidator(){
	$.formValidator.initConfig({autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});
	$("#amount").formValidator().regexValidator({regexp:"num1",datatype:"enum",onerror:"请正确填写金额"});
    $("#userId").formValidator({empty:true}).regexValidator({regexp:"intege1",datatype:"enum",onerror:"请正确填写用户编号"});
    $("#mobile").formValidator().functionValidator({fun:checkUser});
}

function checkUser(){
	var userId = $("#userId").val();
	var mobile = $("#mobile").val();
	if((!userId || userId=='') && (!mobile || mobile=='')){
		return "用户编号和手机号码不能同时为空";
	}
}


function initDetail(){
	queryIncome();
}

function initEditor(){
}