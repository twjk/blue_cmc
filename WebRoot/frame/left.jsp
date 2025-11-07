<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
  	<base href="<%=basePath%>"></head>
    <title>用户功能树</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- Font Awesome 图标库 - 必须在left.jsp中也加载 -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<!-- 现代化样式 -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/modern.css?v=2.0">
<style type="text/css">
<!--
*{margin:0;padding:0;border:0;}
#nav {
 width:100%;  line-height:25px; 
 text-align:left; list-style:none; border:none;overflow:visible
    /*定义整个ul菜单的行高和背景色*/
}

/* 侧边栏收起时，只显示图标，隐藏文字 */
body.sidebar-collapsed #nav > li > a span {
    display: none !important;
}

body.sidebar-collapsed #nav li ul {
    display: none !important;
}

body.sidebar-collapsed #nav > li > a {
    justify-content: center !important;
    align-items: center !important;
    padding: 10px 8px !important;
    width: 100% !important;
    display: flex !important;
}

/* 侧边栏收起时，确保图标显示 */
body.sidebar-collapsed #nav > li > a i,
body.sidebar-collapsed #nav > li > a i[class*="fa-"],
body.sidebar-collapsed #nav > li > a > i {
    display: inline-flex !important;
    visibility: visible !important;
    opacity: 1 !important;
    margin-right: 0 !important;
    margin-left: 0 !important;
    padding: 0 !important;
    width: 20px !important;
    height: 20px !important;
    min-width: 20px !important;
    max-width: 20px !important;
    font-size: 18px !important;
    align-items: center !important;
    justify-content: center !important;
    flex-shrink: 0 !important;
    font-family: "Font Awesome 6 Free", "FontAwesome" !important;
    font-weight: 400 !important;
    color: #6B7280 !important;
    position: relative !important;
    z-index: 1 !important;
}

/* 侧边栏收起时，确保CMC文字显示 */
body.sidebar-collapsed #nav > li > a span[data-cmc="true"],
body.sidebar-collapsed #nav > li > a span:first-child:not(:last-child),
body.sidebar-collapsed #nav > li > a > span:first-child {
    display: inline-flex !important;
    visibility: visible !important;
    opacity: 1 !important;
    width: 40px !important;
    height: 20px !important;
    min-width: 40px !important;
    max-width: 40px !important;
    margin-right: 0 !important;
}

/* 确保图标的::before伪元素也显示 */
body.sidebar-collapsed #nav > li > a i::before,
body.sidebar-collapsed #nav > li > a i[class*="fa-"]::before,
body.sidebar-collapsed #nav > li > a > i::before {
    display: inline-block !important;
    visibility: visible !important;
    opacity: 1 !important;
    font-family: "Font Awesome 6 Free", "FontAwesome" !important;
    font-weight: 400 !important;
    font-style: normal !important;
}

body.sidebar-collapsed #PARENT {
    width: 100% !important;
}

/* 侧边栏展开时恢复 */
body:not(.sidebar-collapsed) #nav > li > a span {
    display: inline-block !important;
}

/* 只恢复展开的菜单显示，保持collapsed菜单隐藏 */
body:not(.sidebar-collapsed) #nav li ul.expanded {
    display: block !important;
}

