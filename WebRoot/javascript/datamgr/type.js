function findChilds(selseq){
	var pcode = $("#type"+selseq).val();
	if(pcode==undefined){
		selseq = 0;
		pcode = "";
	}
	var operseq = selseq+1;
	for(var i=operseq;i<=3;i++){
		$("#type"+i).empty();
	}
	$("#type3desc").empty();
	$("#typedesc").text("");
	$("#type"+operseq).append("<option value=''>--请选择--</option>");
	
	var url = "typeAction!doFindChild.do";
	var params = "pcode="+pcode;
	$.getJSON(url, params, function(result){
		for(var i=0;i<result.length;i++){
			$("#type"+operseq).append("<option value='"+result[i].typecode+"'>"+result[i].typename+"</option>");
			if(operseq==3 && result[i].typedesc!=null){
				$("#type3desc").append("<option value='"+result[i].typecode+"'>"+result[i].typedesc+"</option>");
			}
		}
	});
}

function findRootTypes(selId){
	if($("#"+selId+" option").length>1) return;
	var url = "typeAction!doFindChild.do";
	var params = "pcode=";
	$.getJSON(url, params, function(result){
		for(var i=0;i<result.length;i++){
			$("#"+selId).append("<option value='"+result[i].typecode+"'>"+result[i].typename+"</option>");			
		}
	});
}

function showTypeDesc(){
	var typecode = $("#type3").val();
	$("#typedesc").text($("#type3desc option[value='"+typecode+"']").text());
}