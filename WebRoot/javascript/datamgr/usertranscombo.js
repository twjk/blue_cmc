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
	
	var url = "userTransComboAction!doQuery.do";
	var pars = $("#qryForm").serialize() + (page != null ? "&page=" + page : "");
	doQuery(url, pars);
}

function detailData(id) {
	var url = "userTransComboAction!doDetail.do";
	var pars = "ucid=" + id;
	doEdit(url, pars);
}

function editData() {
	var url = "userTransComboAction!doEdit.do";
	var pars = "";
	doEdit(url, pars);
}

//选择套餐
function selectCombo(){
	$("#pkgList li").hide();
	$("#pkgList li:first").show();
	$("#pkgList").find(":input[name='entity.pkgid']").removeAttr("checked");
	$("#trStartTime").hide();
	$("#trPackage").show();
	$("#saveResult").text('');
	
	var comboId = $("#selCombo").val();
	if(comboId=='') return;
	
	var priceUnit = $("#selCombo").find("option:selected").attr("priceUnit");
		
	$("#pkgList").find("li[comboId='"+comboId+"']").show();
	$("#trNum").show();
	if(priceUnit==2){
		$("#trStartTime").show();
	}
}

//选择特价套餐
function checkPackage(){
	var pkgid = getRadValue("entity.pkgid");
	if(pkgid || pkgid!=''){
		$("#trNum").hide();
	}else{
		$("#trNum").show();
	}
	$("#saveResult").text('');
}

function saveData() {
	if(validator()){
		btnDisabled();
		var url = "userTransComboAction!doSave.do";
		var pars = $('#editForm').serialize();
		$.ajax({type:'post',url:url, data:pars, complete:function(result){
			$("#saveResult").text(result.responseText);
			btnEnabled();
		}});
	}
}

//作废
function invalidData(ucid) {
	var url = "userTransComboAction!doInvalid.do";
	var pars = "ucid=" + ucid;
	if (confirm("确定要作废翻译套餐吗？")) {
		doOptCommon(url, pars);
	}
}

//删除
function deleteData(ucid) {
	var url = "userTransComboAction!doDelete.do";
	var pars = "ucid=" + ucid;
	if (confirm("确定要删除翻译套餐吗？")) {
		doOptCommon(url, pars);
	}
}

function validator() {
	initValidator();
	if(!jQuery.formValidator.pageIsValid('1')) return false;
	return true;
}

function initValidator(){
	$.formValidator.initConfig({autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});
	$("#payside_ctrip").formValidator().functionValidator({fun:checkPaySide});
    $("#userid").formValidator({empty:true}).regexValidator({regexp:"intege",datatype:"enum",onerror:"请正确填写用户编号"});
    $("#selCombo").formValidator().inputValidator({min:1,onerror:"请选择翻译套餐"});
    $("#num").formValidator({empty:true}).regexValidator({regexp:"intege",datatype:"enum",onerror:"请正确填写天/次数"});
    $("#noPkg").formValidator().functionValidator({fun:checkNumAndPackage});
}

function checkPaySide(){
	var payside = getRadValue("entity.payside");
	if(!payside || payside==''){
		return "请选择付款方";
	}
}

function checkNumAndPackage(){
	var pkgid = getRadValue("entity.pkgid");
	var num = $("#num").val();
	if((!pkgid || pkgid=='')
			&&(!num || num=='')){
		return "请选择特价套餐或者填写自选天/次数";
	}
}

//添加使用用户套餐
function addUseUserCombo(){
	showBgiframe("useUserComboDiv",0,300);
}

function hideUseUserCombo(){
	$("#useUserComboDiv").hide();
}

function saveUseUserCombo(){
	if(validator4Use()){
		var url = "userTransComboAction!doSaveUsed.do";
		var pars = $('#editForm').serialize();		
		doOptCommonQ(url, pars, 'detailData('+$("#ucid").val()+')');
	}
}

function validator4Use() {
	initValidator4Use();
	if(!jQuery.formValidator.pageIsValid('2')) return false;
	return true;
}

function initValidator4Use(){
	$.formValidator.initConfig({validatorgroup:"2",autotip:false,alertmessage:true,onerror:function(msg,id){showErrInfo(msg,id,0,0);}});
    $("#usedtime").formValidator({validatorgroup:"2"}).inputValidator({min:1,max:100,onerror:"请正确填写使用时间"});
}


function initEditor(){
}