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
	
	var url = "themeAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

//跳转到明细
function queryDetailData(id){
	location.href = "themeSentenceAction!doInit.do?themeid="+id
		+ "&page=" + $("#nowpage").val()
		+ "&" + $("#qryForm").serialize()
	;
}

function editData(id) {
	var url = "themeAction!doEdit.do";
	var pars = "";
	if(id){
		pars = "id=" + id;
	}
	doEdit(url, pars);
}


function saveData() {
	$("#pic").val($("#selPic").attr("src"));
	$("#bgpic").val($("#selBgpic").attr("src"));
	var url = "themeAction!doSave.do";
	doSave(url);
}

function deleteData(id) {
	var url = "themeAction!doDelete.do";
	var pars = "id=" + id;
	doDelete(url, pars);
}


function validator() {
	initValidator();
	if(!jQuery.formValidator.pageIsValid('1')) return false;
	return true;
}

function initValidator(){
	$.formValidator.initConfig({autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});   
    $("#titlecn").formValidator().inputValidator({min:1,max:20,onerror:"请正确填写中文名称"});
    $("#titlelc").formValidator().inputValidator({min:0,max:50,onerror:"请正确填写当地名称"});
    $("#themecode").formValidator().inputValidator({min:1,max:10,onerror:"请正确填写主题代码"});
    $("#pic").formValidator().inputValidator({min:0,max:200,onerror:"请正确填写图片"});
    $("#bgpic").formValidator().inputValidator({min:0,max:200,onerror:"请正确填写背景图片"});
    $("#downloadurl").formValidator().inputValidator({min:0,max:200,onerror:"请正确填写离线包地址"});
    $("#filesize").formValidator().inputValidator({min:0,max:200,onerror:"请正确填写离线包大小"});
    $("#sortindex").formValidator({empty:false}).regexValidator({regexp:"intege",datatype:"enum",onerror:"请正确填写排序"});
}

function initEditor(){
}
