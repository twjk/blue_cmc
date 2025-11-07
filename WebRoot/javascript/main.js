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
 * 显示或隐藏左侧功能树帧 - Cloudflare风格平滑动画
 * 收起时只显示图标，展开时显示图标+文字
 */
function switchBar(){
	console.log('switchBar called');
	var btn = $("#slink");
	var ltd = $("#ltd");
	
	if(!ltd || ltd.length === 0) {
		console.error('找不到ltd元素');
		ltd = document.getElementById("ltd");
	}
	
	if(!ltd) {
		console.error('无法找到侧边栏元素');
		return;
	}
	
	var isCollapsed = false;
	if(typeof jQuery !== 'undefined' && ltd.jquery) {
		isCollapsed = ltd.hasClass("sidebar-collapsed");
	} else {
		isCollapsed = ltd.classList.contains("sidebar-collapsed");
	}
	
	console.log('当前状态 - isCollapsed:', isCollapsed);
	
	var leftFrame = document.getElementById("leftFrame");
	
	if(isCollapsed){
		// 展开侧边栏
		console.log('展开侧边栏');
		if(typeof jQuery !== 'undefined' && ltd.jquery) {
			ltd.removeClass("sidebar-collapsed");
		} else {
			ltd.classList.remove("sidebar-collapsed");
		}
		if(btn && btn.length > 0) {
			btn.removeClass("collapsed");
			btn.find("i").removeClass("fa-angles-right").addClass("fa-angles-left");
			btn.attr("alt","收起");
			btn.attr("title","收起侧边栏");
		} else {
			var btnEl = document.getElementById("slink");
			if(btnEl) {
				btnEl.classList.remove("collapsed");
				var icon = btnEl.querySelector("i");
				if(icon) {
					icon.classList.remove("fa-angles-right");
					icon.classList.add("fa-angles-left");
				}
				btnEl.setAttribute("alt","收起");
				btnEl.setAttribute("title","收起侧边栏");
			}
		}
		// 保存状态到 localStorage
		localStorage.setItem("sidebarCollapsed", "false");
	}
	else{
		// 收起侧边栏
		console.log('收起侧边栏');
		if(typeof jQuery !== 'undefined' && ltd.jquery) {
			ltd.addClass("sidebar-collapsed");
		} else {
			ltd.classList.add("sidebar-collapsed");
		}
		if(btn && btn.length > 0) {
			btn.addClass("collapsed");
			btn.find("i").removeClass("fa-angles-left").addClass("fa-angles-right");
			btn.attr("alt","展开");
			btn.attr("title","展开侧边栏");
		} else {
			var btnEl = document.getElementById("slink");
			if(btnEl) {
				btnEl.classList.add("collapsed");
				var icon = btnEl.querySelector("i");
				if(icon) {
					icon.classList.remove("fa-angles-left");
					icon.classList.add("fa-angles-right");
				}
				btnEl.setAttribute("alt","展开");
				btnEl.setAttribute("title","展开侧边栏");
			}
		}
		// 保存状态到 localStorage
		localStorage.setItem("sidebarCollapsed", "true");
	}
	
	// 通知iframe内部更新状态
	if(leftFrame && leftFrame.contentWindow) {
		try {
			leftFrame.contentWindow.postMessage({type: 'sidebar-toggle', collapsed: !isCollapsed}, '*');
		} catch(e) {
			console.log('无法通知iframe:', e);
		}
	}
}

// 页面加载时恢复侧边栏状态
$(document).ready(function(){
	var isCollapsed = localStorage.getItem("sidebarCollapsed") === "true";
	var btn = $("#slink");
	var ltd = $("#ltd");
	var leftFrame = document.getElementById("leftFrame");
	
	if(isCollapsed){
		ltd.addClass("sidebar-collapsed");
		btn.addClass("collapsed");
		if(btn.find("i").length > 0){
			btn.find("i").removeClass("fa-angles-left").addClass("fa-angles-right");
		}
		btn.attr("alt","展开");
		btn.attr("title","展开侧边栏");
		
		// 通知iframe内部更新状态
		setTimeout(function(){
			if(leftFrame && leftFrame.contentWindow) {
				try {
					leftFrame.contentWindow.postMessage({type: 'sidebar-toggle', collapsed: true}, '*');
				} catch(e) {
					console.log('无法通知iframe:', e);
				}
			}
		}, 500);
	}
});