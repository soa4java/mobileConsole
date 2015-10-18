<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ taglib uri="/mpFunction" prefix="mp"%>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 用户管理 - ${id eq null?"新增":"修改" }</div>
			<div class="ropt"><input type="reset" value="返回列表" class="ps-btn" onclick="history.back();" /></div>
			<div class="clear"></div>			
		</div>
		<form method="post" action="${ctx }/user/save" id="form">
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">

				<input type="hidden" name="id" value="${id}"/>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>用户名</td>
					<td colspan="1" width="38%" class="pn-fcontent">
					<c:if test="${id eq null}">
						<input type="text" name="username"  maxlength="100"  
						validate="{required:true,username:true,remote:'${ctx }/user/checkUsername?oldUsername=${user.username}',messages:{remote:'此用户名已被使用'}}" />
					</c:if>
					<c:if test="${id ne null}">
						<input type="hidden" name="username" value="${user.username }">${user.username }
					</c:if>
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>姓名</td>
					<td colspan="1" width="38%" class="pn-fcontent">
						<input type="text"  name="name" value="${user.name }"  validate="{required:true,maxlength:20}"/>
					</td>					
				</tr>
				<c:choose>
				<c:when test="${id eq null}">
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>密码</td>
					<td colspan="1" width="38%" class="pn-fcontent">
						<input type="password" autocomplete="off" id="password" name="password" value="${user.password }" class="required" maxlength="100" />
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>确认密码</td>
					<td colspan="1" width="38%" class="pn-fcontent">
						<input type="password" value="${user.password }" autocomplete="off" equalTo="#password" validate="{required:true}" />
					</td>
				</tr>
				</c:when>
				<c:otherwise>
					<input type="hidden" id="password" name="password" value="${user.password }"/>
				</c:otherwise>
				</c:choose>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>电子邮箱</td>
					<td colspan="1" width="38%" class="pn-fcontent">
						<input type="text" name="email"  value="${user.email }"  validate="{required:true,email:true,maxlength:40}"/>
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>用户状态</td>
					<td colspan="3" width="88%" class="pn-fcontent">
						<select name="status">
							<option value="">请选择</option>
							<c:forEach items="${itemList }" var="item">
								<option value="${item.code }"
								<c:if test="${user.status==item.code}">
								selected="selected"
								</c:if>
								>${item.name }</option>
							</c:forEach>
						
						</select>
						 
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">备注</td>
					<td colspan="3" width="88%" class="pn-fcontent">
						<textarea name="remark" validate="{maxlength:100}">${user.remark }</textarea>	
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">角色</td>
					<td colspan="3" width="88%" class="pn-fcontent">
 					<c:forEach items="${roles}" var="role">
						<input type="checkbox" value='${role.id}' name="roleID"
						${mp:contain(roleIDs,role.id)?'checked=true':'' }
						>${role.id}-${role.name}</checkbox>
					</c:forEach>
 					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="submit" value="提交" class="ps-btn"/> &nbsp; 
						<input type="reset" value="重置" class="ps-btn" />&nbsp; 
						<input type="reset" value="返回" class="ps-btn" onclick="history.back();" />
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