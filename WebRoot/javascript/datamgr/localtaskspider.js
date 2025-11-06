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
	
	var url = "localTaskSpiderAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function startDeal(id){
	var url = "localTaskSpiderAction!doStartDeal.do";
	var pars = "id=" + id;
	doOptCommonQ(url, pars);
}

function finishDeal(id){
	var url = "localTaskSpiderAction!doFinishDeal.do";
	var pars = "id=" + id;
	doOptCommonQ(url, pars);
}

//展示转文章Div
function showToArticle(id, title){
	$("#spdId").val(id);
	$("#spdTitle").text(id+"-"+title);
	showBgiframe("toArticlelDiv",0,300);
}

//隐藏转文章Div
function hideToArticle(){
	$("#toArticlelDiv").hide();
	$("#spdId").val("");
	$("#spdTitle").text("");
}

//取消
function saveToArticle(){
	var fullCatId = $("#toArticle_currCat").val();
	if(fullCatId==''){
		alert('请选择分类');
		return;
	}
	
	var url = "localTaskSpiderAction!doToArticle.do";
	var params = "id=" + $("#spdId").val() + "&fullCatId=" + fullCatId;
	doOptCommon(url, params);
}

function initEditor(){
	
}