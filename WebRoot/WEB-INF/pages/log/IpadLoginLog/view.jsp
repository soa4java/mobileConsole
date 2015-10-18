<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: iPad登录日志 - 查看</div>
			<div class="clear"></div>
		</div>
		<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
			<tr>
				<td width="12%" class="pn-flabel">ID</td>
				<td  width="38%" class="pn-fcontent">${entity.id }</td>			
				<td width="12%" class="pn-flabel">用户</td>
				<td  width="38%" class="pn-fcontent">${entity.username }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">操作</td>
				<td  width="38%" class="pn-fcontent">${entity.opt }</td>			
				<td width="12%" class="pn-flabel">登录时间</td>
				<td  width="38%" class="pn-fcontent">${entity.loginTime }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">来源</td>
				<td  width="38%" class="pn-fcontent">${entity.source }</td>			
				<td width="12%" class="pn-flabel">IP地址</td>
				<td  width="38%" class="pn-fcontent">${entity.ip }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">状态</td>
				<td colspan='3' width="38%" class="pn-fcontent">${entity.status }</td>			
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