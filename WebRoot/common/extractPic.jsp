<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="qcmz" prefix="lt"%>
<html>
  <head>
	<title>编辑器</title>
	<script type="text/javascript" charset="utf-8" src="<lt:contextPath/>/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<lt:contextPath/>/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="<lt:contextPath/>/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/jquery-1.11.0.min.js"></script>
	<style type="text/css">
		body, html{margin: 0; padding: 0; border: 0; font-size: 12px; overflow-x:hidden;}
		#picUL{list-style: none;padding: 0px;margin: 0px;width: 100%;}
		#picUL li{width:140px;float: left; margin: 2px 5px 2px 5px;border:#E5E5E5 solid 1px;line-height: 20px;text-align: center;}
		#picUL img{height:100px;max-width:100px;cursor: pointer;}
	</style>
  </head>
  
  <body>
  	<div>
  		<script id="editor" type="text/plain"></script>
		<br/>
		<ul id="picUL"></ul>
		<p align="center" style="clear: both;">
			<input type="button" id="btnOK" value="提取图片" onclick="extractImg();" style="cursor: pointer;">
			&nbsp;
			<input type="button" id="btnOK" value="确定" onclick="ok();" style="cursor: pointer;">
			&nbsp;
			<input type="button" id="btnCancel" value="取消" onclick="window.close();" style="cursor: pointer;">
		</p>
	</div>
	<script type="text/javascript">
		var cfgCat = getQueryStringByName("cfgCat");
	
		//实例化编辑器
	    var ue = UE.getEditor('editor', {
	    	cfgCat: cfgCat,
	    	toolbars: [['simpleupload', 'insertimage', 'fullscreen', 'source']]
	    });
		
		function ok(){
			var arrImg=new Array()
			var i = 0;
			$("#picUL").find("img").each(function(){
				arrImg[i] = this.src;
				i++;
			});
			window.opener.extractPicCallback(arrImg);
			window.close();
		}
		
		function getQueryStringByName(name){
			var result = location.search.match(new RegExp("[\?\&]" + name+ "=([^\&]+)","i"));
			if(result == null || result.length < 1){
			    return "";
			}
			return decodeURI(result[1]);
		}
		
		function extractImg(){
			$("#picUL").empty();
			var html = ue.getContent();
			$(html).find("img").each(function(){
				$("#picUL").append("<li><img src='"+this.src+"'></li>");
			});
			$("#picUL").find("img").bind("click", function(){
				$(this).parent().remove();
			});
		}
		
	</script>
  </body>
</html>