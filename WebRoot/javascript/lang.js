/**
 使用说明
 <select id="qry_fromlang" autoname="fromtextlang" initvalue="" inithead="0"></select>
 <select id="qry_fromlang" autoname="fromspeechlang" initvalue="" inithead="0"></select>
 autoname：以textlang或speechlang结尾，必填
 initvalue：初始值，可以没有
 inithead：初始头部，可以没有
 */
var textLangJson = '[{"langCode":"zh-cn","langIcon":null,"langName":"中文","langNameLc":"中文","language":"zh-cn","status":1},{"langCode":"zh-cht","langIcon":null,"langName":"繁体中文","langNameLc":"繁體中文","language":"zh-cn","status":1},{"langCode":"zh-hk","langIcon":null,"langName":"粤语","langNameLc":"粤语","language":"zh-cn","status":1},{"langCode":"en","langIcon":null,"langName":"英语","langNameLc":"English","language":"zh-cn","status":1},{"langCode":"fr","langIcon":null,"langName":"法语","langNameLc":"Français","language":"zh-cn","status":1},{"langCode":"de","langIcon":null,"langName":"德语","langNameLc":"Deutsch","language":"zh-cn","status":1},{"langCode":"ko","langIcon":null,"langName":"韩语","langNameLc":"한국어","language":"zh-cn","status":1},{"langCode":"ja","langIcon":null,"langName":"日语","langNameLc":"日本語","language":"zh-cn","status":1},{"langCode":"es","langIcon":null,"langName":"西班牙语","langNameLc":"Español","language":"zh-cn","status":1},{"langCode":"pt","langIcon":null,"langName":"葡萄牙语","langNameLc":"Português","language":"zh-cn","status":1},{"langCode":"it","langIcon":null,"langName":"意大利语","langNameLc":"Italiano","language":"zh-cn","status":1},{"langCode":"ar-eg","langIcon":null,"langName":"阿拉伯语","langNameLc":"العَرَبِيَّة","language":"zh-cn","status":1},{"langCode":"ru","langIcon":null,"langName":"俄罗斯语","langNameLc":"Русский","language":"zh-cn","status":1},{"langCode":"th","langIcon":null,"langName":"泰语","langNameLc":"ไทย","language":"zh-cn","status":1},{"langCode":"cs","langIcon":null,"langName":"捷克语","langNameLc":"Čeština","language":"zh-cn","status":1},{"langCode":"da","langIcon":null,"langName":"丹麦语","langNameLc":"Dansk","language":"zh-cn","status":1},{"langCode":"nl","langIcon":null,"langName":"荷兰语","langNameLc":"Nederlands","language":"zh-cn","status":1},{"langCode":"fi","langIcon":null,"langName":"芬兰语","langNameLc":"Suomi","language":"zh-cn","status":1},{"langCode":"el","langIcon":null,"langName":"希腊语","langNameLc":"Ελληνικά","language":"zh-cn","status":1},{"langCode":"he","langIcon":null,"langName":"希伯来语","langNameLc":"עִבְרִית","language":"zh-cn","status":1},{"langCode":"hi","langIcon":null,"langName":"印地语","langNameLc":"हिंदी","language":"zh-cn","status":1},{"langCode":"hu","langIcon":null,"langName":"匈牙利语","langNameLc":"Magyar","language":"zh-cn","status":1},{"langCode":"id","langIcon":null,"langName":"印尼语","langNameLc":"Indonesia","language":"zh-cn","status":1},{"langCode":"no","langIcon":null,"langName":"挪威语","langNameLc":"Norsk","language":"zh-cn","status":1},{"langCode":"pl","langIcon":null,"langName":"波兰语","langNameLc":"Polski","language":"zh-cn","status":1},{"langCode":"sv","langIcon":null,"langName":"瑞典语","langNameLc":"Svenska","language":"zh-cn","status":1},{"langCode":"tr","langIcon":null,"langName":"土耳其语","langNameLc":"Türkçe","language":"zh-cn","status":1},{"langCode":"vi","langIcon":null,"langName":"越南语","langNameLc":"Tiếng Việt","language":"zh-cn","status":1},{"langCode":"uk","langIcon":null,"langName":"乌克兰语","langNameLc":"Yкраїнська","language":"zh-cn","status":1},{"langCode":"ms","langIcon":null,"langName":"马来语","langNameLc":"Melayu","language":"zh-cn","status":1},{"langCode":"ca","langIcon":null,"langName":"加泰隆语","langNameLc":"Català","language":"zh-cn","status":1},{"langCode":"hr","langIcon":null,"langName":"克罗地亚语","langNameLc":"hrvatski","language":"zh-cn","status":1},{"langCode":"ro","langIcon":null,"langName":"罗马尼亚语","langNameLc":"Română","language":"zh-cn","status":1},{"langCode":"sk","langIcon":null,"langName":"斯洛伐克语","langNameLc":"slovenčina","language":"zh-cn","status":1}]';
var speechLangJson = '[{"langCode":"zh-cn","langIcon":null,"langName":"普通话","langNameLc":"普通话","language":"zh-cn","status":1},{"langCode":"zh-cht","langIcon":null,"langName":"普通话(繁体)","langNameLc":"普通話(繁體)","language":"zh-cn","status":1},{"langCode":"zh-hk","langIcon":null,"langName":"粤语","langNameLc":"粤语","language":"zh-cn","status":1},{"langCode":"en","langIcon":null,"langName":"英语","langNameLc":"English","language":"zh-cn","status":1},{"langCode":"fr","langIcon":null,"langName":"法语","langNameLc":"Français","language":"zh-cn","status":1},{"langCode":"de","langIcon":null,"langName":"德语","langNameLc":"Deutsch","language":"zh-cn","status":1},{"langCode":"ko","langIcon":null,"langName":"韩语","langNameLc":"한국어","language":"zh-cn","status":1},{"langCode":"ja","langIcon":null,"langName":"日语","langNameLc":"日本語","language":"zh-cn","status":1},{"langCode":"es","langIcon":null,"langName":"西班牙语","langNameLc":"Español","language":"zh-cn","status":1},{"langCode":"pt","langIcon":null,"langName":"葡萄牙语","langNameLc":"Português","language":"zh-cn","status":1},{"langCode":"it","langIcon":null,"langName":"意大利语","langNameLc":"Italiano","language":"zh-cn","status":1},{"langCode":"ar-eg","langIcon":null,"langName":"阿拉伯语","langNameLc":"العَرَبِيَّة","language":"zh-cn","status":1},{"langCode":"ru","langIcon":null,"langName":"俄罗斯语","langNameLc":"Русский","language":"zh-cn","status":1},{"langCode":"th","langIcon":null,"langName":"泰语","langNameLc":"ไทย","language":"zh-cn","status":1},{"langCode":"cs","langIcon":null,"langName":"捷克语","langNameLc":"Čeština","language":"zh-cn","status":1},{"langCode":"da","langIcon":null,"langName":"丹麦语","langNameLc":"Dansk","language":"zh-cn","status":1},{"langCode":"nl","langIcon":null,"langName":"荷兰语","langNameLc":"Nederlands","language":"zh-cn","status":1},{"langCode":"fi","langIcon":null,"langName":"芬兰语","langNameLc":"Suomi","language":"zh-cn","status":1},{"langCode":"el","langIcon":null,"langName":"希腊语","langNameLc":"Ελληνικά","language":"zh-cn","status":1},{"langCode":"he","langIcon":null,"langName":"希伯来语","langNameLc":"עִבְרִית","language":"zh-cn","status":1},{"langCode":"hi","langIcon":null,"langName":"印地语","langNameLc":"हिंदी","language":"zh-cn","status":1},{"langCode":"hu","langIcon":null,"langName":"匈牙利语","langNameLc":"Magyar","language":"zh-cn","status":1},{"langCode":"id","langIcon":null,"langName":"印尼语","langNameLc":"Indonesia","language":"zh-cn","status":1},{"langCode":"no","langIcon":null,"langName":"挪威语","langNameLc":"Norsk","language":"zh-cn","status":1},{"langCode":"pl","langIcon":null,"langName":"波兰语","langNameLc":"Polski","language":"zh-cn","status":1},{"langCode":"sv","langIcon":null,"langName":"瑞典语","langNameLc":"Svenska","language":"zh-cn","status":1},{"langCode":"tr","langIcon":null,"langName":"土耳其语","langNameLc":"Türkçe","language":"zh-cn","status":1},{"langCode":"vi","langIcon":null,"langName":"越南语","langNameLc":"Tiếng Việt","language":"zh-cn","status":1},{"langCode":"uk","langIcon":null,"langName":"乌克兰语","langNameLc":"Yкраїнська","language":"zh-cn","status":1},{"langCode":"ms","langIcon":null,"langName":"马来语","langNameLc":"Melayu","language":"zh-cn","status":1},{"langCode":"ca","langIcon":null,"langName":"加泰隆语","langNameLc":"Català","language":"zh-cn","status":1},{"langCode":"hr","langIcon":null,"langName":"克罗地亚语","langNameLc":"hrvatski","language":"zh-cn","status":1},{"langCode":"ro","langIcon":null,"langName":"罗马尼亚语","langNameLc":"Română","language":"zh-cn","status":1},{"langCode":"sk","langIcon":null,"langName":"斯洛伐克语","langNameLc":"slovenčina","language":"zh-cn","status":1}]';

