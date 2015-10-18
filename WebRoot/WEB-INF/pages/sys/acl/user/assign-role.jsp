<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ taglib uri="/mpFunction" prefix="mp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<base target="_parent"/>
<title></title>
<%@ include file="/page/resform.jsp" %>
</head>
<body>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 分配角色</div>
			<div class="clear"></div>			
		</div>
		<c:if test="${msg!=null }"><span>${msg }</span></c:if>
		<form method="post" action="${ctx }/acl/save" id="form">
			<input type="hidden" name="id" value="${vuser.id }"> 
			 
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
			<tr>
				<td width="12%" class="pn-flabel">用户名</td>
				<td  width="38%" class="pn-fcontent">${vuser.username }</td>			
				<td width="12%" class="pn-flabel">姓名</td>
				<td  width="38%" class="pn-fcontent">${vuser.name }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">机构代码</td>
				<td  width="38%" class="pn-fcontent">${vuser.deptCode }</td>			
				<td width="12%" class="pn-flabel">机构名称</td>
				<td  width="38%" class="pn-fcontent">${vuser.deptName }</td>			
			</tr>
			<tr>
					<td width="12%" class="pn-flabel">电子邮箱</td>
					<td width="38%" class="pn-fcontent">${vuser.email }</td>
					<td width="12%" class="pn-flabel">用户状态</td>
					<td colspan="3" width="88%" class="pn-fcontent">
					   	<c:forEach items="${itemList }" var="item">
					  		<c:if test="${item.code==vuser.status}">
					  		${item.name }
					  		</c:if>
					    </c:forEach>
 					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">备注</td>
					<td colspan="3" width="88%" class="pn-fcontent">
						${vuser.remark }
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">角色</td>
					<td colspan="3" width="88%" class="pn-fcontent">
					<c:forEach items="${allRoleList }" var="role">
						<input type="checkbox" name="checkedRoleIds" value="${role.id }"
						${mp:contain(checkeRoleIds,role.id)?'checked=true':''}>
						${role.id }-${role.name }
					</c:forEach>
					
 					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="submit" value="提交" class="ps-btn" /> &nbsp; 
						<input type="reset" value="重置" class="ps-btn" />&nbsp; 
						<input type="reset" value="返回" class="ps-btn" onclick="window.close();" />
					</td>
				</tr>
			</table>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<script type="text/javascript">
$(function() {
	$("#form").validate();
});
</script>