body:not(.sidebar-collapsed) #nav li ul.collapsed {
    display: none !important;
}
/*==================一级目录===================*/
#nav a {
 width:100%; 
 display: flex !important;
 align-items: center !important;
 padding: 10px 16px !important;
 padding-left: 16px !important;
 gap: 12px !important;
 /*Width(一定要)，否则下面的Li会变形*/
}
#nav li { background-image:none; width:100%; /*一级目录的背景色*/
 float:left; font-size:13px;

 /*float：left,本不应该设置，但由于在Firefox不能正常显示
 继承Nav的width,限制宽度，li自动向下延伸*/
}
#nav a:link  {
 color:#111827; text-decoration:none;font-weight:500;width:100%; 
}
#nav a:visited  {
 color:#111827;text-decoration:none;font-weight:500;width:100%; 
}
#nav a:hover  {
 color:#3B82F6;text-decoration:none;font-weight:500;width:100%; 
}
/*==================二级目录===================*/
#nav li ul {
 list-style:none;
 text-align:left;width:100%; 
}
#nav li ul li{ 
 background: transparent;width:100%; /*二级目录的背景色*/
   border-bottom:none;  /*下面的一条白边*/
}
#nav li ul a{
         padding: 8px 16px 8px 48px !important; /* 与一级菜单文字对齐：16px(padding) + 20px(图标) + 12px(gap) = 48px */
         width:100%;
}
/*下面是二级目录的链接样式*/
#nav li ul a:link  {
 color:#6B7280; text-decoration:none; font-weight:400;
}
#nav li ul a:visited  {
 color:#6B7280;text-decoration:none; font-weight:400; 
}
#nav li ul a:hover {
 color:#3B82F6; font-weight:500;
 text-decoration:none; background-color:#F3F4F6;
 /* 二级onmouseover的字体颜色、背景色*/
}
/*==============================*/
#nav li:hover ul {
 left: auto;
}
#nav li.sfhover ul {
 left: auto;
}
#content {
 clear: left; 
}
#nav ul.collapsed {
 display: none;
}
-->
#PARENT{
 width:180px; margin:0 auto 0;
}
/* 美化滚动条样式 */
::-webkit-scrollbar {
    width: 8px;
}
::-webkit-scrollbar-track {
    background: #F9FAFB;
}
::-webkit-scrollbar-thumb {
    background: #D1D5DB;
    border-radius: 4px;
}
::-webkit-scrollbar-thumb:hover {
    background: #9CA3AF;
}
</style>
<body style="background-color:#F9FAFB;overflow-y:auto;overflow-x:hidden;height:100vh;">
<div style="height:10px"></div>
<div id="PARENT">
<ul id="nav">
<s:iterator value="#session.userbean.treeRights" id="right" status="st">
<li>
	<a href="javascript:void(0);"  onclick="DoMenu('ChildMenu${st.count }')" data-menu-name="${right.rightname }">
		<i class="fa-regular fa-circle" style="display: inline-flex !important; visibility: visible !important; opacity: 1 !important; width: 20px !important; height: 20px !important; min-width: 20px !important; max-width: 20px !important; margin-right: 0 !important; font-family: 'Font Awesome 6 Free' !important; font-weight: 400 !important; font-size: 18px !important; flex-shrink: 0 !important; align-items: center !important; justify-content: center !important;"></i>
		<span>${right.rightname }</span>
	</a>
	<ul id="ChildMenu${st.count }" class="collapsed">
		<s:iterator value="chdrights">
			<li><a href="<s:property value="url"/>" target="mainFrame" data-submenu-name="<s:property value="rightname"/>"><s:property value="rightname"/></a></li>
		</s:iterator>
	 </ul>
</li>
</s:iterator>
</ul>
<script type="text/javascript">
// 菜单图标映射 - Cloudflare风格（使用Font Awesome 6.4.0 Regular线条图标）
// 使用Font Awesome 6 Free版本中确定存在的Regular线条图标
var menuIconMap = {
	'BLUECMC': 'fa-regular fa-file', // 确定存在
	'同城管理': 'fa-regular fa-map', // 基础地图图标
	'对话管理': 'fa-regular fa-comments', // 确定存在（已验证）
	'众包任务管理': 'fa-regular fa-users', // 确定存在
	'订单管理': 'fa-regular fa-file', // 基础文件图标
	'用户管理': 'fa-regular fa-user', // 单数用户图标
	'系统管理': 'fa-regular fa-gear', // 确定存在
	'钱包管理': 'fa-regular fa-credit-card', // 信用卡图标
	'奖励管理': 'fa-regular fa-star', // 星星图标
	'红包管理': 'fa-regular fa-envelope', // 确定存在
	'翻译管理': 'fa-regular fa-globe', // 地球图标
	'译库管理': 'fa-regular fa-book', // 确定存在
	'主题管理': 'fa-regular fa-paintbrush', // 画笔图标
	'每日一句': 'fa-regular fa-calendar-days', // 确定存在
	'配置管理': 'fa-regular fa-gear', // 使用gear图标
	'日志管理': 'fa-regular fa-file-text', // 文件文本图标
	'分析统计': 'fa-regular fa-chart-bar' // 柱状图图标
};

