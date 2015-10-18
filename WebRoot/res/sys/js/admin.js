$(function(){
	$(".pn-ltbody tr").hover(
		function () {$(this).addClass("pn-lhover");},
		function () {$(this).removeClass("pn-lhover"); }
	); 
	$(".pn-ltbody tr").click(function () {
		$(this).addClass("pn-lselected");
		$(this).siblings().removeClass("pn-lselected");
	}); 
	loadOrderIcon();
	$(".pn-lthead th").bind("click",function(){
		var obj = $(this).find("span");
		var fieldName = $(obj).find("input:hidden[name='fieldName']").val();
		if(fieldName){
			var order = "";
			if($(obj).hasClass(OrderConfig.orderAsc)){
				order = OrderConfig.desc;
			}else if($(obj).hasClass(OrderConfig.orderDesc)){
				order = "";
			}else{
				order = OrderConfig.asc;
			}
			addOrderParam(fieldName,order);	
			$("#searchForm").submit();
			$(this).unbind( "click" );
		}
	});	
	
	$("a.delete").click(function () {
		return confirm('您确定删除吗？');
	}); 
})

function jumpPage(pageNo) {
	$("#pageNo").val(pageNo);
	$("#searchForm").submit();
}

function gotoPage(pageNo,totalPages) {
	var exp = "^[1-9]\\d*$";
	if( new String(pageNo).match(exp)){
		var no =parseInt(pageNo);
		var total =parseInt(totalPages);
		if(no>=1&&no<=total){
			jumpPage(no);
		}else{
			alert("您输入的页码超出范围，请重新输入");			
		}
	}else{
		alert("请输入整数");
	}
}

Utils = {
		version : '1.0',
		name:'YuchengTech JavaScript Utils'
	};
Utils.toggleCheckState = function(name) {
	$("input[type=checkbox][name=" + name + "]").each(function() {
		if($(this).attr("checked")){
			$(this).attr("checked", false);
		}else{
			$(this).attr("checked", true);
		}
	});
}
Utils.checkedCount = function(name) {
	var count = 0;
	$("input[type=checkbox][name=" + name + "]").each(function() {
		if($(this).attr("checked")){
			count++;
		}
	});
	return count;
}
Utils.forward =function(url){
	window.location.href=url;
}
Utils.batch = function(action,opt,unit,type){
	if(Utils.checkedCount("ids")>0){
		if(confirm("您是否确认"+opt+"这"+Utils.checkedCount("ids")+unit+type+"？")){
			syscCheckBox();
			$("#dataListForm").attr("action",action);
			$("#dataListForm").submit();								
		}
	}else{
		alert("请选待"+opt+"的"+type+"!");
	}
}
Utils.doSearch = function(action){
	var selector = "#searchForm";
	var old = $(selector).attr("action");
	$(selector).attr("action",action);
	$(selector).submit();				
	$(selector).attr("action",old);
}
function syscCheckBox(){
	$("input[type=checkbox][name='ids']").each(function() {
		if($(this).attr("checked")){
			$(this).next("input[name=identifiers]").attr("disabled", false);
		}else{
			$(this).next("input[name=identifiers]").attr("disabled", true);
		}
	});
	
}
Utils.lmenu = function(id) {
	var m = $('#' + id);
	m.addClass('lmenu');
	m.children().each(function() {
		$(this).children('a').bind('click', function() {
			$(this).parent().addClass("lmenu-focus");
			$(this).blur();
			var li = m.focusElement;
			if (li && li!=this) {
				$(li).parent().removeClass("lmenu-focus");
			}
			m.focusElement=this;
		});
	});
}

OrderConfig = {
	orderbySelector : '#orderBy',
	orderSelector : '#order',
	orderDesc : 'order-desc',
	orderAsc : 'order-asc',
	desc : 'desc',
	asc:'asc'
};

function loadOrderIcon(){
	var orderClassName;
	$("input:hidden[name='fieldName']").each(function(i,n){
		var order = getOrderInfo()[this.value];
		if(order){
			if(OrderConfig.asc==order){
				orderClassName = OrderConfig.orderAsc;
			}else if(OrderConfig.desc==order){
				orderClassName = OrderConfig.orderDesc;
			}else{
				return;
			}		
			$(this).parent("span").addClass(orderClassName);				
		}
	});
}

function getOrderInfo(){
	var orderInfo = {};
	var orderByArray = $(OrderConfig.orderbySelector).val().split(",");
	var orderArray = $(OrderConfig.orderSelector).val().split(",");
	for(var i=0;i<orderByArray.length;i++){
		if(orderByArray[i]){
			orderInfo[orderByArray[i]]=orderArray[i];
		}
	}
	return orderInfo;
}

function addOrderParam(orderby_,order_){
	var orderBy = $(OrderConfig.orderbySelector).val();
	var order = $(OrderConfig.orderSelector).val();	
	if(!getOrderInfo()[orderby_]){		
		orderBy = 	orderBy+","+orderby_;
		order = 	order+","+order_;
	}else{
		var orderByArray = orderBy.split(",");
		var orderArray = order.split(",");
		var orderBy = '';
		var order = '';
		for(var i=0;i<orderByArray.length;i++){
			if(orderByArray[i]){
				if(orderby_==orderByArray[i]){
					if(OrderConfig.desc==order_||OrderConfig.asc==order_){
						orderBy+=","+orderByArray[i];
						order+=","+order_;
					}
				}else{
					orderBy+=","+orderByArray[i];
					order+=","+orderArray[i];
				}					
			}
		}	
	}	
	orderBy = orderBy.indexOf(',')==0?orderBy.replace(',',''):orderBy;
	order = order.indexOf(',')==0?order.replace(',',''):order;
	$(OrderConfig.orderbySelector).val(orderBy);
	$(OrderConfig.orderSelector).val(order);
}