<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 网页抓取日志 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/log/crawl-record.action">
			<input type="hidden" name="page.pageNo" id="pageNo" value="1"/>
			<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
			<input type="hidden" name="page.order" id="order" value="${page.order}"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">类型</td>
					<td colspan='3' width="38%" class="pn-fcontent">
							<input type="text" maxlength="20" name="filter_LIKES_type" value="${param.filter_LIKES_type }"/>
					</td>	
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="submit" value="查询" class="ps-btn" />
					</td>
				</tr>
			</table>
		</form>
		<s:actionmessage />
		<form id="dataListForm" method="post">
			<table class="pn-ltable" style="" width="100%" cellspacing="1" cellpadding="0" border="0">
				<thead class="pn-lthead">
					<tr>
						<th width="20"><input type='checkbox' onclick='Utils.toggleCheckState("ids")' /></th>
						<th>ID</th>
						<th>信息版本号</th>
						<th>类型</th>
						<th>耗时</th>
						<th>实际抓取数</th>
						<th>抓取方式</th>
						<th>是否报错</th>
						<th>操作选项</th>
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
					<s:iterator value="page.result">
						<tr align="center">
						<td><input type='checkbox' name='ids' value='${id}' /></td>
						<td>${id}</td>
						<td>${version}</td>
						<td>${type}</td>
						<td>${timeConsuming}</td>
						<td>${numInFact}</td>
						<td>${way}</td>
						<td>${success}</td>
							<td>
								<c:if test="${session.hasPermission['CrawlRecord:view'] }">
								<a href="${ctx }/log/crawl-record!view.action?id=${id}" class="pn-opt">查看</a>  </c:if>
								<c:if test="${session.hasPermission['CrawlRecord:delete'] }">
								<a href="${ctx }/log/crawl-record!delete.action?id=${id}" class="pn-opt delete">删除</a>	</c:if>							
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<jsp:include page="/page/page.jsp"/>
			<div class="ps-btn-opt">
				<c:if test="${session.hasPermission['CrawlRecord:batchDelete'] }">
				<input type="button" class="ps-btn" value="批量删除"  onclick="Utils.batch('${ctx }/log/crawl-record!batchDelete.action','删除','条','网页抓取日志');"/></c:if>
				<c:if test="${session.hasPermission['CrawlRecord:export'] }">
				<input type="button" class="ps-btn" value="导出excel"  onclick="Utils.doSearch('${ctx }/log/crawl-record!exportExcel.action');"/> </c:if> 
			</div>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/reslist.jsp" %>