/**
 * @Descriptor 	: Js for Object validator ,with pure javascript no jquery
 * @Author		: snow,wei
 * @Date		: 2009-08-07
*/

/*
 * 判断文本数据是否为空
 * obj:对象名,altMsg:提示消息
 */
function textNull(obj,altMsg)
{
	var str = obj.value;
	str = str.replace(/^\s+/g,'').replace(/\s+$/g,'');
	if(str.length<1){
		window.alert(altMsg);
		obj.focus();
		return false;
	}
	return true;
}

/*
 * 对输入串中特殊字符的处理(不空时返回true)
 * obj:对象名,altMsg:提示消息
 */
function checkSepChar(obj,altMsg){
	var str = str = obj.value;;
	if(str.length<1){
		obj.focus();
		alert(altMsg);
		return false;
	}else{
		var checkData = /<|>|'|;|&|#|"|'/;
		if(checkData.test(str) ) {
			obj.focus();
			alert(altMsg);
			return false;
		}
	}
	return true;
}

/*
 * 判断文本数据对象是否超长
 * obj:对象名,altMsg:提示内容,maxLen：最大长度
 */
function textLarge(obj,altMsg,maxLen)
{
	if(obj.value.length > maxLen)
	{
		window.alert(altMsg);
		obj.focus();
		return false;
	}
	return true;
}

/*
 * 判断checkbox/radio对象是否有选中项，没有时返回false
 * obj:checkbox/radio对象名,altMsg:提示内容
 */
function selNull(obj,altMsg)
{
	var flag=false;
	if(obj.checked)
	{
		flag=true;
	}
	for(var i=0;i<obj.length;i++)
	{
		if(obj[i].checked)
		{
			flag=true;
			break;
		}
	}
	if(flag){
	     return true;
	}else{
		window.alert(altMsg);
	    return false;
    }
}

/*
 * 判断select列表对象是否有选中项目，没有时返回false
 * obj:select对象名,altMsg:提示消息
 */
function listNull(obj,altMsg)
{
	if (obj.selectedIndex==-1)
	{
		window.alert(altMsg);
		return false;
	}
	if (obj.find('option:selected').val()==''){
		window.alert(altMsg);
		return false;
	}
	
	return true;
}

/*
 * 判断文本对象输入内容是否为数字
 * obj:文本对象,altMsg:提示消息
 */
function isNumber(obj,altMsg)
{
	if(isNaN(obj.value))
	{
		window.alert(altMsg);
	    return false;
	}
	return true;
}

/*
 * 判断输入对象是否为有效日期格式数据，要求的日期必须是:yyyy-MM-dd计10位字符
 * obj:文本对象
 */
function checkDate(obj){   
	
	if(obj.value.length>8)
	{
	    return false;
	}
	var dateStr = obj.value;
	var matchArray = dateStr.match(/^(\d{1,4})(|\/)(\d{1,2})\2(\d{1,2})$/);
    var mathcArray1=dateStr.match(/^(\d{1,4})(|\/)(\d{1,1})\2(\d{1,2})$/);
    var mathcArray2=dateStr.match(/^(\d{1,4})(|\/)(\d{1,2})\2(\d{1,1})$/);
    var mathcArray3=dateStr.match(/^(\d{1,4})(|\/)(\d{1,1})\2(\d{1,1})$/);
    if (matchArray == null && mathcArray1==null && mathcArray2==null && mathcArray3==null) 
    {   
        return false; 
    }
	var year = dateStr.substring(0,4);
	var month = dateStr.substring(4,6);
	var day = dateStr.substring(6,8);
	if(year.length<4 | year.length>4 | year==0)
	{
		return false;
	}
	if(month>12 | day>31 | month==0 | day==0 | month.length<2 | day.length<2 | month.length>2 | day.length>2)
	{
	    return false;
	}
    if(month==4||month==6||month==9||month==11)
    {
		if(day==31)
	    {
	            return false;
	    }
	}
	if(parseInt(month,10)==2)
	{
		if((year%400)==0||((year%100)!=0&&(year%4)==0))  
		{	
			if(day>29)
			{
				return false;
			}
		}	
		else if(day>28)
		{
			return false;
		}
	}
	return true; 
} 

/*
 * 判断输入对象是否为有效日期格式数据，要求的日期必须是:yyyy-MM-dd HH:mm:ss 计19位字符
 * obj:文本对象,altMsg:提示消息
 */
