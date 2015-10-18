<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置:web资源文件 - 查看</div>
			<div class="clear"></div>
		</div>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">编号</td>
					<td colspan="1" width="38%" class="pn-fcontent">${webResource.id }</td>
					<td width="12%" class="pn-flabel">名称</td>
					<td colspan="1" width="38%" class="pn-fcontent">${webResource.name }</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">当前版本</td>
					<td colspan="1" width="38%" class="pn-fcontent">${webResource.curVer }</td>
					<td width="12%" class="pn-flabel">上一个版本</td>
					<td colspan="1" width="38%" class="pn-fcontent">${webResource.lastVer }</td>
				</tr>
				 
				<tr>
					<td width="12%" class="pn-flabel">程序路径</td>
					<td colspan="1" width="38%" class="pn-fcontent">${webResource.url }</td>
					<td width="12%" class="pn-flabel">更新日期</td>
					<td colspan="1" width="38%" class="pn-fcontent">${webResource.updateDt }</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">说明</td>
					<td colspan="3"  class="pn-fcontent">${webResource.des }</td>
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