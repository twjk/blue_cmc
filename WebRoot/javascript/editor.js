//选择图片
function selectPic(inputId){
	var finder = new CKFinder();
	finder.selectActionFunction = callback_selPic; //设置文件被选中时的函数
	finder.selectActionData = inputId;  //接收地址的input ID 
	finder.popup() ; 
}
//选择图片回调
function callback_selPic(fileUrl,data){
	$("#"+data["selectActionData"]).attr("src",fileUrl);
	var pre = $("#"+data["selectActionData"]).prev(":text");
	if($(pre).length==1){
		$(pre).val(fileUrl);
	}
}

//选择文件
function selectFile(inputId){
	var finder = new CKFinder();
	finder.selectActionFunction = callback_selFile; //设置文件被选中时的函数
	finder.selectActionData = inputId;  //接收地址的input ID 
	finder.popup() ; 
}
//选择文件回调
function callback_selFile(fileUrl,data){
	$("#"+data["selectActionData"]).val(fileUrl);
}