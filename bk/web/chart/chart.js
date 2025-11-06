$(document).ready(function(){
	displayTodayCount();
	displayDayCount();
});

//ApiKey当日访问量
function displayTodayCount(){
	var url = "ApiKeyAction!doTodayCallCount.do";
	var params = "";
	$.ajax({type: 'post', url: url, data: params, complete: function(result){
		$("#todayCount").html(result.responseText);
	}});
}

//ApiKey每日访问量
function displayDayCount(){
	var url = "ApiKeyAction!doDayCallCount.do";
	var params = "";
	$.ajax({type: 'post', url: url, data: params, complete: function(result){
		$("#dayCount").html(result.responseText);
	}});
}