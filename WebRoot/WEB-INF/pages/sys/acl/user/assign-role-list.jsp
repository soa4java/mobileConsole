<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 分配角色 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/acl/assignRoleList.action">
			<input type="hidden" name="page.pageNo" id="pageNo" value="1"/>
			<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
			<input type="hidden" name="page.order" id="order" value="${page.order}"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">用户名</td>
					<td colspan="1" width="38%" class="pn-fcontent">
							<input type="text" maxlength="100" name="filter_LIKES_username" value="${filter_LIKES_username }"/>
					</td>
					<td width="12%" class="pn-flabel">姓名</td>
					<td colspan="1" width="38%" class="pn-fcontent">
							<input type="text" maxlength="100" name="filter_LIKES_name" value="${filter_LIKES_name }"/>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="submit" value="查询" class="ps-btn" />
					</td>
				</tr>
			</table>
		</form>
		<form id="dataListForm" method="post">
			<table class="pn-ltable" style="" width="100%" cellspacing="1" cellpadding="0" border="0">
				<thead class="pn-lthead">
					<tr>
						<th width="20"><input type='checkbox' onclick='Utils.toggleCheckState("ids")' /></th>
						<th>ID</th>
						<th>用户名<span><input type="hidden" name="fieldName" value="username" disabled="disabled"/> </span></th>
						<th>姓名</th>
						<th>机构代码</th>
						<th>机构名称</th>
						<th>用户状态</th>
						<th>当前角色</th>
						<th>操作选项</th>
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
					<c:forEach items="${page.result }" var="vUser">
						<tr align="center" style="background-color: D8E5F2;">
							<td><input type='checkbox' name='ids' value='${vUser.id}' /></td>
							<td>${vUser.id}</td>
							<td>${vUser.username}</td>
							<td>${vUser.name}</td>
							<td>${vUser.deptCode}</td>
							<td>${vUser.deptName}</td>
							<td ${vUser.status eq 0?'class=red':''}>
								<c:forEach items="${itemList }" var="item">
									<c:if test="${item.code==vUser.status}">
										${item.name}  
									</c:if>
								</c:forEach>  
							</td>
							<td width="25%">
							<c:forEach items="${vUser.roleList }" var="role">
							${role.name}
							</c:forEach>
							 </td> 
							<td>
								<c:if test="${sessionScope.hasPermission['AssignRole:assignRole'] }">
								<a href="javascript:void(0)" onclick="assignRole('${vUser.id}');" class="pn-opt">分配角色</a>	</c:if>						
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<jsp:include page="/page/page.jsp"/>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/reslist.jsp" %>
<script type="text/javascript">
	function assignRole(id) {
		var url =  "${ctx }/acl/assignRole?id=" + id + "&rdm=" + Math.random();
		var result = window.showModalDialog(url, window, "dialogWidth:800px;dialogHeight:360px;resizable:no;scroll:auto;status=0;");
		if(result){
			alert(result.msg);
			//window.location.reload();		
			$("#searchForm").submit();	
		}
	}
</script>