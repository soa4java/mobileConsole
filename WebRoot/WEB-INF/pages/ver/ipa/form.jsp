<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 平板升级包- ${id eq 0?"新增":"修改" }</div>
			<div class="clear"></div>
		</div>
		<div style="color:red;text-align:center;">${errorMassage }</div>
		<form method="post"  id="form" enctype="multipart/form-data" action="${ctx }/ipa/save">
			<input type="hidden" name="id" value="${id }"/>
 			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<s:token/>
					<tr>	
						<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>版本</td>
						<td colspan="3" width="38%" class="pn-fcontent">
							<c:if test="${id eq null}"><input type="text" name="ver" value="${ver }" /></c:if>
							<c:if test="${id ne null}"><input type="hidden" name="ver" value="${ver }">${ver }</c:if>
						</td>
					 				
					</tr>
					<tr>						
						<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>Ipa文件</td>
						<td  width="38%" class="pn-fcontent">
							<input type="file" name="resFile" validate="{required:${id eq null?'true':'false' },accept:'ipa',messages:{required:'请上传ipa文件',accept:'请上传zip文件'}}"/>
						</td>
						<td width="12%" class="pn-flabel">是否强制升级</td>
						<td  width="38%" class="pn-fcontent">
							 <select name="isForceUpdate">
							 	<option value="1" <c:if test="${isForceUpdate eq '1'}">selected</c:if>>强制升级</option>
							 	<option value="0" <c:if test="${isForceUpdate eq '0'}">selected</c:if>>不强制升级</option>
							 </select>
						</td>
					</tr>
				   
				   <tr colspan="3" >
						<td width="12%" class="pn-flabel"><span class="pn-frequired"> </span>企业网站更新地址</td>
						<td colspan="3"   class="pn-fcontent">
						 <input name="websiteurl" type="text" value=""  /> 
  						</td>
					</tr>
						   <tr colspan="3" >
						<td width="12%" class="pn-flabel"><span class="pn-frequired"> </span>Appstore上架ID</td>
						<td colspan="3"   class="pn-fcontent">
						 <input name="appstoreid" type="text" value=""  /> 
  						</td>
					 
					
					<tr colspan="3" >
						<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>说明</td>
						<td colspan="3"   class="pn-fcontent">
						<textarea name="des" validate="{required:true,maxlength:100}">${des }</textarea>
  						</td>
					</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="submit" value="提交" class="opt-btn"/> &nbsp; 
						<input type="reset" value="重置" class="opt-btn" />&nbsp; 
						<input type="button" value="返回" class="opt-btn" onclick="history.back();" />
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