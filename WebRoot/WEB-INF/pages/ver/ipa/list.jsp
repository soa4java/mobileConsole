<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置:IPA管理 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/ipa/list">
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
		<c:if test="${msg!=null }"><span><font color=red>${msg }</font></span></c:if>		
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
				<c:forEach items="${page.result }" var="ipa" varStatus="status">
						<tr align="center"  style="background-color: D8E5F2;">
						     <td><input type='checkbox' name='ids' value='${ipa.id}' /></td>
							<td>${ipa.vid}</td>							
							<td>${ipa.version}</td>							
							<td>${ipa.des}</td>							
							<td>${ipa.update_dt}</td>	
							<td><c:if test="${ipa.status eq '1'}"><span class='red'>已激活</span></c:if><c:if test="${ipa.status eq '0'}">未生效</c:if><c:if test="${ipa.status ==null}">初始版本</c:if></td>					
							<td><c:if test="${ipa.forceUpdate eq '1'}"><span class='red'>强制升级</span></c:if><c:if test="${ipa.forceUpdate eq '0'}">不强制升级</c:if></td>						
							<td align="left">
								<a href="${ctx }<%=com.yuchengtech.mobile.console.common.Constants.UPLOAD_PATH%>/${ipa.filename}"  class="pn-opt"> 下载 </a>
                     			<c:if test="${ipa.status eq '0' and ipa.vid eq (page.totalCount)}"><a  href="javascript:if(confirm('确认要激活吗?'))location='${ctx }/ipa/active?id=${ipa.id}'"  class="pn-opt">|激活</a>	</c:if>
								<c:if test="${status.last==true}"><a href="javascript:if(confirm('确认要回退吗?'))location='${ctx }/ipa/rollback?id=${ipa.id}'"  class="pn-opt">|回退</a>	</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>   
			<jsp:include page="/page/page.jsp"/>
			<div class="ps-btn-opt">
				<input type="button" class="opt-btn" value="上传"  onclick="Utils.forward('${ctx }/ipa/input')"/> 
			</div>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/reslist.jsp" %>