var arrLangHead = new Array();
arrLangHead[0] = "--请选择--";
arrLangHead[1] = "--全部--";
arrLangHead[2] = "--";
arrLangHead[3] = "所有";

//页面加载后初始化
$(document).ready(function() {
	var initvalue, inithead;
	
	var textLangs = $.parseJSON(textLangJson);
	$("select[autoname$='textlang']").each(function(){
		if($(this).children().length>0) return; //避免重复初始化
		
		initvalue = $(this).attr("initvalue");
		inithead = $(this).attr("inithead")*1;

		if(inithead>=0 && inithead<=3){
			$(this).append("<option value=''>"+arrLangHead[inithead]+"</option>");
		}
		
		for (var i = 0; i < textLangs.length; i++) {
			if(initvalue==textLangs[i].langCode){
				$(this).append("<option value='"+textLangs[i].langCode+"' selected>"+textLangs[i].langName+"</option>");	
			}else{
				$(this).append("<option value='"+textLangs[i].langCode+"'>"+textLangs[i].langName+"</option>");
			}
		}
	});
	
	var speechLangs = $.parseJSON(speechLangJson);
	$("select[autoname$='speechlang']").each(function(){
		if($(this).children().length>0) return; //避免重复初始化
		
		initvalue = $(this).attr("initvalue");
		inithead = $(this).attr("inithead")*1;

		if(inithead>=0 && inithead<=3){
			$(this).append("<option value=''>"+arrLangHead[inithead]+"</option>");
		}
		
		for (var i = 0; i < speechLangs.length; i++) {
			if(initvalue==speechLangs[i].langCode){
				$(this).append("<option value='"+speechLangs[i].langCode+"' selected>"+speechLangs[i].langName+"</option>");	
			}else{
				$(this).append("<option value='"+speechLangs[i].langCode+"'>"+speechLangs[i].langName+"</option>");
			}
		}
	});
});