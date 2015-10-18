<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 后台登录日志 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/server-login-log/list">
			<input type="hidden" name="page.pageNo" id="pageNo" value="1"/>
			<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
			<input type="hidden" name="page.order" id="order" value="${page.order}"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">用户</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" maxlength="20" name="filter_LIKES_username" value="${filter_LIKES_username }"/>
					</td>	
					<td width="12%" class="pn-flabel">状态</td>
					<td  width="38%" class="pn-fcontent">
					<select name="filter_EQS_status">
						<option value="">请选择</option>
						<option value="Y">成功</option>
						<option value="N">失败</option>
					</select>
 					</td>	
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">用户名</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" maxlength="20" name="filter_LIKES_empName" value="${filter_LIKES_empName }"/>
					</td>
					<td width="12%" class="pn-flabel">登录时间</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" maxlength="20" name="filter_LIKES_loginTime" value="${filter_LIKES_loginTime }"/>
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
						<th>用户</th>
						<th>用户姓名</th>
						<th>机构代码</th>
						<th>机构名称</th>
						<th>操作</th>
						<th>登录时间</th>
						<th>IP地址</th>
						<th>结果</th>
						<th>操作选项</th>
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
					<c:forEach items="${page.result }" var="log">
						<tr align="center">
						<td><input type='checkbox' name='ids' value='${log.id}' /></td>
						<td>${log.id}</td>
						<td>${log.username}</td>
						<td>${log.empName}</td>
						<td>${log.deptCode}</td>
						<td>${log.deptName}</td>
						<td>${log.opt}</td>
						<td>${log.loginTime}</td>
						<td>${log.ip}</td>
						<td>${log.result}</td>
							<td>
							<c:if test="${sessionScope.hasPermission['LoginLog:delete'] }"><a href="${ctx }/server-login-log/delete?id=${log.id}" class="pn-opt delete">删除</a></c:if>
							<c:if test="${sessionScope.hasPermission['LoginLog:view'] }">
								<c:if test="${log.optNum >= 1 }">
									<a href="${ctx }/server-login-log/viewLog?id=${log.id}" class="pn-opt"  target="_blank">查看</a>
								</c:if>
							</c:if>						
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<jsp:include page="/page/page.jsp"/>
			<div class="ps-btn-opt">
			    <c:if test="${sessionScope.hasPermission['LoginLog:batchDelete'] }">
				<input type="button" class="ps-btn" value="批量删除"  onclick="Utils.batch('${ctx }/server-login-log/batchDelete','删除','条','登录日志');"/></c:if>
				<c:if test="${sessionScope.hasPermission['LoginLog:export'] }"> 
				<input type="button" class="ps-btn" value="导出excel"  onclick="Utils.doSearch('${ctx }/server-login-log/exportExcel');"/> </c:if>
			</div>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/reslist.jsp" %>