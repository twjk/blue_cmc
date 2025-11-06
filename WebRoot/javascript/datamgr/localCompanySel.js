var noexistsTip = "没有匹配信息";
var companyData;

function autocompleteCompany(obj){
	if($(obj).next("input[name$='companyid']").length==0){
		$(obj).after("<input type='hidden' name='"+$(obj).attr("autoname")+"'/>");
	}
	$(obj).autocomplete({
		source: function(request, response) {
            $.ajax({
                url: "localCompanyAction!findCompany.do",
                dataType: "json",
                data: "key="+request.term,
                success: function(data) {
                	if(data.length==0){
                		data = eval('[{"companyid":"","companyname":"没有匹配信息"}]');
                	}
                	companyData = data;                		
                    response(
                        $.map(data, function(company) {
                        return {
                            label: company.companyname,
                            value: company.companyname,
                            code: company.companyid
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
				$(this).next("input[name$='companyid']").val(ui.item.code);
			}else{
				//未选择
				if(companyData.length==1
						&& $(this).val()==companyData[0].companyid){
					$(this).next("input[name$='companyid']").val(companyData[0].companyid);
				}else{
					$(this).next("input[name$='companyid']").val("");
				}
			}
		}
	});
}

function initCompany(){
	$("input[autoname$='companyid']").each(function(){
		var obj = $(this);
		var next = $(this).next("input[name$='companyid']");
		if($(next).length>0){
			if($(next).val()!='' && $(this).val()==''){
				$.ajax({
	                url: "localCompanyAction!getCompany.do",
	                dataType: "json",
	                data: "id="+$(next).val(),
	                success: function(data) {
	                	if(data){
	                		$(obj).val(data.companyname);
	                	}
	                }
	            });
			}else if($(next).val()=='' && $(this).val()!=''){
				$.ajax({
	                url: "localCompanyAction!getCompany.do",
	                dataType: "json",
	                data: "key="+$(this).val(),
	                success: function(data) {
	                	if(data){
	                		$(next).val(data.companyid);
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
	$("input[autoname$='companyid']").bind('focus',function(){
		autocompleteCompany(this);
	});
	initCompany();
});
