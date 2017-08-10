<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/easyui-common.jsp"%>
<%@ include file="/WEB-INF/views/include/commoncss.jsp"%>
<script src="${ctxstatic}/js/admin/sys/role_info.js"
	type="text/javascript"></script>

<title>编辑角色</title>
</head>
<body>
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="form-horizontal" id="roleInfoForm">

				<input type="hidden" id="id" value="${role.id}">

				<div class="row">
					<div class="col-sm-6 b-r">

						<div class="form-group">
							<label class="col-sm-3 control-label">角色名称</label>
							<div class="col-sm-9">
								<input class="form-control" id="name" value="${role.name}"
									name="name">
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label">上级名称</label>
							<div class="col-sm-9">
								<input class="form-control" id="pName" readonly="readonly"
									name="pName" type="text"
									style="background-color: #ffffff !important;"
									onclick="RolInfoDlg.showPNameSelectTree(); return false;"
									value="${pName}" /> <input class="form-control" type="hidden"
									id="pid" value="${role.pid}" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label">部门名称</label>
							<div class="col-sm-9">
								<input class="form-control" id="deptName" readonly="readonly"
									name="deptName" type="text"
									style="background-color: #ffffff !important;"
									onclick="RolInfoDlg.showDeptSelectTree(); return false;"
									value="${deptName}" readonly="readonly" /> <input
									class="form-control" type="hidden" id="deptid"
									value="${role.deptid}" />
							</div>
						</div>
						<div class="hr-line-dashed"></div>



					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<label class="col-sm-3 control-label">别名</label>
							<div class="col-sm-9">
								<input class="form-control" id="tips" value="${role.tips}"
									name="tips">
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label">排序</label>
							<div class="col-sm-9">
								<input class="form-control" id="排序" value="${role.num}" name="num">
							</div>
						</div>
						<div class="hr-line-dashed"></div>
					</div>
				</div>

				<!-- 这是部门下拉框 -->
				<div id="deptContent" class="menuContent"
					style="display: none; position: absolute; z-index: 200;">
					<ul id="deptTree" class="ztree tree-box"
						style="width: 250px !important;"></ul>
				</div>

				<!-- 这是父级菜单下拉框 -->
				<div id="pNameContent" class="menuContent"
					style="display: none; position: absolute; z-index: 200;">
					<ul id="pNameTree" class="ztree tree-box"
						style="width: 250px !important;"></ul>
				</div>

				<div class="row btn-group-m-t">
					<div class="col-sm-10">

						<div class="row btn-group-m-t">
							<div class="col-sm-10" style="margin-left: 126px;">
								<button class="btn btn-info" id="ensure"
									onclick="RolInfoDlg.editSubmit()">
									<i class="fa fa-check"></i>&nbsp;提交
								</button>
								<button class="btn btn-danger" name="取消" id="cancel"
									onclick="RolInfoDlg.close()">
									<i class="fa fa-eraser"></i>&nbsp;取消
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</body>
</html>