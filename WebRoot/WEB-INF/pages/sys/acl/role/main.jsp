<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<style type="text/css">
<!--
#roleListFrame{height:99.9%;visibility:inherit;width:99.95%;border: 2;}
#assginAuthorityFrame{height:99.9%;visibility:inherit;width:99.95%;border: 2;}

#rcenter{width:100%;}

#rcenter-left {position:absolute;width:210px;height:100%;padding-top: 1px;left:0;top:0px;bottom:0;right: 0;
	border-top-width: 0px;border-top-style: solid;border-top-color: #ECEFF4;border: 0px;
	border-bottom-width: 0px;border-bottom-style: solid;border-bottom-color: #ECEFF4;
	}
#rcenter-right { position:absolute;left: 210px;top:0px;right: 0;height: 100%;
	border-top-width: 0px;border-top-style: solid;border-top-color: #ECEFF4;border: 0px;
	border-bottom-width: 0px;border-bottom-style: solid;border-bottom-color: #ECEFF4;
	padding-bottom: 100px;
	}
-->
</style>
<!--
<frameset cols="18%,82%" frameborder="0" border="0" framespacing="0" name="roleFrameset"  id="roleFrameset">
	<frame src="${ctx }/acl/assign-authority!roleList.action" name="roleListFrame"  id="roleListFrame" noresize="noresize"/>
	<frame src="#" name="assginAuthorityFrame" id="assginAuthorityFrame" />
</frameset>
-->
<div id="rcenter" style="height: 100%">
	<div id="rcenter-left"  style="height: 100%">
		<iframe id="roleListFrame" name="roleListFrame" src="${ctx }/assign-authority/roleList" frameborder="0"></iframe>
	</div>
	<div id="rcenter-right"  style="height: 100%">
		<iframe id="assginAuthorityFrame" name="assginAuthorityFrame" src="${ctx }/page/blank.html" frameborder="0"></iframe>
	</div>
	
</div>
