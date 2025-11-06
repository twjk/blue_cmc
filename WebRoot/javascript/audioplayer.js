/**
 * 播放
 * 使用示例：
 * <script type="text/javascript" src="<lt:contextPath/>/javascript/audioplayer.js"></script>
 * <img src="onclick="playAudio('${media}', this);" <lt:contextPath/>/images/play.png" title="播放" style="vertical-align:middle;cursor: pointer;">
 */

var audio;	//播放器

var iconObj;
var iconPath_play = contextPath+"/images/play.png";
var iconPath_stop = contextPath+"/images/stop.png";

function playAudioById(objId, obj){
	var url = $("#"+objId).val();
	playAudio(url, obj)
}

function playAudio(url, obj){
	iconObj = obj;
	//停止
	if($(iconObj).attr('src')==iconPath_stop){
		if(audio!=null){
			audio.pause();
		}
		$(iconObj).attr('src',iconPath_play);
		return;
	}
	
	//播放初始化
	if(audio==null){
		audio = new Audio();
		audio.onended = function() {
			$(iconObj).attr('src',iconPath_play);
		};
		audio.onerror = function() {
			alert('加载失败');
			$(iconObj).attr('src',iconPath_play);
		};
	}else{
		audio.pause();
		$("img[flag='playAudio']").attr('src',iconPath_play);
	}
	
	//播放
	audio.src = url;
	audio.play();
	$(iconObj).attr('src',iconPath_stop).attr('flag','playAudio');
}

