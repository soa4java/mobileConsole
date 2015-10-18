<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 网页抓取日志 - 查看</div>
			<div class="clear"></div>
		</div>
		<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
			<tr>
				<td width="12%" class="pn-flabel">ID</td>
				<td  width="38%" class="pn-fcontent">${id }</td>			
				<td width="12%" class="pn-flabel">信息版本号</td>
				<td  width="38%" class="pn-fcontent">${version }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">类型</td>
				<td  width="38%" class="pn-fcontent">${type }</td>			
				<td width="12%" class="pn-flabel">耗时</td>
				<td  width="38%" class="pn-fcontent">${timeConsuming }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">理论抓取数</td>
				<td  width="38%" class="pn-fcontent">${numInTheory }</td>			
				<td width="12%" class="pn-flabel">实际抓取数</td>
				<td  width="38%" class="pn-fcontent">${numInFact }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">成功信息</td>
				<td  width="38%" class="pn-fcontent">${msg }</td>			
				<td width="12%" class="pn-flabel">错误信息</td>
				<td  width="38%" class="pn-fcontent">${err }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">抓取方式</td>
				<td  width="38%" class="pn-fcontent">${way }</td>			
				<td width="12%" class="pn-flabel">是否报错</td>
				<td  width="38%" class="pn-fcontent">${success }</td>			
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