function isDate(obj,altMsg)
{
	
	if(obj.value.length!=19)
	{
		window.alert(altMsg);
	    return false;
	}
	if(obj.value.substring(4,5)!="-")
	{
		window.alert(altMsg);
	    return false;
	}
	if(obj.value.substring(7,8)!="-")
	{
		window.alert(altMsg);
	    return false;
	}
	if(obj.value.substring(10,11)!=" ")
	{
		window.alert(altMsg);
	    return false;
	}
	if(obj.value.substring(13,14)!=":")
	{
		window.alert(altMsg);
	    return false;
	}
	if(obj.value.substring(16,17)!=":")
	{
		window.alert(altMsg);
	    return false;
	}
	var year=obj.value.substring(0,4);
	var month=obj.value.substring(5,7);
	var day=obj.value.substring(8,10);
	var hour=obj.value.substring(11,13);
	var min=obj.value.substring(14,16);
	var sec=obj.value.substring(17,19);
	if(isNaN(year) | isNaN(month) | isNaN(day) | isNaN(hour) | isNaN(min) | isNaN(sec))
	{
		window.alert(altMsg);
	    return false;
	}
	if(month>12 | day>31 | hour>23 | min>59 | sec>59)
	{
		window.alert(altMsg);
	    return false;
	}
    if(month==4||month==6||month==9||month==11)
    {
		if(day==31)
	    {
				window.alert(altMsg);
	            return false;
	    }
	}
	if(parseInt(month,10)==2)
	{
		if((year%400)==0||((year%100)!=0&&(year%4)==0))  
		{	
			if(day>29)
			{
				window.alert(alt);
				return false;
			}
		}	
		else if(day>28)
		{
			window.alert(alt);
			return false;
		}
	}
	return true;
}

/*
 * 通过checkbox对象进行的全选/全不选操作
 * name:要操作的checkbox名
 * allCheckId:当前checkbox对象
 */
function checkEvent(name, allCheckId) {
    var allCk = document.getElementById(allCheckId);    
    if (allCk.checked == true)
    	checkAll(name);
    else
    	checkAllNo(name);
}

//全选    
function checkAll(name) {
    var names = document.getElementsByName(name);
    var len = names.length;
    if (len > 0) {
        for (var i = 0; i < len; i++)
        names[i].checked = true;
    }
}

//全不选    
function checkAllNo(name) {
    var names = document.getElementsByName(name);
    var len = names.length;
    if (len > 0) {
        for (var i = 0; i < len; i++)
        names[i].checked = false;
    } 
}

/*
 * 通过checkbox对象进行的反选操作
 * name:要操作的checkbox名
 * allCheckId:当前checkbox对象
 */
function reserveCheck(name) {
    var names = document.getElementsByName(name);
    var len = names.length;
    if (len > 0) {
        for (var i = 0; i < len; i++) {
            if (names[i].checked) names[i].checked = false;
            else names[i].checked = true;
        }
    }
}

/*
 * 对非法字符的判断，遇以下字符时返回false
 * obj:操作的text对象,altMsg:提示消息
 */
function invalStr(obj,altMsg){
	var invString = "!@#$%^&*,'\/<>?";
	var s = obj.value;
	if(s!=null && s.length > 0){
		for (var i = 0; i < s.length; i++)
	    {   
	        var c = s.charAt(i);
	        if (invString.indexOf(c) > -1){
	            window.alert(altMsg);
				obj.focus();
				return false;
	        }
	    }
	}
    return true; 
}

/*
 * Description	: 根据信用卡类型对卡号的验证
 * cardNo		: 卡号对象
 * cardType		: 卡类型对象
 * url			: 要调用的AJax链接URL
 * altMsg		: 返回的提示消息
 * 
 */
var ccFlag=false;  //信用卡验证标识
function checkCreditCard(cardNo,cardType,url,altMsg){
	if(cardNo.value.length>1){
		ereg=/^[1-9][0-9]{14,15}$/;//测试信用卡的合法性
		if(ereg.test(cardNo.value)){
			//调用Ajax进行信用卡号的验证
			if(cardType.value.trim() != ''){
				if(url==null){
					url = "commAjaxManager!validateCreditCardInfo.do";
				}
				var pars = "creditCardType=" + cardType.value + "&creditCardNO=" + cardNo.value;
				$.ajax({type: 'post', 
					async : false,
					url: url, 
					data: pars, 
					complete: validateCreditCardResult
					});
				return ccFlag;
			}else{
				alert("信用卡类型未选！");
				cardType.focus();
				return false;
			}
		}else{
			alert(altMsg);
			cardNo.focus();
			return false;
		}
	}
	return true;
}
//信用卡验证返回值
function validateCreditCardResult(data){
	if(data.responseText == 'failure'){
		alert('信用卡验证失败!');
		ccFlag = false;
	}
	else if(data.responseText == 'pauseService'){
		alert('所选的银行暂停服务，请选择其他银行!');
		ccFlag = false;
	}
	else if(data.responseText == 'success')
	{
		//alert('信用卡验证通过!');
		ccFlag = true;
	}
}

/*
 * 信用卡验证码验证(简单位数验证)
 */
function checkCreditValidator(obj,altMsg){
	if(obj.value.length>1){
		ereg=/^[1-9][0-9]{2,3}$/;
		if(ereg.test(obj.value)) 
			return true;
		else{
			alert(altMsg);
			obj.focus();
			return false;
		}
	}
	return true;
}

/*
 * 信用卡有效期的验证
 */