// 为菜单项添加图标 - 增强版
function addMenuIcons() {
	// 一级菜单图标
	var menuLinks = document.querySelectorAll('#nav > li > a');
	
	menuLinks.forEach(function(link) {
		// 获取菜单名称
		var menuName = link.getAttribute('data-menu-name');
		if (!menuName) {
			// 如果没有data-menu-name，尝试从文本内容获取
			var span = link.querySelector('span');
			if (span) {
				menuName = span.textContent || span.innerText;
			} else {
				menuName = link.textContent || link.innerText;
			}
			menuName = menuName.trim();
		}
		
		// 特殊处理：翻译系统显示"CMC"文字
		if (menuName === 'BLUECMC') {
			// 查找所有可能的图标元素（包括所有i标签）
			var existingIcon = link.querySelector('i[class*="fa-"]');
			if (!existingIcon) {
				existingIcon = link.querySelector('i');
			}
			
			// 查找文字span（最后一个span通常是文字）
			var allSpans = link.querySelectorAll('span');
			var textSpan = null;
			if (allSpans.length > 0) {
				// 最后一个span通常是文字，但如果已经有CMC，则倒数第二个是文字
				for (var i = allSpans.length - 1; i >= 0; i--) {
					if (!allSpans[i].hasAttribute('data-cmc')) {
						textSpan = allSpans[i];
						break;
					}
				}
				if (!textSpan) {
					textSpan = allSpans[allSpans.length - 1];
				}
			}
			
			// 检查是否已有CMC文字
			var existingCmc = link.querySelector('span[data-cmc="true"]');
			if (existingCmc) {
				// 如果已存在，确保它在正确位置（图标位置或文字前面）
				if (existingIcon && existingCmc !== existingIcon.nextSibling) {
					// 如果图标还在，CMC应该在图标位置
					link.replaceChild(existingCmc, existingIcon);
				} else if (textSpan && existingCmc.nextSibling !== textSpan) {
					link.insertBefore(existingCmc, textSpan);
				}
				return; // 跳过处理
			}
			
			// 创建CMC文字span
			var cmcSpan = document.createElement('span');
			cmcSpan.textContent = 'CMC';
			cmcSpan.setAttribute('data-cmc', 'true');
			cmcSpan.style.cssText = 'display: inline-flex !important; visibility: visible !important; opacity: 1 !important; width: 40px !important; height: 20px !important; min-width: 40px !important; max-width: 40px !important; margin-right: 8px !important; font-size: 12px !important; font-weight: 600 !important; flex-shrink: 0 !important; align-items: center !important; justify-content: center !important; color: #3B82F6 !important; background-color: #E0F2FE !important; border-radius: 4px !important;';
			
			if (existingIcon) {
				// 如果有图标，替换图标
				link.replaceChild(cmcSpan, existingIcon);
			} else if (textSpan) {
				// 如果没有图标，在文字前面插入CMC
				link.insertBefore(cmcSpan, textSpan);
			} else {
				// 如果既没有图标也没有文字span，直接添加到链接开头
				link.insertBefore(cmcSpan, link.firstChild);
			}
			return; // 跳过图标处理
		}
		
		// 查找图标类 - 使用regular线条图标
		var iconClass = menuIconMap[menuName] || 'fa-regular fa-circle';
		
		// 如果regular图标不存在，fallback到solid版本
		// 注意：某些图标在regular中不存在，需要fallback
		var regularFallback = {
			'fa-map-location-dot': 'fa-solid fa-map-location-dot',
			'fa-user-group': 'fa-solid fa-user-group',
			'fa-file-invoice': 'fa-solid fa-file-invoice',
			'fa-wallet': 'fa-solid fa-wallet',
			'fa-gift': 'fa-solid fa-gift',
			'fa-language': 'fa-solid fa-language',
			'fa-palette': 'fa-solid fa-palette',
			'fa-sliders': 'fa-solid fa-sliders',
			'fa-file-lines': 'fa-solid fa-file-lines',
			'fa-chart-line': 'fa-solid fa-chart-line'
		};
		
		if (iconClass.indexOf('fa-regular') !== -1) {
			var iconName = iconClass.replace('fa-regular ', '');
			if (regularFallback[iconName]) {
				iconClass = regularFallback[iconName];
			}
		}
		
		// 检查是否已有图标 - 查找所有可能的图标元素
		var existingIcon = link.querySelector('i[class*="fa-"]');
		if (!existingIcon) {
			// 如果没找到，尝试查找任何i标签
			existingIcon = link.querySelector('i');
		}
		
		if (existingIcon) {
			// 更新现有图标 - 直接使用Font Awesome类名
			existingIcon.className = iconClass;
			// 强制设置样式，确保显示 - regular线条图标使用400字重，solid实心图标使用900字重
			var fontWeight = iconClass.indexOf('fa-regular') !== -1 ? '400' : '900';
			// 使用setAttribute和style双重设置，确保样式生效
			var styleStr = 'display: inline-flex !important; opacity: 1 !important; visibility: visible !important; width: 20px !important; height: 20px !important; min-width: 20px !important; max-width: 20px !important; margin-right: 0 !important; font-family: "Font Awesome 6 Free" !important; font-weight: ' + fontWeight + ' !important; font-size: 18px !important; flex-shrink: 0 !important; align-items: center !important; justify-content: center !important;';
			existingIcon.setAttribute('style', styleStr);
			existingIcon.style.cssText = styleStr;
			// 确保图标可见 - 多次设置确保生效
			existingIcon.style.display = 'inline-flex';
			existingIcon.style.visibility = 'visible';
			existingIcon.style.opacity = '1';
			existingIcon.style.width = '20px';
			existingIcon.style.height = '20px';
		} else {
			// 创建新图标 - 直接使用Font Awesome类名
			var newIcon = document.createElement('i');
			// 直接使用Font Awesome类名，不添加自定义类
			newIcon.className = iconClass;
			// 强制设置样式，确保显示 - regular线条图标使用400字重，solid实心图标使用900字重
			var fontWeight = iconClass.indexOf('fa-regular') !== -1 ? '400' : '900';
			// 使用setAttribute和style双重设置，确保样式生效
			var styleStr = 'display: inline-flex !important; opacity: 1 !important; visibility: visible !important; width: 20px !important; height: 20px !important; min-width: 20px !important; max-width: 20px !important; margin-right: 0 !important; font-family: "Font Awesome 6 Free" !important; font-weight: ' + fontWeight + ' !important; font-size: 18px !important; flex-shrink: 0 !important; align-items: center !important; justify-content: center !important;';
			newIcon.setAttribute('style', styleStr);
			newIcon.style.cssText = styleStr;
			
			// 找到文字节点
			var span = link.querySelector('span');
			if (span) {
				// 检查span前面是否已有图标
				var prevIcon = span.previousElementSibling;
				if (prevIcon && prevIcon.tagName === 'I') {
					// 如果已有图标，替换它
					link.replaceChild(newIcon, prevIcon);
				} else {
					// 否则在span前面插入
					link.insertBefore(newIcon, span);
				}
			} else {
				// 如果没有span，创建span包裹文字
				var textContent = link.textContent || link.innerText;
				// 清空链接内容（但保留图标）
				var existingIcons = link.querySelectorAll('i[class*="fa-"]');
				while (link.firstChild) {
					if (link.firstChild.tagName !== 'I') {
						link.removeChild(link.firstChild);
					} else {
						link.removeChild(link.firstChild);
					}
				}
				// 创建span
				var textSpan = document.createElement('span');
				textSpan.textContent = textContent.trim();
				link.appendChild(newIcon);
				link.appendChild(textSpan);
			}
			// 确保图标可见
			newIcon.style.display = 'inline-flex';
			newIcon.style.visibility = 'visible';
			newIcon.style.opacity = '1';
		}
	});
	
	// 再次检查图标是否真的存在
	var allIcons = document.querySelectorAll('#nav > li > a i');
	allIcons.forEach(function(icon) {
		// 图标检查完成
	});
}

