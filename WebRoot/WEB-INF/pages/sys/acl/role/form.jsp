<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<%@ include file="/page/resform.jsp" %>
<script type="text/javascript">
	
	$(function() {
		$("#roleForm").validate();
	});
</script>
</head>
<body>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 角色管理 - ${id eq null?'新增':'修改' }</div>
			<div class="clear"></div>
		</div>
		<c:if test="${msg!=null }">
		   <span>${msg }</span>
		</c:if>
		<form method="post" action="${ctx }/role/save" id="roleForm" >
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<input type="hidden" name="id" value="${id}"/>
				<tr>
					<c:if test="${id != null }">
						<td width="12%" class="pn-flabel">角色ID</td>
						<td colspan="1"   width="38%" class="pn-fcontent">${id }</td>
					</c:if>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>角色</td>
					<td colspan="${id ne null?1:3 }" width="38%" class="pn-fcontent">
						<input type="text"  name="name" value="${role.name }" maxlength="100"  validate="{required:true,maxlength:50}"/>
					</td>					
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">描述</td>
					<td colspan="3" width="88%" class="pn-fcontent">
						<textarea name="intro" maxlength="255">${role.intro }</textarea>							
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="button" id="RoleBtn" value="提交" class="ps-btn" onclick="submit()"/> &nbsp; 
						<input type="reset" value="重置" class="ps-btn" />&nbsp; 
						<input type="button" value="返回" class="ps-btn" onclick="history.back();" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>