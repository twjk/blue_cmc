var noexistsTip = "没有匹配信息";
var sourceData;

function autocompleteSource(obj){
	if($(obj).next("input[name$='sourceid']").length==0){
		$(obj).after("<input type='hidden' name='"+$(obj).attr("autoname")+"'/>");
	}
	$(obj).autocomplete({
		source: function(request, response) {
            $.ajax({
                url: "localSourceAction!findSource.do",
                dataType: "json",
                data: "key="+request.term,
                success: function(data) {
                	if(data.length==0){
                		data = eval('[{"sourceid":"","sourcename":"没有匹配信息"}]');
                	}
                	sourceData = data;                		
                    response(
                        $.map(data, function(source) {
                        return {
                            label: source.sourcename,
                            value: source.sourcename,
                            code: source.sourceid
                        }
                    }));
                }
            });
		},
		create: function(event, ui) {    
			$(this).bind("click",function(){
				var k = $(this).val();
				if(k==""){
					k = " ";
				}
				$(this).autocomplete("search", k);
			});    
		},
		change: function(event, ui){
			if(ui.item){
				//选择一项
				$(this).next("input[name$='sourceid']").val(ui.item.code);
			}else{
				//未选择
				if(sourceData.length==1
						&& $(this).val()==sourceData[0].sourceid){
					$(this).next("input[name$='sourceid']").val(sourceData[0].sourceid);
				}else{
					$(this).next("input[name$='sourceid']").val("");
				}
			}
		}
	});
}

function initSource(){
	$("input[autoname$='sourceid']").each(function(){
		var obj = $(this);
		var next = $(this).next("input[name$='sourceid']");
		if($(next).length>0){
			if($(next).val()!='' && $(this).val()==''){
				$.ajax({
	                url: "localSourceAction!getSource.do",
	                dataType: "json",
	                data: "id="+$(next).val(),
	                success: function(data) {
	                	if(data){
	                		$(obj).val(data.sourcename);
	                	}
	                }
	            });
			}else if($(next).val()=='' && $(this).val()!=''){
				$.ajax({
	                url: "localSourceAction!getSource.do",
	                dataType: "json",
	                data: "key="+$(this).val(),
	                success: function(data) {
	                	if(data){
	                		$(next).val(data.sourceid);
	                	}
	                }
	            });
			}
		}else{
			$(this).val('');
		}		
	});
}

$(document).ready(function() {
	$("input[autoname$='sourceid']").bind('focus',function(){
		autocompleteSource(this);
	});
	initSource();
});
