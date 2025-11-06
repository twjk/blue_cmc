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
	
	var url = "transPicPickAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function editData(id) {
	var url = "transPicPickAction!doEdit.do?picId="+id;
	OpenWindowFull("图片翻译",url);
}

function rotatePic(){
	if (confirm("旋转后会刷新页面，如有已编辑的数据请先保存。确定要现在旋转图片么？")) {
		var picId = $("#picId").val();
		var url = "transPicPickAction!doRotatePic.do";
		var pars = "picId=" + picId + "&degree=" + $("#degree").val();
		
		doOptCommon(url, pars, "editData('"+picId+"')");
	}
}

function showCancel(){
	$("#cancelDiv").show();
}

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
	var url = "transPicPickAction!doCancel.do";
	var pars = "picId=" + $("#picId").val() + "&reason=" + reason;
	doOptCommon(url, pars, 'closeAndRefresh()');
}

function selectCancelReason(){
	$("#cancelReason").val($("#selCancelReason").val());
}

//开始翻译
function startTrans(){
	var url = "transPicPickAction!doStart.do";
	doSave(url,"editData('"+$("#picId").val()+"')");
}

//保存信息
function saveData() {
	if(other && !otherConfirm()){
		return;
	}
	//获取标注信息	
	$("#transResult").val(getTransInfo());
	var url = "transPicPickAction!doSave.do";
	doSave(url,"editData('"+$("#picId").val()+"')");
}

//修改他人操作的订单时，询问确认
function otherConfirm(){
	return confirm($("#opername").text()+"正在处理订单，确定要继续操作么？");
}

//完成处理
function completeTrans(){
	//获取标注信息	
	$("#transResult").val(getTransInfo());
	var url = "transPicPickAction!doCompleteTrans.do";	
	doSave(url, 'closeAndRefresh()');
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

//保存留言
function saveMsg(){
	var url = "transPicPickAction!doAddMsg.do";
	var picId = $("#picId").val();
	var params = "picId="+picId+"&msg="+$("#msg").val();
	
	doOptCommon(url, params, "editData('"+picId+"')");
}

function validator() {
	return true;
}

function initEditor(){
}