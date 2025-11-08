/*
 * Description	: 	增删改查数据维护公用js
 */
function doAjax(url,params,fn){
	$.ajax({type: 'post', url: url, data: params, complete: fn});
}

function doQuery(url, params) {
	btnDisabled();
	$.ajax({type: 'post', url: url, data: params, complete: getList});
}

function getList(result) {
	$("#editorArea").hide();
	$("#resultArea").show();
	
	$("#resultArea").html(result.responseText);
	$('html, body').animate({scrollTop:0}, 'slow');
	
	listStyle();
	
	// 触发resultArea高度调整（如果页面有定义adjustResultAreaHeight函数）
	if (typeof adjustResultAreaHeight === 'function') {
		setTimeout(adjustResultAreaHeight, 100);
	}
	
	btnEnabled();
}

function listStyle(){
	var Ptr = $("tbody[id^=tab] > tr");
	for(var i=0;i<Ptr.length;i++) {
	    Ptr[i].onmouseover=function(){
	    	this.tmpClass=this.className;
	    	this.className = "t3";    
	    };
	    Ptr[i].onmouseout=function(){
	    	this.className=this.tmpClass;
	    };
	}
}

function doEdit(url, params) {
	btnDisabled();
	$("#resultArea").hide();
	$("#editorArea").show();
	$.ajax({type:'post', url: url, data: params, complete: function(result){
		$("#resultArea").hide();
		$("#editorArea").html(result.responseText);
		initEditor();
		btnEnabled();
	}});
}

function doDetail(url, params) {
	$("#resultArea").hide();
	$("#editorArea").show();
	$.ajax({type:'post', url: url, data: params, complete: function(result){
		$("#resultArea").hide();
		$("#editorArea").html(result.responseText);
		initDetail();
	}});
}

//公共操作。提示操作结果，如果成功则刷新列表，并保留分页信息
function doOptCommon(url,params,fn) {
	btnDisabled();
	$.ajax({type:'post',url:url, data:params, complete:function(result){
		if (result.responseText == 'SUCCESS') {
			alert('操作成功！');
			if(fn){
				eval(fn);
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

//成功不提示，便于快速操作
function doOptCommonQ(url,params,fn) {
	btnDisabled();
	$.ajax({type:'post',url:url, data:params, complete:function(result){
		if (result.responseText == 'SUCCESS') {
			if(fn){
				eval(fn);
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

function doOperate(url, params, fn){
	btnDisabled();
	$.ajax({type:'post',url:url, data:params, complete:function(result){
		alert(result.responseText);
		if(fn){
			eval(fn);
		}else{
			refreshList();
		}
		btnEnabled();
	}});
}

function doDelete(url, params) {
	if (confirm("确定要删除吗？")) {
		doOptCommon(url, params);
	}
}

function doUpdate(url, params) {
	doOptCommon(url, params);
}

function doSave(url,fn) {
	if(validator()){
		var pars = $('#editForm').serialize();
		doOptCommon(url,pars,fn);
	}
}

function doSaveNoValidator(url,fn) {
	var pars = $('#editForm').serialize();
	doOptCommon(url,pars,fn);
}

//从编辑页到列表页
function backList(){
	$("#searchArea").show();
	$("#resultArea").show();
	$("#editorArea").hide();
	$('html, body').animate({scrollTop:0}, 'slow');
}

//刷新列表
function refreshList(){
	var page = $("#nowpage").val();
	dataQuery(page);
}

//刷新父窗口并关闭子窗口
function closeAndRefresh(){
	window.opener.refreshList();
	window.close();
}

function refresh() {
	$("#btnQuery").click();
}

//重载页面
function reload(){
	document.location.reload();	
}

$(document).ready(function() {
	// 默认查询
	if($("#resultArea").is(":visible")){
		refresh();
	}
	$("#resultArea").after('<div style="display: none;"><input id="nowpage" value="1" /></div>');
});

function doUrl(url) {
	btnDisabled();
	var pars = $('form').serialize();
	$.ajax({type:'post',url:url, data:pars, complete:function(result){
		if (result.responseText == 'SUCCESS') {
			alert('操作成功！');
			document.location.reload();			
		} else {
			var reason = result.responseText;
			alert('操作失败！\n\n原因：'+reason);
			btnEnabled();
		}
	}});
}

//捕获回车事件
document.onkeydown=function(){   	
   /**
   if (event.keyCode==13){   	
	   if($("#resultArea").css("display")=="block")
	   		$("input[name='queryBtn']").click();     	    
	    else	
	    	$("input[name='saveBtn']").click();    	  
       return false;   
    } 
    */  
}