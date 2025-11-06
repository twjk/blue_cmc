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
	
	var url = "themeSentenceAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function resetQuery(){
	resetCat("qry_");
}

//返回主题列表
function backTheme(){
	location.href = "themeAction!doInit.do?" + $("#themeQueryString").val();
}

function addData(){
	var url = "themeSentenceAction!doEdit.do";
	var pars = "themeid=" + $("#qry_themeid").val();
	doEdit(url, pars);
}

function editData(id) {
	var url = "themeSentenceAction!doEdit.do";
	var pars = "";
	if(id){
		pars = "id=" + id;
	}
	doEdit(url, pars);
}

function saveData() {
	var url = "themeSentenceAction!doSave.do";
	doSave(url);
}

function deleteData(id) {
	var url = "themeSentenceAction!doDelete.do";
	var pars = "id=" + id;
	doDelete(url, pars);
}


function validator() {
	initValidator();
	if(!jQuery.formValidator.pageIsValid('1')) return false;
	return true;
}

function validator4Batch() {
	initValidator();
	var sel = $(":checkbox[name='libid']:checked").length;
	if(sel>0){
		$("#libids").val(sel);
	}else{
		$("#libids").val('');
	}
	
	if(!jQuery.formValidator.pageIsValid('2')) return false;
	
	return true;
}

function initValidator(){
	//单条编辑校验
	$.formValidator.initConfig({autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});
	$("#themeid").formValidator().inputValidator({min:1,onerror:"主题不能为空"}); 
    $("#fromlang").formValidator().inputValidator({min:1,onerror:"请正确选择源语言"});
    $("#src").formValidator().inputValidator({min:1,max:500,onerror:"请正确填写原文"});
    $("#tolang").formValidator().inputValidator({min:1,onerror:"请正确选择目标语言"});
    $("#dst").formValidator().inputValidator({min:1,max:500,onerror:"请正确填写译文"});
    
    //批量导入校验
    $.formValidator.initConfig({validatorgroup:"2",autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});
    $("#cat1").formValidator({validatorgroup:"2"}).inputValidator({min:1,max:100,onerror:"请选择分类"});
    $("#libids").formValidator({validatorgroup:"2"}).inputValidator({min:1,onerror:"请搜索后选择语句"});
}

//批量导入
function addDataBatch(themeid){
	var url = "themeSentenceAction!doAddBatch.do";
	var pars = "themeid=" + $("#qry_themeid").val();
	doEdit(url, pars);
}

//批量导入搜索
function queryLib(){
	var tolang = $("#lib_to").val();
	var key = $("#lib_key").val();
	if(tolang=='' && key==''){
		alert('请选择目标语言或输入原文');
		return;
	}

	$("#libList").empty();

	var url = "themeSentenceAction!doQueryLib.do";
	var pars = "themeid=" + $("#qry_themeid").val()
		+ "&libtype=" + $("#lib_type").val()
		+ "&from=" + $("#lib_from").val()
		+ "&to=" + tolang
		+ "&key=" + key
	;
	$.ajax({type: 'post', url: url, data: pars, dataType: "json", complete: function(json){
		eval("var libs = " + json.responseText);
		var lib,obj;
		for(var i=0;i<libs.length;i++){
			obj = libs[i];
			lib = "<li>&nbsp;<input type='checkbox' name='libid' id='lib"+i+"' value='"+obj.libId+"'>"	+ obj.src +"（" + obj.catName+" | " + obj.fromName + "->" + obj.toName  + "）" +"</li>";
			$("#libList").append(lib);
		}
	}});
}

//智能批量选择
function checkBatch(){
	if(window.getSelection){	//chrome,firefox,opera
		var selectionObj = window.getSelection();
		if(selectionObj.rangeCount==0){return;}
		rangeObj = selectionObj.getRangeAt(0);
		var docFragment = rangeObj.cloneContents();
		var tempDiv = document.createElement("div");
		tempDiv.appendChild(docFragment);
		selectedHtml = tempDiv.innerHTML;
	}else if (document.getSelection) { //其他
		var range=window.getSelection().getRangeAt(0);
		var container = document.createElement('div');
		container.a(range.cloneContents());
		selectedHtml = container.innerHTML;
    }else if(document.selection){//IE特有的
		selectionObj = document.selection;
		if(selectionObj.rangeCount==0){return;}
		rangeObj = selectionObj.createRange();
		selectedHtml = rangeObj.htmlText;
	}
	$(selectedHtml).find(":checkbox").each(function(){
		$("#"+this.id).attr("checked","checked");
	});
}


//批量导入保存
function saveDataBatch(){
	var url = "themeSentenceAction!doSaveBatch.do";
	if(validator4Batch()){
		var params = $('#editForm').serialize();
		btnDisabled();
		$.ajax({type:'post',url:url, data:params, complete:function(result){
			if (result.responseText == 'SUCCESS') {
				var again = confirm("操作成功，是否继续添加？");
				if (again) {
					btnEnabled();
					resetCat();
					$("#lib_key").val("");
					$("#libList").empty();
				}else{
					refreshList();
				}
			} else {
				var reason = result.responseText;
				alert('操作失败！\n\n原因：'+reason);
			}
			btnEnabled();
		}});
	}
}

function initEditor(){
	initCat();
}
