//分拣译库
function doSorting(){
	if (confirm("需要较长时间，确定要重新分拣译库吗？")) {
		var themeId = "";
		if($("#spanToTheme").is(":visible")){
			themeId = $("#themeId").val();
		}
		var url="transLibAction!doSorting.do";
		var pars = "libClass="+$("#libClass").val()+"&themeId="+themeId;
		doOptCommon(url, pars, "reload()");
	}
}

//自动校验数据
function doAutoCheck(){
	if (confirm("需要较长时间，确定要自动校验译库停用数据吗？")) {
		var url="transLibAction!doAutoCheck.do";
		var pars = "";
		doOptCommon(url, pars, "reload()");
	}
}

function chgLib(){
	var selLibClass = $("#libClass").val(); 
	if(selLibClass=='CmcTransLibraryTemp'){
		$("#spanToTheme").show();
	}else{
		$("#spanToTheme").hide();
	}
}

$(document).ready(function() {
	chgLib();
});