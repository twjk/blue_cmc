/**
 * Description	: frame page JS code.
 * Create Date	: 2009-10-22
 */

function logout(){
	top.location.href='caslogout.jsp';
}

function modpwd(){
	showBgiframe("#modpwd",0,300); 	
}
function undomodpwd(){
	$("#modpwd").hide();
}
function domodpwd(){
	var regpwd=new RegExp("^\\w{1,12}$");
	var oldpwd=$("#oldpwd").val();
	var newpwd=$("#newpwd").val();
	var renewpwd=$("#renewpwd").val();
	if(!regpwd.test(oldpwd)){
		alert("密码的长度1~12，只允许数字、字母、下划线");
		$("#oldpwd").focus();
		return ;
	}
	if(!regpwd.test(newpwd)){
		alert("密码的长度1~12，只允许数字、字母、下划线");
		$("#newpwd").focus();
		return ;
	}
	if(!regpwd.test(renewpwd)){
		alert("密码的长度1~12，只允许数字、字母、下划线");
		$("#renewpwd").focus();
		return ;
	}
	if(newpwd!=renewpwd){
		alert("两次密码不一致");
		$("#newpwd").focus();
		return ;
	}
	var url="userInfoAction!doUpdatePWD.do";
	var pars="oldpwd=" + oldpwd + "&newpwd=" + newpwd;
	$.ajax({type: 'post', url: url, data: pars, complete: function(result){
		if (result.responseText == "SUCCESS") {
			alert('操作成功，请继续！');
			$("#modpwd").hide();
		} else {
			if(result.responseText!='null') alert(result.responseText);
			else alert('操作失败，请正确操作！');
		}		
	}});
	
}

//显示漂浮DIV
function showBgiframe(id,x,y){
	obj = $(id);
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

/*
 * 显示或隐藏左侧功能树帧
 */
function switchBar(){
	var img = $("#slink")
	var ltd = $("#ltd");
	if(img.attr("src") == contextPath+"/images/main/arrow_l.gif" ){
		ltd.hide();
		img.attr("src",contextPath+"/images/main/arrow_r.gif");
		img.attr("alt","展开");
	}
	else{
		ltd.show();
		img.attr("src",contextPath+"/images/main/arrow_l.gif");
		img.attr("alt","收起");
	}
}