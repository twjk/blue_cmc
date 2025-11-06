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
	
	var url = "transOrderDealAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function editData(id, orderType) {
	var url = "transOrderDealAction!doEdit.do";
	var pars = "orderId=" + id + "&orderType="+orderType;
	if(orderType==1){
		OpenWindowFull("图片翻译",url+"?"+pars);
	}else{
		doEdit(url, pars);
	}
}

//旋转图片
function rotatePic(){
	if (confirm("旋转后会刷新页面，如有已编辑的数据请先保存。确定要现在旋转图片么？")) {
		var orderId = $("#orderId").val();
		var orderType = $("#orderType").val();
		var url = "transOrderDealAction!doRotatePic.do";
		var pars = "orderId=" + orderId + "&degree=" + $("#degree").val();
		
		doOptCommon(url, pars, "editData('"+orderId+"' ,"+orderType+")");
	}
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

//开始翻译
function startTrans(){
	var orderId = $("#orderId").val();
	var orderType = $("#orderType").val();
	var url = "orderAction!doAccept.do";
	var params = "orderId="+orderId;
	doOptCommonQ(url, params, "editData('"+orderId+"', "+orderType+")");
}

//交单
function handover(){
	if(other && !otherConfirm()){
		return;
	}
	
	if(!confirm("确定已修正翻译语言，并要将订单交给其他译员处理么？")){
		return;
	}
	
	var orderId = $("#orderId").val();
	var orderType = $("#orderType").val();
	var url = "orderAction!doHandover.do";
	var params = "orderId="+orderId+ "&from="+$("#from").val() + "&to="+$("#to").val();
	doOptCommonQ(url, params, "editData('"+orderId+"', "+orderType+")");
}

//保存短文快译信息
function saveText() {
	if(other && !otherConfirm()){
		return;
	}
	var orderId = $("#orderId").val();
	var orderType = $("#orderType").val();
	var url = "transOrderDealAction!doSaveText.do";
	var params = "orderId="+orderId
			   + "&dst="+encodeURIComponent($("#dst").val())
			   + "&from="+$("#from").val()
			   + "&to="+$("#to").val()
			   ;
	if(isExistObject($("#needTime"))){
		params = params + "&needTime=" + $("#needTime").val();
	}
	doOptCommon(url, params, "editData('"+orderId+"', "+orderType+")");
}

//完成短文快译处理
function finishText(){
	if(!validatorText()){
		return;
	}
	
	if(other && !otherConfirm()){
		return;
	}

	var url = "transOrderDealAction!doFinishText.do";
	var params = "orderId="+$("#orderId").val()
	   		   + "&dst="+encodeURIComponent($("#dst").val())
	   		   + "&from="+$("#from").val()
	   		   + "&to="+$("#to").val()
	   		   + "&needTime="+$("#needTime").val()
	   		   ;

	doOptCommon(url, params);
}

function validatorText() {
	$.formValidator.initConfig({validatorgroup:"text",autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});   
    $("#dst").formValidator({validatorgroup:"text"}).inputValidator({min:1,max:4000,onerror:"请正确填写译文"});
	if(!jQuery.formValidator.pageIsValid('text')) return false;
	return true;
}

//保存图片翻译信息
function savePic() {
	if(other && !otherConfirm()){
		return;
	}
	
	var orderId = $("#orderId").val();
	var orderType = $("#orderType").val();
	var url = "transOrderDealAction!doSavePic.do";
	var params = "orderId="+orderId
	   		   + "&transResult="+encodeURIComponent(getTransInfo())
	   		   + "&from="+$("#from").val()
	   		   + "&to="+$("#to").val()
	   		   ;
	if(isExistObject($("#needTime"))){
		params = params + "&needTime=" + $("#needTime").val();
	}
	doOptCommon(url, params, "editData('"+orderId+"', "+orderType+")");
}

//完成图片翻译处理
function finishPic(){
	if(other && !otherConfirm()){
		return;
	}
	
	var orderId = $("#orderId").val();
	var url = "transOrderDealAction!doFinishPic.do";
	var params = "orderId="+orderId
	   		   + "&transResult="+encodeURIComponent(getTransInfo())
	   		   + "&from="+$("#from").val()
	   		   + "&to="+$("#to").val()
	   		   + "&needTime="+$("#needTime").val()
	   		   ;

	doOptCommon(url, params, 'closeAndRefresh()');
}

//获取标注信息
//TODO:后续扩展插件，在插件内部实现
function getTransInfo(){
	var picture = $( '#picture1');
	var divs = $(picture).find('.more32');
	var moreInfos = '';
	// each more infos on that picture
	$.each(divs, function( index, value ){
		if(index>0){
			moreInfos=moreInfos+'§;§';
		}
		descr=$(value).find('textarea').val();
		if(descr==undefined){
			descr="";
		}
		var topPosition=$(value).css('top');
		var leftPosition=$(value).css('left');
		
		//去px并取整
		topPosition = Math.round(topPosition.substr(0,topPosition.length-2)/zoom);
		leftPosition = Math.round(leftPosition.substr(0,leftPosition.length-2)/zoom);
		
		moreInfos = moreInfos+value.id+"§|§"+leftPosition+"§|§"+topPosition+"§|§"+descr;
	});
	return moreInfos;
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

//交换语言
function exchangeLang(){
	var from = $("#from").val();
	var to = $("#to").val();
	$("#from").val(to);
	$("#to").val(from);
}

function initEditor(){
}