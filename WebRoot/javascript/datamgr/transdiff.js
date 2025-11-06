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
	
	var url = "transDiffAction!doQuery.do";
	var pars = $("#qryForm").serialize() + "&pageSize=50" + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

//修正译文
function correctDst(diffId, toLib) {
	var dst = $("#dst_"+diffId).val();
	if(dst==''){
		alert('请输入译文');
		return;
	}
	
	var url = "transDiffAction!doCorrectDst.do";
	var params = "id=" + diffId + "&dst="+dst+"&toLib="+toLib;
	btnDisabled();
	$.ajax({type:'post',url:url, data:params, complete:function(result){
		if (result.responseText == 'SUCCESS') {
			$("#td_dst_"+diffId).html(dst);
			$("#td_status_"+diffId).html('已处理');
		} else {
			var reason = result.responseText;
			alert('操作失败！\n\n原因：'+reason);
		}
		btnEnabled();
	}});
}

