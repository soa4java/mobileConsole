<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 白名单 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/black-white/whiteList">
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
		<c:if test="${msg!=null }"><span>${msg }</span></c:if>
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
						<th>操作选项</th>
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
					<c:forEach items="${page.result }" var="white">
						<tr align="center" style="background-color: D8E5F2;">
							<td><input type='checkbox' name='ids' value='${white.id}' /></td>
							<td>${white.id}</td>
							<td>${white.username}</td>
							<td>${white.name}</td>
							<td>${white.deptCode}</td>
							<td>${white.deptName}</td>
							<td ${status eq 0?'class=red':''}><s:property value="@com.yuchengtech.drcb.ipad.service.dict.DictHelper@get(@com.yuchengtech.drcb.ipad.service.dict.DictCode@USER_STATE)[status]"/> </td>
							<td>
								<c:if test="${sessionScope.hasPermission['WhiteList:toBlackList'] }">
								<a href="${ctx }/black-white/toBlackList?id=${white.id}" class="pn-opt">加入黑名单</a>	</c:if>	
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<jsp:include page="/page/page.jsp"/>
			<div class="ps-btn-opt">
				<c:if test="${sessionScope.hasPermission['WhiteList:batchToBlackList'] }">
				<input type="button" class="ps-btn" value="批量加入黑名单"  onclick="Utils.batch('${ctx }/black-white/batchToBlackList.action','启用','个','用户');"/> </c:if> 
			</div>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/reslist.jsp" %>