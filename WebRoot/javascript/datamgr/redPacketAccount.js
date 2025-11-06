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
	
	var url = "redPacketAccountAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

//发红包
function packetQuery(page) {
	//对跳转的页码判断
	if (page != null) {
		if(isNaN(page)){
			alert("输入的页码不正确！");
			return;
		}
		$("#nowpage").val(page);
	}else
		$("#nowpage").val(1);
	
	var url = "redPacketAccountAction!doPacketQuery.do";
	var pars = "userid="+uid + (page != null ? "&page=" + page : "");

	$.ajax({type: 'post', url: url, data: pars, complete: function(result){
		$("#rppacketnav").html(result.responseText);
	}});
}

//红包领取记录
function receiveQuery(page) {
	//对跳转的页码判断
	if (page != null) {
		if(isNaN(page)){
			alert("输入的页码不正确！");
			return;
		}
		$("#nowpage").val(page);
	}else
		$("#nowpage").val(1);
	
	var url = "redPacketAccountAction!doReceiveQuery.do";
	var pars = "userid="+uid + (page != null ? "&page=" + page : "");

	$.ajax({type: 'post', url: url, data: pars, complete: function(result){
		$("#rpreceivenav").html(result.responseText);
	}});
}

//提现记录
function cashQuery(page) {
	//对跳转的页码判断
	if (page != null) {
		if(isNaN(page)){
			alert("输入的页码不正确！");
			return;
		}
		$("#nowpage").val(page);
	}else
		$("#nowpage").val(1);
	
	var url = "redPacketAccountAction!doCashQuery.do";
	var pars = "userid="+uid + (page != null ? "&page=" + page : "");

	$.ajax({type: 'post', url: url, data: pars, complete: function(result){
		$("#rpcashnav").html(result.responseText);
	}});
}

function editData(userid){
	uid = userid;
	var url = "redPacketAccountAction!doEdit.do";
	var pars = "userid="+uid;
	$.ajax({type:'post', url: url, data: pars, complete: function(result){
		$("#searchArea").hide();
		$("#resultArea").hide();
		$("#editorArea").show();
		$("#editorArea").html(result.responseText);
		packetQuery();
		receiveQuery();
		cashQuery();
	}});
}

function backList(){
	$("#searchArea").show();
	$("#resultArea").show();
	$("#editorArea").hide();
	$(".nav-one a").click();
}

//查看红包领取列表
function packetReceived(packetid){
	openWin("redPacketAction!doReceivedQuery.do?packetid="+packetid,"红包领取列表",700,360);
}

function openWin(url,name,iWidth,iHeight) { 
    //获得窗口的垂直位置 
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2; 
    //获得窗口的水平位置 
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; 
    window.open(url, name, 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no'); 
}
