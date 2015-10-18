<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 网页抓取日志 - ${id eq null?"新增":"修改" }</div>
			<div class="ropt"><input type="button" value="返回列表" class="ps-btn" onclick="history.back();" /></div>
			<div class="clear"></div>
		</div>
		<form method="post" action="${ctx }/log/crawl-record!save.action" id="form">
			<input type="hidden" name="id" value="${id }"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<s:token/>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>ID</td>
					<td  width="38%" class="pn-fcontent">
						<c:if test="${id ne null}"><input type="hidden" name="id" value="${id }"/>${id }</c:if>	
						<c:if test="${id eq null}"><input type="text" name="id" value="${id }" validate="{required:true,number:true,remote:'${ctx }/log/crawl-record!checkUnique.action?propertyName=id',messages:{remote:'此ID已被使用'}}"/></c:if>							
					</td>
					<td width="12%" class="pn-flabel">信息版本号</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="version" value="${version }" validate="{number:true}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">类型</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="type" value="${type }" validate="{maxlength:20}"/>
					</td>
					<td width="12%" class="pn-flabel">耗时</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="timeConsuming" value="${timeConsuming }" validate="{number:true}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">理论抓取数</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="numInTheory" value="${numInTheory }" validate="{number:true}"/>
					</td>
					<td width="12%" class="pn-flabel">实际抓取数</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="numInFact" value="${numInFact }" validate="{number:true}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">成功信息</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="msg" value="${msg }" validate="{maxlength:100}"/>
					</td>
					<td width="12%" class="pn-flabel">错误信息</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="err" value="${err }" validate="{maxlength:1,000}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">抓取方式</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="way" value="${way }" validate="{maxlength:20}"/>
					</td>
					<td width="12%" class="pn-flabel">是否报错</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="success" value="${success }" validate="{number:true}"/>
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