//从booking采集酒店名称
function doSpiderBooking(){
	if (confirm("需要较长时间，确定从booking采集酒店名称吗？")) {
		var url="transLibSpiderAction!doSpiderBooking.do";
		var pars = "destId="+$("#booking_selCountry").val();
		doOperate(url, pars, "reload()");
	}
}