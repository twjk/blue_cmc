function save(){
	$(":checkbox[flag^='proxy']").each(function(){
		var nextInput = $(this).next("input");
		var chargeCheckbox = $(this).next("input").next("input");
		var chargeInput = $(this).next("input").next("input").next("input");
		if(!this.checked){
			$(nextInput).attr("name","");
			$(chargeInput).attr("name","");
		}else{
			if($(nextInput).attr("name")==""){
				$(nextInput).attr("name","map['"+this.value+"'].priority");
			}
			if($(chargeInput).attr("name")==""){
				$(chargeInput).attr("name","map['"+this.value+"'].charge");
			}
			if($(chargeCheckbox).is(':checked')){
				$(chargeInput).val(1);
			}else{
				$(chargeInput).val(0);
			}
		}
		
	});
	
	var url="funcCapAction!doSave.do";
	var pars = $("#editForm").serialize();
	doOptCommon(url,pars,"reload()");
}

function checkProxy(obj){
   	var priority = $("#priority"+obj.value).val();
   	var nextInput = null;
   	$(":checkbox[flag='"+obj.id+"']").each(function(){
   		this.checked = obj.checked;
   		nextInput = $(this).next("input");
   		if(this.checked && $(nextInput).val()==""){
	   		$(nextInput).val(priority);
   		}else if(!this.checked && $(nextInput).val()==priority){
	   		$(nextInput).val("");
   		}
   	});
}

function chgPriority(obj){
	$(":input[flag='"+obj.id+"']").each(function(){
   		this.value = obj.value;
   	});
}

$(document).ready(function(){
	$(":checkbox[flag^='proxy']").bind("click",function(){
		var proxyid = this.value.split("|")[2];
		var priority = $("#priority"+proxyid).val()
		var nextInput = $(this).next("input");
		
		if(this.checked){
			if($(nextInput).val()==''){
				$(nextInput).val(priority);
			}
		}else{
			if($(nextInput).val()==priority){
				$(nextInput).val("");
			}
		}
	});
});
