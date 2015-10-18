<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
<div class="role-list">
	<c:forEach items="${list }" var="role">
		<div class="pn-sp"><a href="${ctx }/assign-authority/assignAuthority?roleId=${role.id }"  target="assginAuthorityFrame">${role.name }</a></div>
	
	</c:forEach>
 
</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/reslist.jsp" %>
<style>
<!--
.role-list{margin: 5px 0 0 5px;padding:5px;border:1px solid #B4CFCF;background:#F9FBFD;position: absolute;height: 96%;width: 90%;}
.pn-sp{text-align: center;vertical-align: middle;}
-->
</style>