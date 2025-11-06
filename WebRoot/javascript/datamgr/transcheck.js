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
	
	var url = "transOrderCheckAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function editData(id, orderType) {
	var url = "transOrderCheckAction!doEdit.do";
	var pars = "orderId=" + id + "&orderType="+orderType;
	doEdit(url, pars);
}

//保存留言
function saveMsg(){
	var orderId = $("#orderId").val();
	var orderType = $("#orderType").val();
	
	var url = "orderAction!doAddMsg.do";
	var params = "orderId="+orderId+"&msg="+$("#msg").val();
	
	doOptCommon(url, params, "editData('"+orderId+"', "+orderType+")");
}

//开始校对
function startCheck(){
	var orderId = $("#orderId").val();
	var orderType = $("#orderType").val();
	var url = "transOrderCheckAction!doStartCheck.do";
	var params = "orderId="+orderId;
	doOptCommonQ(url, params, "editData('"+orderId+"', "+orderType+")");
}

//保存图片翻译校对
function saveCheckPic() {
	if(other && !otherConfirm()){
		return;
	}
	
	var orderId = $("#orderId").val();
	var orderType = $("#orderType").val();
	
	//获取标注信息	
	$("#transResult").val(getTransInfo());
	var url = "transOrderCheckAction!doSaveCheckPic.do";
	doSave(url,"editData('"+orderId+"', "+orderType+")");
}

//完成图片翻译校对
function finishCheckPic() {
	if(other && !otherConfirm()){
		return;
	}
	
	//获取标注信息	
	$("#transResult").val(getTransInfo());
	var url = "transOrderCheckAction!doFinishCheckPic.do";
	doSave(url);
}

//旋转图片
function rotatePic(){
	if (confirm("旋转后会刷新页面，如有已编辑的数据请先保存。确定要现在旋转图片么？")) {
		var orderId = $("#orderId").val();
		var orderType = $("#orderType").val();
		var url = "transPicHumanAction!doRotatePic.do";
		var pars = "picId=" + orderId + "&degree=" + $("#degree").val();
		
		doOptCommon(url, params, "editData('"+orderId+"', "+orderType+")");
	}
}

//获取图片翻译标注信息
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

//保存文本翻译校对
function saveCheckText() {
	if(other && !otherConfirm()){
		return;
	}
	
	var orderId = $("#orderId").val();
	var orderType = $("#orderType").val();
	var url = "transOrderCheckAction!doSaveCheckText.do";
	var params = "orderId="+orderId
			   + "&transResult="+encodeURIComponent($("#dst").val())
			   + "&from="+$("#from").val()
	   		   + "&to="+$("#to").val()
			   ;
	
	doOptCommon(url, params, "editData('"+orderId+"', "+orderType+")");
}

//完成文本翻译校对
function finishCheckText() {
	if(other && !otherConfirm()){
		return;
	}
	
	var orderId = $("#orderId").val();
	var url = "transOrderCheckAction!doFinishCheckText.do";
	var params = "orderId="+orderId
			   + "&transResult="+encodeURIComponent($("#dst").val())
			   + "&from="+$("#from").val()
	   		   + "&to="+$("#to").val()
			   ;
	doOptCommon(url, params);
}

//修改他人操作的订单时，询问确认
function otherConfirm(){
	return confirm($("#checkname").text()+"正在处理订单，确定要继续操作么？");
}

//交换语言
function exchangeLang(){
	var from = $("#from").val();
	var to = $("#to").val();
	$("#from").val(to);
	$("#to").val(from);
}

function validator() {
	return true;
}

function initEditor(){
}