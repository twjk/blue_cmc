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
	
	var url = "transTextHumanAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function editData(id) {
	var url = "transTextHumanAction!doEdit.do";
	var pars = "";
	if(id){
		pars = "orderId=" + id;
	}
	doEdit(url, pars);
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
	var orderId = $("#orderId").val();
	var url = "transTextHumanAction!doCancel.do";
	var params = "orderId=" + orderId + "&reason=" + reason;
	doOptCommon(url, params);
}

function selectCancelReason(){
	$("#cancelReason").val($("#selCancelReason").val());
}

//开始翻译
function startTrans(){
	var orderId = $("#orderId").val();
	var url = "transTextHumanAction!doStart.do";
	var params = "orderId="+orderId;
	doOptCommonQ(url, params, "editData('"+orderId+"')");
}

//保存信息
function saveData() {
	if(other && !otherConfirm()){
		return;
	}
	var orderId = $("#orderId").val();
	var url = "transTextHumanAction!doSave.do";
	var params = "orderId="+orderId+"&dst="+encodeURIComponent($("#dst").val());

	doOptCommon(url, params, "editData('"+orderId+"')");
}

//修改他人操作的订单时，询问确认
function otherConfirm(){
	return confirm($("#opername").text()+"正在处理订单，确定要继续操作么？");
}

//完成处理
function finish(){
	if(other && !otherConfirm()){
		return;
	}
	
	var orderId = $("#orderId").val();
	var url = "transTextHumanAction!doFinish.do";
	var params = "orderId="+orderId+"&dst="+encodeURIComponent($("#dst").val());

	doOptCommon(url, params);
}


//保存留言
function saveMsg(){
	var orderId = $("#orderId").val();
	var url = "orderAction!doAddMsg.do";
	var params = "orderId="+orderId+"&msg="+encodeURIComponent($("#msg").val());
	
	doOptCommon(url, params, "editData('"+orderId+"')");
}

var audio = new Audio();//定义一个播放器
audio.onended = function() {
	var imgPlayPath = contextPath+"/images/play.png";
	$("#playVoice").attr('src',imgPlayPath);
};
function playSound(voice){
	var imgPlayPath = contextPath+"/images/play.png";
	var imgStopPath = contextPath+"/images/stop.png";
	if(audio!==null){
		if(audio.paused){
			audio.src = voice;
			audio.play();
			$('#playVoice').attr('src',imgStopPath);  
		}else{
			$('#playVoice').attr('src',imgPlayPath);  
			audio.pause();			
		}
	}
}

function initEditor(){
}