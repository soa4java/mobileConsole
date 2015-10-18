<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置:IOS安装包 - 查看</div>
			<div class="clear"></div>
		</div>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">版本</td>
					<td colspan="1" width="38%" class="pn-fcontent">${ver }</td>
					<td width="12%" class="pn-flabel">名称</td>
					<td colspan="1" width="38%" class="pn-fcontent">${name }</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">程序路径</td>
					<td colspan="1" width="38%" class="pn-fcontent">${websizeurl }</td>
					<td width="12%" class="pn-flabel">更新日期</td>
					<td colspan="1" width="38%" class="pn-fcontent">${dt }</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">说明</td>
					<td colspan="3"  class="pn-fcontent"><textarea > ${msg }</textarea></td>
 				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="reset" value="返回" class="opt-btn" onclick="history.back();" />
					</td>
				</tr>
			</table>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/resview.jsp" %>