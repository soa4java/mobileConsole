<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 操作日志 - 查看</div>
			<div class="clear"></div>
		</div>
		<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
			<tr>
				<td width="12%" class="pn-flabel">ID</td>
				<td  width="38%" class="pn-fcontent">${id }</td>			
				<td width="12%" class="pn-flabel">操作人员</td>
				<td  width="38%" class="pn-fcontent">${operator }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">执行时间</td>
				<td  width="38%" class="pn-fcontent">${time }</td>			
				<td width="12%" class="pn-flabel">操作类型</td>
				<td  width="38%" class="pn-fcontent">${opt }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">操作对象</td>
				<td  width="38%" class="pn-fcontent">${obj }</td>			
				<td width="12%" class="pn-flabel">操作对象名称</td>
				<td  width="38%" class="pn-fcontent">${label }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">操作对象标识</td>
				<td colspan='3' width="38%" class="pn-fcontent">${identifier }</td>			
			</tr>					
			<tr>
				<td colspan="4" class="pn-fbutton">
					<input type="button" value="返回" class="ps-btn" onclick="history.back();" />
				</td>
			</tr>
			</table>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/resview.jsp" %>