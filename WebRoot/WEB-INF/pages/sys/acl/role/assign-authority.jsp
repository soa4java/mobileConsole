<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ taglib uri="/mpFunction" prefix="mp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/page/resform.jsp" %>
</head>
	<%-- <c:forEach items="${mp:getValus(menu,'1')}" var="status">
			<li>${status.name }</li>
		</c:forEach> --%>
<body>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 授权管理 - 维护</div>
			<div class="clear"></div>
		</div>
		<c:if test="${msg!=null }"><span>${msg }</span></c:if>
		<form method="post" action="${ctx }/assign-authority/save" id="form">
		<input type="hidden" name="roleId" value="${roleId }"/>
		<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
		<tr>
			<td colspan="3" class="pn-fbutton">
				<input type="submit" value="提交" class="ps-btn"/> &nbsp; 
				<input type="reset" value="重置" class="ps-btn" />&nbsp; 
				<input type="button" value="返回" class="ps-btn" onclick="history.back();" />
			</td>
		</tr>
		<c:forEach items="${mp:getValus(menu,'1')}" var="fistMenus">
		<tr> 
			<td class="first"><input type="checkbox" name="id" value="${fistMenus.id }" id="${fistMenus.id }" class="${fistMenus.pid }" 
			${mp:contain(ids,fistMenus.id)?'checked=true':'' }/>${fistMenus.name }</td>
			<td colspan="2" class="pn-fcontent"></td>
		</tr>
		<c:forEach items="${mp:getValus(menu,fistMenus.id)}" var="secondMenus">
		<tr>
			<td class="pn-fcontent"></td>
			<td class="second"><input type="checkbox" name="id" value="${secondMenus.id }"  id="${secondMenus.id }" class="${secondMenus.pid }" 
			${mp:contain(ids,secondMenus.id)?'checked=true':'' }/>${secondMenus.name }</td>
			<td class="pn-fcontent">
			<c:forEach items="${mp:getValus(opt,secondMenus.id)}" var="opt">
				<input type="checkbox" name="id" value="${opt.id }" id="${opt.id }" class="${opt.pid }" 
				${mp:contain(ids,opt.id)?'checked=true':'' }/>${opt.name }
			</c:forEach>
			</td>
		</tr>
		</c:forEach>		
		</c:forEach>
	 </table>
		</form>
	</div>
</body>
</html>
<script type="text/javascript">
	$(function() {
		$("#form").validate();
	});
	$(":checkbox").bind("click",function(){
		checkParentCheckbox(this.className,this.checked);			
		checkChildCheckbox(this.id,this.checked);			
	});	
	function checkChildCheckbox(id,isChecked){	
		if(id){			
			$(":checkbox."+id).each(function(){
				this.checked = isChecked;
				checkChildCheckbox(this.id,isChecked);				
			});
		}
	}
	function checkParentCheckbox(className,isChecked){	
		if(className){
			var parent = $(":checkbox#"+className);
			if(isChecked&&parent){
				$(parent).attr("checked",isChecked);				
			}
			checkParentCheckbox($(parent).attr("class"),isChecked);
		}
	}
</script>
<style type="text/css">
.first{background-color:#F9FBFD;text-align:right;padding-right:3px;height: 17px;width:10%}
.second{background-color:#F9FBFD;text-align:left;padding-right:3px;height: 17px;width:12%}
</style>