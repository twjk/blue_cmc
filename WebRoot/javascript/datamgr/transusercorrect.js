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
	
	var url = "transUserCorrectAction!doQuery.do";
	var pars = $("#qryForm").serialize() + "&pageSize=50" + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

//修正译文
function correctDst(correctid, toLib) {
	var dst = $("#dst_"+correctid).val();
	if(dst==''){
		alert('请输入译文');
		return;
	}
	
	var url = "transUserCorrectAction!doCorrectDst.do";
	var params = "id=" + correctid + "&dst="+dst+"&toLib="+toLib;
	btnDisabled();
	$.ajax({type:'post',url:url, data:params, complete:function(result){
		if (result.responseText == 'SUCCESS') {
			$("#td_dst_"+correctid).html(dst);
			$("#td_status_"+correctid).html('已处理');
			$("#td_oper_"+correctid).html('');
		} else {
			var reason = result.responseText;
			alert('操作失败！\n\n原因：'+reason);
		}
		btnEnabled();
	}});
}

//修正译文
function deleteData(id) {
	var url = "transUserCorrectAction!doDelete.do";
	var params = "id=" + id;
	btnDisabled();
	$.ajax({type:'post',url:url, data:params, complete:function(result){
		if (result.responseText == 'SUCCESS') {
			$("#tr_"+id).hide();
		} else {
			var reason = result.responseText;
			alert('操作失败！\n\n原因：'+reason);
		}
		btnEnabled();
	}});
}

