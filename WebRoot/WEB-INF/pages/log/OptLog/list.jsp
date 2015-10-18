<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 操作日志 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/opt-log/list">
			<input type="hidden" name="page.pageNo" id="pageNo" value="1"/>
			<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
			<input type="hidden" name="page.order" id="order" value="${page.order}"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">操作人员</td>
					<td width="38%" class="pn-fcontent">
						<input type="text" maxlength="20" name="filter_LIKES_operator" value="${filter_LIKES_operator }"/>
					</td>	
					<td width="12%" class="pn-flabel">执行时间</td>
					<td width="38%" class="pn-fcontent">
						<input type="text" maxlength="20" name="filter_LIKES_time" value="${filter_LIKES_time }"/>
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
						<th>操作人员</th>
						<th>执行时间</th>
						<th>操作类型</th>
						<th>操作对象</th>
						<th>操作对象标识</th>
						<th>操作选项</th>
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
					<c:forEach items="${page.result }" var="optLog">
						<tr align="center">
						<td><input type='checkbox' name='ids' value='${optLog.id}' /></td>
						<td>${optLog.id}</td>
						<td>${optLog.operator}</td>
						<td>${optLog.time}</td>
						<td>${optLog.opt}</td>
						<td>${optLog.label}</td>
						<td width="260">${optLog.identifier}</td>
							<td>
							    <c:if test="${session.hasPermission['OptLog:view'] }">
								<a href="${ctx }/opt-log/view?id=${optLog.id}" class="pn-opt" >查看</a> </c:if>
								<c:if test="${session.hasPermission['OptLog:delete'] }">
								<a href="${ctx }/opt-log/delete?id=${optLog.id}" class="pn-opt delete">删除</a>	</c:if>							
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<jsp:include page="/page/page.jsp"/>
			<div class="ps-btn-opt"> 
				<c:if test="${session.hasPermission['OptLog:batchDelete'] }">
				<input type="button" class="ps-btn" value="批量删除"  onclick="Utils.batch('${ctx }/opt-log/batchDelete','删除','条','操作日志');"/></c:if>
				<c:if test="${session.hasPermission['OptLog:export'] }">
				<input type="button" class="ps-btn" value="导出excel"  onclick="Utils.doSearch('${ctx }/opt-log!exportExcel');"/></c:if> 
			</div>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/reslist.jsp" %>