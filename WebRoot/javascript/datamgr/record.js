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
	
	var url = "recordAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);		
}

function dataExport() {
	var url = "exportAction!doExportRecord.do";
	$("#qryForm").attr("action",url);
	$("#qryForm").submit();
}

var flag = "";
//保存
function addData(status) {
	flag = status;
	doSave("recordAction!doSave.do?status="+status,'window.close()');
}

//处理初始化
function processDataInit(id) {
	$("#resultArea").hide();//resultArea置为不可见
	$("#editorArea").show();//editorArea置为可见
	var url = "recordAction!doProcessInit.do";
	var pars = "id=" + id;
	doEdit(url, pars);
}

function doDetail(id) {
	$("#resultArea").hide();//resultArea置为不可见
	$("#editorArea").show();//editorArea置为可见
	var url = "recordAction!doDetail.do";
	var pars = "id=" + id;
	doEdit(url, pars);
}

//处理中
function processingData(status) {
	var pars = $('#editForm').serialize();
	var url = "recordAction!doProcessing.do?status="+status;
	doOptCommon(url,pars);
}

//完成
function processData() {
	if(validator_process()){
		doSave("recordAction!doProcess.do");
	}
}

function validator() {
	initValidator_add();
	if (!jQuery.formValidator.pageIsValid('1')) return false; 
	return true;
}

function initValidator_add(){
	$.formValidator.initConfig({autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});   
    $("#callno").formValidator()
    			.inputValidator({min:1,max:50,onerror:"请正确填写主叫号码"});	
    $("#calledno").formValidator()
    			.inputValidator({min:1,max:50,onerror:"请正确填写被叫号码"});	
    $("#contact").formValidator()
    			.inputValidator({min:1,max:50,onerror:"请正确填写客户姓名"});						  
    $("#mobile").formValidator()
    			.inputValidator({min:1,max:50,onerror:"请正确填写联系电话"});
    $("#type1").formValidator()
    			.inputValidator({min:1,onerror:"请选择业务类型"});
    $("#type2").formValidator()
    			.inputValidator({min:1,onerror:"请选择问题环节"});
    $("#type3").formValidator()
    			.inputValidator({min:1,onerror:"请选择问题分类"});
    $("#content").formValidator()
    			.inputValidator({min:1,max:500,onerror:"请正确填写问题内容"});	
    $("#preoper").formValidator()
    			.inputValidator({min:0,max:500,onerror:"请正确填写排查过程"});
    if(flag == "03") {
    	$("#operresult").formValidator()
    			.inputValidator({min:1,max:500,onerror:"请正确填写处理结果"});
    } else {
   	 	$("#operresult").formValidator()
   	 			.inputValidator({min:0,max:500,onerror:"请正确填写处理结果"});
    }
}

function validator_process() {
	initValidator_process();
	if (!jQuery.formValidator.pageIsValid('1')) return false; 
	return true;
}

function initValidator_process(){
	$.formValidator.initConfig({formid:"editForm",autotip:true,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});   
    $("#operresult").formValidator()
    						   .inputValidator({min:1,max:500,onerror:"请正确填写处理结果"});	
}

function initEditor(){

}
