<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 操作日志 - ${id eq null?"新增":"修改" }</div>
			<div class="ropt"><input type="button" value="返回列表" class="ps-btn" onclick="history.back();" /></div>
			<div class="clear"></div>
		</div>
		<form method="post" action="${ctx }/log/opt-log!save.action" id="form">
			<input type="hidden" name="id" value="${id }"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<s:token/>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>ID</td>
					<td  width="38%" class="pn-fcontent">
						<c:if test="${id ne null}"><input type="hidden" name="id" value="${id }"/>${id }</c:if>	
						<c:if test="${id eq null}"><input type="text" name="id" value="${id }" validate="{required:true,number:true,remote:'${ctx }/log/opt-log!checkUnique.action?propertyName=id',messages:{remote:'此ID已被使用'}}"/></c:if>							
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>操作人员</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="operator" value="${operator }" validate="{required:true,maxlength:20}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">执行时间</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="time" value="${time }" validate="{maxlength:20}"/>
					</td>
					<td width="12%" class="pn-flabel">操作类型</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="opt" value="${opt }" validate="{maxlength:20}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">操作对象</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="obj" value="${obj }" validate="{maxlength:20}"/>
					</td>
					<td width="12%" class="pn-flabel">操作对象名称</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="label" value="${label }" validate="{maxlength:20}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">操作对象标识</td>
					<td colspan='3' width="38%" class="pn-fcontent">
						<input type="text" name="identifier" value="${identifier }" validate="{maxlength:100}"/>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="submit" value="提交" class="ps-btn"/>&nbsp; 
						<input type="reset" value="重置" class="ps-btn" />&nbsp; 
						<input type="button" value="返回" class="ps-btn" onclick="history.back();" />
					</td>
				</tr>
			</table>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/resform.jsp" %>
<script type="text/javascript">
	$(function() {
		$("#form").validate();
	});
</script>