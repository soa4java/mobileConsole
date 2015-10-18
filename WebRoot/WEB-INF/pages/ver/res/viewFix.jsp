<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置:资源版本管理 - 查看增量版本</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/web-resource/viewFix">
		<input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="page.order" id="order" value="${page.order}"/>
		<input type="hidden" name="filter_EQL_vid"  value="${filter_EQL_vid}"/>
		
		</form>
			<table class="pn-ltable" style="" width="100%" cellspacing="1" cellpadding="0" border="0">
				<thead class="pn-lthead">
					<tr>
						<th>ID</th>
						<th>系统版本号</th>
						<th>增量版本号</th>
						<th>更新地址</th>
						<th>文件大小</th>
						<th>是否强制升级</th>
						<th>更新时间</th>
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
				<c:forEach items="${page.result }" var="webResourceincr">
						<tr align="center"  style="background-color: D8E5F2;">
						    <td>${webResourceincr.id}</td>
							<td>${webResourceincr.vid}</td>							
							<td>${webResourceincr.increamentvid}</td>							
							<td><a href='${ctx }<%=com.yuchengtech.mobile.console.common.Constants.UPLOAD_PATH%>/${baseline}/fix_${webResourceincr.increamentvid}_${webResourceincr.vid}.zip'>下载</a></td>							
							<td>${webResourceincr.vsize}</td>	
							<td><c:if test="${webResourceincr.forceUpdate eq '1'}"><span class='red'>强制升级</span></c:if>
							<c:if test="${webResourceincr.forceUpdate eq '0'}">非强制更新</c:if>
							</td>					
							<td>${webResourceincr.updateDt}</td>						
						</tr>
					</c:forEach>
				</tbody>
			</table> 
			<jsp:include page="/page/page.jsp"/>
			<div class="ps-btn-opt">
						<input type="reset" value="返回" class="opt-btn" onclick="history.back();" />
			</div>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/resview.jsp" %>