function checkCardTimeValidator(obj,altMsg){
	if(obj.value.length<4){
		alert("请输入有效的信用卡有效期!");
		obj.focus();
		return false;
	}else{
		var cardTime = obj.value;
		ereg=/^[0-9]{2,4}[-]{0,1}[0-9]{2}$/;
		if(!ereg.test(cardTime)){
			alert(altMsg);
			obj.focus();
			return false;
		}
		
		if(cardTime.indexOf("-")>1){
			var cardYear = cardTime.split("-")[0];
			var cardMonth = cardTime.split("-")[1];
			
			//年份必须是2位或4位
			if(cardYear.length>4 || cardYear.length==3 || cardYear.length<2){
				alert("输入的信用卡有效期年份不正确!");
				obj.focus();
				return false;
			}
			
			//年份控制
			var sYear = (cardYear.length==4)?cardYear:"20"+cardYear;
			if(sYear.length==4){
				var curYear = (new Date()).getFullYear();
				var curMonth = (new Date()).getMonth();
				if(parseInt(sYear,10)<=parseInt(curYear,10)){
					if(parseInt(sYear,10)<parseInt(curYear,10)){
						alert("信用卡有效期年份不得小于当前年份!");
						obj.focus();
						return false;
					}else{
						if(parseInt(sYear,10)<parseInt(curMonth,10)){
							alert("信用卡有效期月份不得小于当前月份!");
							obj.focus();
							return false;
						}
					}
				}
			}
			
			//月份必须是01-12之间
			if(parseInt(cardMonth,10)>12 || parseInt(cardMonth,10)<1){
				alert(altMsg);
				obj.focus();
				return false;
			}
		}else{
			//卡有效期必须是4位或6位
			if(cardTime.length>6 || cardTime.length==5 ||cardTime.length<4){
				alert(altMsg);
				obj.focus();
				return false;
			}else{
				var cardYear = null;
				var cardMonth = null;
				if(cardTime.length==6){
					cardYear = cardTime.substring(0,4);
					cardMonth = cardTime.substring(4,6);
				}else{
					cardYear = cardTime.substring(0,2);
					cardMonth = cardTime.substring(2,4);
				}
				
				//年份控制
				var sYear = (cardYear.length==4)?cardYear:"20"+cardYear;
				if(sYear.length==4){
					var curYear = (new Date()).getFullYear();
					var curMonth = (new Date()).getMonth();
					if(parseInt(sYear,10)<=parseInt(curYear,10)){
						if(parseInt(sYear,10)<parseInt(curYear,10)){
							alert("信用卡有效期年份不得小于当前年份!");
							obj.focus();
							return false;
						}else{
							if(parseInt(sYear,10)<parseInt(curMonth,10)){
								alert("信用卡有效期月份不得小于当前月份!");
								obj.focus();
								return false;
							}
						}
					}
				}
				//月份必须是01-12之间
				if(cardMonth==null || parseInt(cardMonth,10)>12 || parseInt(cardMonth,10)<1){
					alert(altMsg);
					obj.focus();
					return false;
				}
			}
		}
	}
	return true;
}

/*
 * 对用户手机号码的验证(移动号段) 
 */
function checkMobileNo(obj,altMsg){
	if(obj.value!=null){
		ereg=/^(((13[4-9]{1})|(15[8-9|0]{1}))+\d{8})$/;
		var phone = DBC2SBC(obj.value);
		if(phone.indexOf(",")>1){
			var arr = phone.split(",");
			for(var i = 0; i < arr.length; i++){
				var iArr = arr[i].trim();
				if(iArr.length==11){
					if(!ereg.test(iArr)){
						alert(altMsg);
						obj.focus();
						return false;
					}
				}else{
					alert(altMsg);
					obj.focus();
					return false;
				}
			}
		}else{
			if(ereg.test(phone)) 
				return true;
			else{
				alert(altMsg);
				obj.focus();
				return false;
			}
		}
	}
	return true;
}

/*
 * 对字符串中文及字母的验证
 * (不进行为空的判断)
 */
function isChineseLetter(obj,altMsg){
	var str = obj.value;
	if(str!=null && str.length>0){
		var c = str.match(/[^ -~]/g);
		var e = str.match(/[a-z]/ig);
		if(c==null && e==null){
			alert(altMsg);
			obj.focus();
			return false;
		}
	}
	return true;
}

/*
 * 检查电话号码中的输入是否有效
 */
function isPhoneNo(obj,altMsg){
	var str = obj.value;
	if(str!=null && str.length>0){
		//电话号码必须是7位或以上
		if(str.length<7){
			alert("号码位数不正确，必须7位或以上!");
			obj.focus();
			return false;
		}
		var validStr = "1234567890-#";
		for(var i=0; i<str.length; i++ ) {
			c = str.charAt(i);
			if(validStr.indexOf(c) == -1 ) {
				alert(altMsg);
				obj.focus();
				return false;
			}
		}
	}
	return true;
}

/*
 * 检查邮政编码(中国大陆)
 */
function isZipCode(obj,altMsg){
	var str = obj.value;
	if(str!=null && str.length==6){
		var ereg = /^[1-9]{1}[0-9]{5}$/;
		if(ereg.test(str)) 
			return true;
		else{
			alert(altMsg);
			obj.focus();
			return false;
		}
	}else{
		alert(altMsg);
		obj.focus();
		return false;
	}
}
