<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta content="always" name="referrer">
<title>用户管理</title>
<%@ include file="/WEB-INF/views/include/easyui-common.jsp"%>
<script src="${ctxstatic}/js/admin/sys/user.js" type="text/javascript"></script>
<link href="${ctxstatic}/css/sys/button.css" rel="stylesheet"
	type="text/css">
<style type="text/css">
.panel, .popover, .progress, .progress-bar {
	box-shadow: none;
}

.panel-default {
	border-color: #ddd;
}

.panel {
	margin-bottom: 20px;
	background-color: #fff;
	border: 1px solid transparent;
	border-radius: 4px;
	-webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
}

.panel-heading {
	color: #333;
	background-color: #f5f5f5;
	border-color: #ddd;
}

.panel-heading {
	padding: 10px 15px;
	border-bottom: 1px solid transparent;
	border-top-left-radius: 3px;
	border-top-right-radius: 3px;
}

.col-lg-2 {
	width: 16.66666667%;
	position: relative;
	min-height: 1px;
	padding-right: 15px;
	padding-left: 15px;
}

/* 		div { */
/*     		display: block; */
/* 	} */
</style>


</head>

<body>
	
	<div>
	<div class="col-lg-2 " style="display: inline-block; float: left; margin-top: 20px">
			<div class="panel panel-default">
				<div class="panel-heading">组织机构</div>
				<div class="panel-body dept-tree">
					<ul id="deptTree" class="ztree"></ul>
				</div>
			</div>
	</div>
	<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560" 
	style="height: 500px;float: left;">
		<div data-options="region:'north',border:false"
			style="padding: 10px 5px;">
			<input id="username" class="easyui-textbox"
				data-options="label:'用户名'" style="width: 160px;" /> <input
				id="mobile" class="easyui-textbox" data-options="label:'电话'"
				style="width: 150px;" /> <select id="gender"
				class="easyui-combobox" panelHeight="auto" name="state" label='性别'
				style="width: 120px">
				<option value="">全部</option>
				<option value="1">男</option>
				<option value="2">女</option>
			</select> <a href="javascript:void(0)" onclick="queryUsers()"
				class="easyui-linkbutton button-blue"
				style="width: 70px; margin-left: 10px;">查&nbsp;询</a>
		</div>
		<div data-options="region:'center',border:false" style="height: 100%">
			<table id="dg" style="width: 70%; height: 100%;" class="easyui-layout">
			</table>
			<div id="tb" style="padding: 2px 5px;">
				<a href="javascript:void(0)" onclick="MgrUser.add()"
					class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
					 <a
					id="btn-edit" href="javascript:void(0)" onclick="MgrUser.edit()"
					class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a> <a
					id="btn-delete" href="javascript:void(0)" onclick="MgrUser.delMgrUser()"
					class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
					
				<a href="javascript:void(0)" onclick="MgrUser.resetPwd()" class="easyui-linkbutton"
					iconCls="icon-mini-edit" plain="true">重置密码</a>
					 <a
					href="javascript:void(0)" onclick="MgrUser.freezeAccount()" class="easyui-linkbutton"
					iconCls="icon-user-config" plain="true">冻结</a> <a
					href="javascript:void(0)" onclick="MgrUser.unfreeze()" class="easyui-linkbutton"
					iconCls="icon-recover-deleted-items" plain="true">解除冻结</a> 
					<a
					href="javascript:void(0)" onclick="MgrUser.roleAssign()"
					class="easyui-linkbutton" iconCls="icon-repair" plain="true">角色分配</a>
			</div>
		</div>
	</div>
	<div id="dlg"></div>
</div>
</body>
</html>