// 等待Font Awesome加载完成
function waitForFontAwesome(callback, maxAttempts) {
	maxAttempts = maxAttempts || 50;
	var attempts = 0;
	
	function checkFontAwesome() {
		attempts++;
		// 检查Font Awesome CSS是否加载 - 在iframe中查找
		var faLink = document.querySelector('link[href*="font-awesome"]');
		if (!faLink) {
			// 也检查父窗口
			try {
				if (window.parent && window.parent !== window) {
					faLink = window.parent.document.querySelector('link[href*="font-awesome"]');
				}
			} catch(e) {
				// 跨域限制，忽略
			}
		}
		
		if (!faLink) {
			if (attempts < maxAttempts) {
				setTimeout(checkFontAwesome, 100);
			} else {
				console.warn('Font Awesome CSS链接未找到，但继续执行');
				if (callback) callback();
			}
			return;
		}
		
		// 检查Font Awesome是否真正加载（通过检查::before伪元素的内容）
		var testIcon = document.createElement('i');
		testIcon.className = 'fa-regular fa-house';
		testIcon.style.cssText = 'position: absolute; visibility: hidden; font-size: 20px;';
		document.body.appendChild(testIcon);
		
		// 等待一帧，让浏览器渲染
		requestAnimationFrame(function() {
			setTimeout(function() {
				var beforeStyle = window.getComputedStyle(testIcon, ':before');
				var beforeContent = beforeStyle.getPropertyValue('content');
				var fontFamily = beforeStyle.getPropertyValue('font-family');
				
				document.body.removeChild(testIcon);
				
				var isLoaded = beforeContent && beforeContent !== 'none' && beforeContent !== '""' && beforeContent !== '';
				
				if (isLoaded) {
					if (callback) callback();
				} else if (attempts < maxAttempts) {
					setTimeout(checkFontAwesome, 100);
				} else {
					console.warn('⚠️ Font Awesome加载超时，::before内容:', beforeContent, '继续执行');
					if (callback) callback();
				}
			}, 50);
		});
	}
	
	// 延迟开始检查，确保CSS已加载
	setTimeout(checkFontAwesome, 500);
}

