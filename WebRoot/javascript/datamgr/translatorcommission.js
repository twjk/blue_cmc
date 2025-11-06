function dataQuery() {
	var url = "translatorCommissionAction!doQuery.do";
	var pars = $("#qryForm").serialize() ;
	doQuery(url, pars);
}

function dataExport(){
	var url = "translatorCommissionAction!doExport.do";
	var pars = "start="+$("#start").val()
			 + "&end="+$("#end").val()
			 + "&operCd="+operCd
			 ;
	$.ajax({type:'post',url:url, data:pars, complete:function(result){
		
	}});
}

function dataDetail(operCd){
	var url = "translatorCommissionAction!doDetail.do";
	var pars = "start="+$("#start").val()
			 + "&end="+$("#end").val()
			 + "&operCd="+operCd
			 ;
	$.ajax({type:'post',url:url, data:pars, complete:function(result){
		$("#searchArea").hide();
		$("#resultArea").hide();
		$("#editorArea").show();
		$("#editorArea").html(result.responseText);
		listStyle();
	}});
}

function initEditor(){
}