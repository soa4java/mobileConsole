<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<%@ include file="/page/reslist.jsp" %>
</head>
<body>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 角色管理 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/role/list">
			<input type="hidden" name="page.pageNo" id="pageNo" value="1"/>
			<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
			<input type="hidden" name="page.order" id="order" value="${page.order}"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">角色</td>
					<td colspan="1" width="38%" class="pn-fcontent">
							<input type="text" maxlength="100" name="filter_LIKES_name" value="${filter_LIKES_name }"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="pn-fbutton">
						<input type="submit" value="查询" class="ps-btn" />
					</td>
				</tr>
			</table>
		</form>
		<c:if test="${msg!=null }"><span>${msg }</span></c:if>
		<form id="dataListForm" method="post">
			<table class="pn-ltable" width="100%" cellspacing="1" cellpadding="0" border="0">
				<thead class="pn-lthead">
					<tr>
						<th width="20"><input type='checkbox' onclick='Utils.toggleCheckState("ids")' /></th>
						<th>ID</th>
						<th>角色</th>
						<th>用户数量</th>
						<th>描述</th>
						<th>操作选项</th>							
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
				<c:forEach items="${page.result }" var="role">
 						<tr align="center">
							<td><input type='checkbox' name='ids' value='${role.id}' /></td>
							<td>${role.id}</td>
							<td>${role.name}</td>
							<td>${role.userNum}</td>
							<td>${role.intro}</td>
							<td>
								<c:if test="${sessionScope.hasPermission['Role:view'] }">
								<a href="${ctx }/role/view?id=${role.id}" class="pn-opt">查看</a> </c:if>
								<c:if test="${sessionScope.hasPermission['Role:update'] }">
								<a href="${ctx }/role/input?id=${role.id}&pageNo=${page.pageNo}" class="pn-opt">修改</a> </c:if>
								<c:if test="${sessionScope.hasPermission['Role:delete'] }">
								<c:if test="${role.userNum eq null}">
								  <a href="${ctx }/role/delete?id=${role.id}" class="pn-opt"  >删除</a></c:if>								
							     </c:if>
							    <c:if test="${role.userNum != null}">
								  <a href="#" class="pn-opt" onclick="queryUser('${role.id}');">查看用户</a>
							    </c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<jsp:include page="/page/page.jsp"/>			
			<div class="ps-btn-opt">
				<c:if test="${sessionScope.hasPermission['Role:add'] }">
				<input type="button" class="ps-btn" value="新增"  onclick="Utils.forward('${ctx }/role/input');"/></c:if> 
			</div>
		</form>
	</div>
</body>
</html>
<script type="text/javascript">
	function queryUser(roleId) {
		var url =  "${ctx }/acl/v-user-role?filter_EQL_roleId=" + roleId+"&r="+Math.random();
		var result = window.showModalDialog(url, window, "dialogWidth:800px;dialogHeight:450px;resizable:no;scroll:no;status=0;");
			window.location.reload();			
	}

    	
	
</script>