// 页面加载后执行
function initMenuIcons() {
	waitForFontAwesome(function() {
		// 立即执行一次
		addMenuIcons();
		
		// 延迟执行多次，确保Font Awesome CSS规则已应用
		setTimeout(addMenuIcons, 200);
		setTimeout(addMenuIcons, 500);
		setTimeout(addMenuIcons, 1000);
		
		// 监听菜单展开事件
		setTimeout(function() {
			// 重写DoMenu函数以在菜单展开时添加图标
			var originalDoMenu = window.DoMenu;
			if (originalDoMenu) {
				window.DoMenu = function(emid) {
					originalDoMenu(emid);
					setTimeout(addMenuIcons, 100);
				};
			}
		}, 300);
	});
}

// 多种方式确保执行
if (document.readyState === 'loading') {
	document.addEventListener('DOMContentLoaded', initMenuIcons);
} else {
	initMenuIcons();
}

// 也监听window.onload
window.addEventListener('load', function() {
	setTimeout(addMenuIcons, 200);
});

// 确保在原有脚本执行后也添加图标
setTimeout(function() {
	addMenuIcons();
	// 监听所有菜单点击
	document.querySelectorAll('#nav > li > a').forEach(function(link) {
		link.addEventListener('click', function() {
			setTimeout(addMenuIcons, 100);
		});
	});
	// 多次执行确保图标显示
	setTimeout(addMenuIcons, 1000);
	setTimeout(addMenuIcons, 2000);
}, 500);

// 同步父窗口的侧边栏状态到iframe内部
function syncSidebarState() {
	try {
		var parentWindow = window.parent;
		if (parentWindow && parentWindow.document) {
			var ltd = parentWindow.document.getElementById('ltd');
			if (ltd) {
				var isCollapsed = ltd.classList.contains('sidebar-collapsed');
				if (isCollapsed) {
					document.body.classList.add('sidebar-collapsed');
				} else {
					document.body.classList.remove('sidebar-collapsed');
				}
			}
		}
	} catch(e) {
		// 跨域时忽略错误
		console.log('无法访问父窗口:', e);
	}
}

// 定期检查侧边栏状态
var lastCollapsedState = null;
setInterval(function() {
	syncSidebarState();
	// 如果状态改变，重新添加图标
	var currentState = document.body.classList.contains('sidebar-collapsed');
	if (lastCollapsedState !== currentState) {
		lastCollapsedState = currentState;
		if (typeof addMenuIcons === 'function') {
			setTimeout(addMenuIcons, 100);
			setTimeout(addMenuIcons, 300);
		}
	}
}, 100);

// 初始检查
syncSidebarState();

