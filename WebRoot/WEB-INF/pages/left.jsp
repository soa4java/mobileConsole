<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import="java.util.*"  %>
<%@ include file="/page/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>菜单</title>
</head>
<body class="lbody">
<ul id="lmenu">
 
<c:forEach items="${menu }" var="status">
	<li><a href="${ctx }${status.url }" target="rightFrame">${status.name }</a></li>
</c:forEach>
</ul>
</body>
</html>
<%@ include file="/page/reslist.jsp" %>
<script type="text/javascript">
$(function(){
	Utils.lmenu('lmenu');
	var href = $("#lmenu a:first").attr("href");
	if(href){
		top.frames['rightFrame'].location=href;		
	}
});
</script>