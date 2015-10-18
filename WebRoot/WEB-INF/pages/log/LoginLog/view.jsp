<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div >${entity.empName }[${entity.username }]在${entity.loginTime }从${entity.ip }登录本系统，进行了如下操作：</div>
			<div class="clear"></div>
		</div>
		<table class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
		<tr>
			<th>序号</th>
			<th>人员</th>
			<th>时间</th>
			<th>操作</th>
			<th>数据类型</th>
			<th>数据标识</th>
		</tr>	
		<c:forEach items="${list }" var="option" varStatus="stat">
			<tr>
				<td class="pn-fcontent">${stat.index+1}</td>
				<td class="pn-fcontent">${option.operator}</td>
				<td class="pn-fcontent">${option.time}</td>
				<td class="pn-fcontent">${option.opt}</td>
				<td class="pn-fcontent">${option.label}</td>
				<td class="pn-fcontent">${option.identifier}</td>
			</tr>	
		</c:forEach>	
		 
			</table>
				<div id="buttondiv" name="buttondiv" class="pn-fbutton"><input type=button value="打印" onclick="window.print();this.style.display='none';">
	</div>

</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/resview.jsp" %>
<style type="text/css">
.body-box{margin-left: -356px;position: absolute;left: 50%; width:712px; }
.rhead{border:#000 0;width: 700px;}
.rpos{color:#000; }
.pn-ftable{background-color: #000;width: 712px;}
.pn-ftable th{background-color: #fff;height: 20px;text-align: left;}
.pn-fbutton{margin-top: 10px; }
</style>