// 监听父窗口的点击事件（通过postMessage）
window.addEventListener('message', function(event) {
	if (event.data && event.data.type === 'sidebar-toggle') {
		syncSidebarState();
		// 侧边栏状态改变后，重新添加图标确保显示
		if (typeof addMenuIcons === 'function') {
			setTimeout(addMenuIcons, 100);
			setTimeout(addMenuIcons, 300);
		}
	}
});
</script>
</div>
</body>
<script type=text/javascript><!--
var LastLeftID = "";
function menuFix() {
 var obj = document.getElementById("nav").getElementsByTagName("li");
 
 for (var i=0; i<obj.length; i++) {
  obj[i].onmouseover=function() {
   this.className+=(this.className.length>0? " ": "") + "sfhover";
  }
  obj[i].onMouseDown=function() {
   this.className+=(this.className.length>0? " ": "") + "sfhover";
  }
  obj[i].onMouseUp=function() {
   this.className+=(this.className.length>0? " ": "") + "sfhover";
  }
  obj[i].onmouseout=function() {
   this.className=this.className.replace(new RegExp("( ?|^)sfhover\\b"), "");
  }
 }
}
function DoMenu(emid)
{
 var obj = document.getElementById(emid); 
 obj.className = (obj.className.toLowerCase() == "expanded"?"collapsed":"expanded");
 // 注释掉自动关闭上一个菜单的逻辑，允许多个菜单同时展开
 // if((LastLeftID!="")&&(emid!=LastLeftID)) //关闭上一个Menu
 // {
 //  document.getElementById(LastLeftID).className = "collapsed";
 // }
 LastLeftID = emid;
 // 菜单展开后添加图标
 if (typeof addMenuIcons === 'function') {
 	setTimeout(addMenuIcons, 50);
 }
}
function GetMenuID()
{
 var MenuID="";
 var _paramStr = new String(window.location.href);
 var _sharpPos = _paramStr.indexOf("#");
 
 if (_sharpPos >= 0 && _sharpPos < _paramStr.length - 1)
 {
  _paramStr = _paramStr.substring(_sharpPos + 1, _paramStr.length);
 }
 else
 {
  _paramStr = "";
 }
 
 if (_paramStr.length > 0)
 {
  var _paramArr = _paramStr.split("&");
  if (_paramArr.length>0)
  {
   var _paramKeyVal = _paramArr[0].split("=");
   if (_paramKeyVal.length>0)
   {
    MenuID = _paramKeyVal[1];
   }
  }
  /*
  if (_paramArr.length>0)
  {
   var _arr = new Array(_paramArr.length);
  }
  
  //取所有#后面的，菜单只需用到Menu
  //for (var i = 0; i < _paramArr.length; i++)
  {
   var _paramKeyVal = _paramArr[i].split('=');
   
   if (_paramKeyVal.length>0)
   {
    _arr[_paramKeyVal[0]] = _paramKeyVal[1];
   }  
  }
  */
 }
 
 if(MenuID!="")
 {
  DoMenu(MenuID)
 }else{
 //如果没有选中的menu，那么默认第一个打开
 var obj = document.getElementById("nav").getElementsByTagName("ul");
 DoMenu(obj[0].id)
 }
}
GetMenuID(); //*这两个function的顺序要注意一下，不然在Firefox里GetMenuID()不起效果
menuFix();

// 为二级菜单添加点击事件，保持选中状态
(function() {
	// 为所有二级菜单链接添加点击事件
	var subMenuLinks = document.querySelectorAll('#nav li ul a[target="mainFrame"]');
	subMenuLinks.forEach(function(link) {
		link.addEventListener('click', function(e) {
			// 移除所有二级菜单的active类
			var allSubMenus = document.querySelectorAll('#nav li ul a');
			allSubMenus.forEach(function(item) {
				item.classList.remove('active');
			});
			// 为当前点击的菜单项添加active类
			this.classList.add('active');
		});
	});
	
	// 根据URL高亮当前页面对应的二级菜单
	function highlightCurrentMenu() {
		try {
			var mainFrame = window.parent.document.getElementById('mainFrame');
			if (mainFrame && mainFrame.contentWindow) {
				var currentUrl = mainFrame.contentWindow.location.href;
				if (currentUrl && currentUrl !== 'about:blank') {
					// 移除所有active类
					var allSubMenus = document.querySelectorAll('#nav li ul a');
					allSubMenus.forEach(function(item) {
						item.classList.remove('active');
					});
					// 查找匹配的菜单项
					allSubMenus.forEach(function(item) {
						var itemUrl = item.getAttribute('href');
						if (itemUrl && currentUrl.indexOf(itemUrl) !== -1) {
							item.classList.add('active');
						}
					});
				}
			}
		} catch(e) {
			// 跨域限制，忽略
		}
	}
	
	// 监听iframe加载事件
	try {
		var mainFrame = window.parent.document.getElementById('mainFrame');
		if (mainFrame) {
			mainFrame.addEventListener('load', highlightCurrentMenu);
		}
	} catch(e) {
		// 跨域限制，忽略
	}
	
	// 延迟执行一次，确保iframe已加载
	setTimeout(highlightCurrentMenu, 1000);
	setTimeout(highlightCurrentMenu, 2000);
})();

// 确保菜单图标已添加 - 多次执行确保图标显示
if (typeof addMenuIcons === 'function') {
	setTimeout(addMenuIcons, 300);
	setTimeout(addMenuIcons, 800);
	setTimeout(addMenuIcons, 1500);
}
--></script> 
</html>
