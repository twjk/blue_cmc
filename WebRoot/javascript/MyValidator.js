var hint = $("<div class='errorTip'></div>");
function showErrInfo(msg,id,x,y){
    var me =$(id);
    var me_this = me.get(0);
    hint.html(msg);
    var pos = me.offset();
    hint.css("left",pos.left+me.width()+x+"px")
    .css("top",pos.top+y+"px")
    .css('position','absolute')
    .css("z-index", "100");
    if ($.fn.bgiframe)
       hint.bgiframe({height: hint.get(0).scrollHeight               
    }); 
    $("body").append(hint);
    hint.show();
    me.bind("blur",function(){
    	hint.hide();
    })
}

function hideErrInfo(){
	hint.hide();
}