<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
</head>
<body>
<div class="body-box">
<div class="rhead">
	<div class="rpos">当前位置: 系统属性</div>
	<div class="clear"></div>
</div>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:#c8c8e7 1px solid; border-top:0; margin-top:5px;">
  <thead class="pn-lthead-blue">
  <tr>
    <th height="26" colspan="2" align="left">
	&nbsp;&nbsp;<img src="${ctx }/res/sys/img/admin/ico1.gif" border="0" align="absmiddle" /> <strong>系统属性</strong> </th>
  </tr>
  <tr>
  </thead>
    <td width="18%" height="25" align="right" bgcolor="#FFFFFF" style="border-bottom:#cccccc 1px dashed;">JDK版本：</td>
    <td width="82%" align="left" bgcolor="#FFFFFF" style="border-bottom:#cccccc 1px dashed;"><%=System.getProperty("java.version") %></td>
  </tr>
  <tr style="background-color:#F7F8FA">
    <td height="25" align="right" style="border-bottom:#cccccc 1px dashed;">操作系统版本：</td>
    <td align="left" style="border-bottom:#cccccc 1px dashed;">
    	<%=System.getProperty("os.name") %> <%=System.getProperty("os.version") %> 
    </td>
  </tr>
  <tr>
    <td height="25" align="right" bgcolor="#FFFFFF" style="border-bottom:#cccccc 1px dashed;">操作系统类型：</td>
    <td align="left" bgcolor="#FFFFFF" style="border-bottom:#cccccc 1px dashed;">
		<%=System.getProperty("os.arch") %> <%=System.getProperty("sun.arch.data.model") %>
	</td>
  </tr>
  <tr>
    <td height="25" align="right" bgcolor="#F7F8FA" style="border-bottom:#cccccc 1px dashed;">用户,目录,临时目录：</td>
    <td align="left" bgcolor="#F7F8FA" style="border-bottom:#cccccc 1px dashed;">
		<%=System.getProperty("user.name") %>,<%=System.getProperty("user.dir") %>,<%=System.getProperty("java.io.tmpdir") %>
	</td>
  </tr>
  <tr>
    <td height="25" align="right" bgcolor="#FFFFFF" style="border-bottom:#cccccc 1px dashed;">JAVA运行环境：</td>
    <td align="left" bgcolor="#FFFFFF" style="border-bottom:#cccccc 1px dashed;">
		<%=System.getProperty("java.runtime.name") %> <%=System.getProperty("java.runtime.version") %>
	</td>
  </tr>
  <tr>
    <td height="25" align="right" bgcolor="#F7F8FA" style="border-bottom:#cccccc 1px dashed;">JAVA虚拟机：</td>
    <td align="left" bgcolor="#F7F8FA" style="border-bottom:#cccccc 1px dashed;">
		<%=System.getProperty("java.vm.name") %> <%=System.getProperty("java.vm.version") %>
	</td>
  </tr>
  <tr>
    <td height="25" align="right" bgcolor="#FFFFFF" style="border-bottom:#cccccc 1px dashed;">已用/剩余/最大 内存：</td>
    <td align="left" bgcolor="#FFFFFF" style="border-bottom:#cccccc 1px dashed;">
    <%=(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1048576 %>MB/
    <%=(Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1048676 %>MB/
    <%=Runtime.getRuntime().maxMemory()/1048576 %>MB
    </td>
  </tr>
</table>
</div>
</body>
</html>
<%@ include file="/page/resview.jsp" %>