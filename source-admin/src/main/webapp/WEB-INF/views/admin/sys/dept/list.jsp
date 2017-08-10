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
<title>部门管理</title>
<%@ include file="/WEB-INF/views/include/easyui-common.jsp"%>
<%@ include file="/WEB-INF/views/include/commoncss.jsp"%>
<script src="${ctxstatic}/js/admin/sys/dept.js" type="text/javascript"></script>
</head>
<body>
	<div class="easyui-layout"
		data-options="fit:true,border:false,minWidth:560"
		style="height: 500px; float: left;">
		<div data-options="region:'north',border:false"
			style="padding: 10px 5px;">

			<input id="condition" class="easyui-textbox"
				data-options="label:'名称'" style="width: 160px;" /> <a
				href="javascript:void(0)" onclick="Dept.search()"
				class="easyui-linkbutton button-blue"
				style="width: 70px; margin-left: 10px;">查&nbsp;询</a>
		</div>
		<div data-options="region:'center',border:false" style="height: 100%">
			<div>
				<div id="tb" style="padding: 2px 5px;" >
				
				<shiro:hasPermission name="/dept/add">  
					<a href="javascript:void(0)" onclick="Dept.openAddDept()"
						class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> 
				</shiro:hasPermission>		
					
				<shiro:hasPermission name="/dept/edit">  
						<a
						id="btn-edit" href="javascript:void(0)"
						onclick="Dept.openDeptDetail()" class="easyui-linkbutton"
						iconCls="icon-edit" plain="true">编辑</a>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="/dept/delete">  
						 <a id="btn-delete"
						href="javascript:void(0)" onclick="Dept.delete()"
						class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
				</shiro:hasPermission>
				</div>
				<table id="DeptTable" data-mobile-responsive="true"
					data-click-to-select="true" >
					<thead>
						<tr>
							<th data-field="selectItem" data-checkbox="true"></th>
						</tr>
					</thead>
				</table>
			</div>

		</div>
	</div>



</body>
</html>
