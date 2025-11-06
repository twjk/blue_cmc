<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>翻译</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=request.getContextPath() %>/javascript/jquery-1.11.0.min.js"></script>

  </head>
  
  <body>
  <form id="form1">
	  <p>From:
	  	<select name="from">
	  		<option value="zh-cn" selected="selected">中文</option>
	  		<option value="zh-hk">粤语</option>
	  		<option value="en">英语</option>
	  		<option value="fr">法语</option>
	  		<option value="de">德语</option>
	  		<option value="ko">韩语</option>
	  		<option value="ja">日语</option>
	  		<option value="es">西班牙语</option>
	  		<option value="pt">葡萄牙语</option>
	  		<option value="it">意大利语</option>
	  		<option value="ar-eg">阿拉伯语</option>
	  		<option value="ru">俄罗斯语</option>
	  		<option value="th">泰语</option>
	  	</select>
	  </p>
      <p>To:
      	<select name="to">
	  		<option value="zh-cn">中文</option>
	  		<option value="zh-hk">粤语</option>
	  		<option value="en" selected="selected">英语</option>
	  		<option value="fr">法语</option>
	  		<option value="de">德语</option>
	  		<option value="ko">韩语</option>
	  		<option value="ja">日语</option>
	  		<option value="es">西班牙语</option>
	  		<option value="pt">葡萄牙语</option>
	  		<option value="it">意大利语</option>
	  		<option value="ar-eg">阿拉伯语</option>
	  		<option value="ru">俄罗斯语</option>
	  		<option value="th">泰语</option>
	  	</select>
	  </p>
      <p>
        <textarea name="q" id="src" cols="45" rows="5"></textarea>
      </p>
      <p>
        <input type="button" id="btn" onclick="trans();"  value="翻译">
      </p>
      <p>
        <textarea id="dst" cols="45" rows="5"></textarea>
      </p>
  </form>
  <script type="text/javascript">
  	function trans(){
  		$("#dst").text("");
		$.ajax({
			type:"post"
			,url:"services/trans!trans.do"
			,data:$('#form1').serialize()
			,dataType:"json"
			,beforeSend:function(XMLHttpRequest){}
			,success:function(json){
				if(json.respCode=="1000000" && json.trans_result.length>0){
					var list = json.trans_result;
					for(var i=0;i<list.length;i++){
						$("#dst").append(list[i].dst).append("\r\n");
					}
				}
			}
	});	
	}
  </script>
  </body>
</html>
