<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置:资源升级包管理 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/web-resource/list">
		<s:token></s:token>
			<input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
			<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
			<input type="hidden" name="page.order" id="order" value="${page.order}"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">版本</td>
					<td colspan="1" width="38%" class="pn-fcontent">
							<input type="text" maxlength="100" name="filter_EQS_curVer" value="${filter_EQS_version }"/>
					</td>
					<td width="12%" class="pn-flabel">说明</td>
					<td colspan="1" width="38%" class="pn-fcontent">
							<input type="text" maxlength="100" name="filter_LIKES_des" value="${filter_LIKES_des }"/>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
							<input type="submit" value="查询" class="ps-btn" />		
	     			</td>
				</tr>
			</table>
		</form>
		<c:if test="${msg!=null }"><span><font color=red> ${msg }</font></span></c:if>		
		<form id="dataListForm" method="post">
			<table class="pn-ltable" style="" width="100%" cellspacing="1" cellpadding="0" border="0">
				<thead class="pn-lthead">
					<tr>
						<th width="20"><input type='checkbox' onclick='Utils.toggleCheckState("ids")' /></th>
						<th>ID</th>
						<th>版本</th>
						<th>描述</th>
						<th>更新日期</th>
						<th>状态</th>
						<th>是否强制升级</th>
						<th align="left">操作选项</th>
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
				<c:forEach items="${page.result }" var="webResource">
						<tr align="center"  style="background-color: D8E5F2;">
						     <td><input type='checkbox' name='ids' value='${webResource.id}' /></td>
							<td>${webResource.vid}</td>							
							<td>${webResource.version}</td>							
							<td>${webResource.des}</td>							
							<td>${webResource.updateDt}</td>	
							<td><c:if test="${webResource.status eq '1'}"><span class='red'>已激活</span></c:if><c:if test="${webResource.status eq '0'}">未生效</c:if><c:if test="${webResource.status ==null}">初始版本</c:if></td>					
							<td><c:if test="${webResource.forceUpdate eq '1'}"><span class='red'>强制升级</span></c:if><c:if test="${webResource.forceUpdate eq '0'}">不强制升级</c:if></td>						
							<td align="left">
								<a href="${ctx }<%=com.yuchengtech.mobile.console.common.Constants.UPLOAD_PATH%>/${webResource.baseline}/full_${webResource.baseline}.zip"  class="pn-opt"> 下载全量版本 </a>
     							<a href="${ctx }/web-resource/viewFix?filter_EQL_vid=${webResource.vid}&baseline=${webResource.baseline}"  class="pn-opt"> |查看增量版本</a>
                     			<c:if test="${webResource.status eq '0' and webResource.vid eq (page.totalCount-1)}"><a  href="javascript:if(confirm('确认要激活吗?'))location='${ctx }/web-resource/active?id=${webResource.id}'"  class="pn-opt">|激活</a>	</c:if>
								<c:if test="${webResource.vid eq (page.totalCount-1)}"><a href="javascript:if(confirm('确认要回退吗?'))location='${ctx }/web-resource/rollback?vid=${webResource.vid}'"  class="pn-opt">|回退</a>	</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>   
			<jsp:include page="/page/page.jsp"/>
			<div class="ps-btn-opt">
				<input type="button" class="opt-btn" value="上传"  onclick="Utils.forward('${ctx }/web-resource/input')"/> 
			</div>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/reslist.jsp" %>