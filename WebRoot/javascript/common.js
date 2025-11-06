/**
 * 打开窗口并显示
 * @param name 窗口名称
 * @param url  包含参数
 * @param width  窗口宽度
 * @param height 窗口高度 
 * @return
 */
function OpenWindow(name,url,width,height){
	if(width==undefined){
		width = screen.availWidth*3/5
	}
	if(height==undefined){
		height = screen.availHeight*3/4
	}
	winX = (parseInt(screen.availWidth)-parseInt(width))/2;
    winY = (parseInt(screen.availHeight)-parseInt(height))/2;
    mw = window.open(url, name, 'fullscreen=0,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1');
    mw.blur();
    mw.focus();
    mw.resizeTo(width, height);
    mw.moveTo(winX, winY);

    return 1;
}

function OpenWindowFull(name,url){
    mw = window.open(url, name, 'top=0,left=0,fullscreen=1,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=10,resizable=1');
    mw.blur();
    mw.focus();
    mw.resizeTo(window.screen.availWidth, window.screen.availHeight);
    return 1;
}

/**
 * 打开窗口并显示
 * @param name 窗口名称
 * @param url  包含参数
 * @param width  窗口宽度
 * @param height 窗口高度 
 * @return
 */
function OpenWindowRight(name, url, width, height){
	if(width==undefined){
		width = screen.availWidth*3/5
	}
	if(height==undefined || height==''){
		height = screen.availHeight*3/4
	}
	winX = parseInt(screen.availWidth)-parseInt(width)-20;	//+50防止贴到右侧
    winY = parseInt(screen.availHeight)-parseInt(height)-20;
    mw = window.open(url, name, 'width='+width+',height='+height+',left='+winX+',top='+winY+',fullscreen=0,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1');
//    mw.blur();
//    mw.focus();
//    mw.resizeTo(width, height);
//    mw.moveTo(winX, winY);

    return 1;
}

//显示漂浮div
function showBgiframe(id,x,y){
	obj = $("#"+id);
	var rolltop=document.documentElement.scrollTop;
	var v_left =x+($(document).width())/2-(parseInt(obj.width(),10)/2);
	var v_top= (y-(parseInt(obj.height(),10)/2)+rolltop/2);
    obj.css("left",v_left+"px")
    		.css("top",v_top+"px")
    		.css('position','absolute')
    		.css("z-index", "0");
    if ($.fn.bgiframe){
    	obj.bgiframe({height:obj.get(0).scrollHeight}); 
    }
    obj.show();
}

/**
 * 关闭窗口
 */
function closeWindow(){
	window.close();	
}


function browseUrlById(id){
	browseUrl($("#"+id).val());
}

function browseUrl(url){
	if(url!=null && url!=""){
		OpenWindow('',url);
	}
}

function confirmReload(str){   
    execScript("n   =   (msgbox('"+   str   +"',vbYesNo,   '')=vbYes)",   "vbscript");   
    return(n);   
}   

function moveOption(e1, e2){  
	var $options = $(e1 +' option:selected');//获取当前选中的项
	var $remove = $options.remove();//删除下拉列表中选中的项      
	$remove.appendTo(e2);//追加给对方
}

function moveAllOption(e1, e2){
	var $options = $(e1+' option');      
	$options.appendTo(e2); 
}

function addOption(id,text,value){
	$("#"+id)[0].options.add(new Option(text,value));
}

/*
 * 返回单选框选中的值
 * obj: 单选框对象集合,如:document.getElementsByName("payType")
 * return 如果单选框没有选择，返回''; 否则返回单选框的值
 */
function getRadioValue(obj){
	for(var i=0; i<obj.length; i++){
		if(obj[i].checked && !obj[i].disabled){
			return obj[i].value;
		}
	}
	return "";
}

//获得单选框的值
function getRadValue(name){
	return $(":radio[name='"+name+"']:checked").val();
}

//单选框赋值
function setRadValue(name,value){
	$(":radio[name='"+name+"']").each(function(){
		if(this.value==value){this.checked = true;}
		else{this.checked = false;}
	});
}
//获得复选框的值
function getChkValue(name){
	var result = "";
	$(":checked[name='"+name+"']").each(function(){
		result += "," + this.value;
	});
	if(result.length>0) result = result.substr(1);
	return result;
}

//获得列表框的值
function getSelectValues(id){
	var result = "";
	$("#"+id+" option").each(function(){
		result += "," + $(this).val(); 
	});
	if(result.length>0) result = result.substr(1);
	return result;
}

//获得列表框的文本
function getSelectedText(id){
	return $('#'+id).find('option:selected').text()	
}

//获得列表框的长度
function getSelectLength(id){
	return $("#"+id).find("option").length;
}

//是否有复选框选中
function isCheckboxChecked(name){
	var bResult = false;
	$(":checkbox[name='"+name+"']").each(function(){
		if(this.checked){bResult = true;return;}
	});
	return bResult;
}

//替换所有
function replaceAll(str,text,repText){
	return str.replace(new RegExp(text,"gm"),repText);
}

//是否包含文本
function hasText(obj, text){
	$(obj).each(function(){
		if($(this).text()==text){
			return true;
		}
	});
	return false;
}

//动态交替色
function overtr(obj){
 	$(obj).children().css("background","#F6F6F6");
}
function outtr(obj){
 	$(obj).children().css("background","#ffffff");
}

function btnDisabled(){
	$(":button").attr("disabled",true);
	document.body.style.overflow = 'hidden'; 
 	document.body.style.height = document.documentElement.clientHeight + 'px';
 	document.getElementById('coverLayer').style.display = 'block'; 
 	document.getElementById('coverLayer').style.height = '100%';
}

function btnEnabled(){
	document.body.style.overflow = ''; 
 	document.getElementById('coverLayer').style.display = 'none'; 
	$(":button").removeAttr("disabled");
}

//计算图片等比例的宽和高
function calScale(oriWidth, oriHeight, maxWidth, maxHeight){
	var result = new Array();
	var width = 0;
	var height = 0;
	if(oriWidth<=maxWidth){
    	width = oriWidth;
    	height = oriHeight;
    }else{
    	width = maxWidth;
    	height = width*oriHeight/oriWidth;
    }
    
    if(height>maxHeight){
    	height = maxHeight;
    	width = oriWidth*height/oriHeight;
    }
	result[0] = Math.round(width);
	result[1] = Math.round(height);
	return result;
}

function isExistFunction(funcName){
	try {
		return $.isFunction(eval(funcName)); 
    } catch(e) {}
    return false;
}

function isExistObject(obj){
    return $(obj).length>0;
}