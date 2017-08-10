<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/easyui-common.jsp"%>
<%@ include file="/WEB-INF/views/include/commoncss.jsp"%>
<script src="${ctxstatic}/js/admin/sys/dept_info.js"
	type="text/javascript"></script>

<title>添加部门</title>
</head>
<body>
	<div class="ibox float-e-margins">
    <div class="ibox-content">
        <div class="form-horizontal" id="deptInfoForm">
            <input type="hidden" id="id" value="${dept.id}">
            <div class="row">
                <div class="col-sm-6 b-r">
					<div class="form-group">
							<label class="col-sm-3 control-label">部门名称</label>
							<div class="col-sm-9">
								<input class="form-control" id="simplename"
									value="${dept.simplename}" name="simplename"
									>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
					<div class="form-group">
							<label class="col-sm-3 control-label">部门全称</label>
							<div class="col-sm-9">
								<input class="form-control" id="fullname"
									value="${dept.fullname}" name="fullname"
									>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
					<div class="form-group">
							<label class="col-sm-3 control-label">备注</label>
							<div class="col-sm-9">
								<input class="form-control" id="tips"
									value="${dept.tips}" name="tips"
									>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-6">
                
                	<div class="form-group">
							<label class="col-sm-3 control-label">排序</label>
							<div class="col-sm-9">
								<input class="form-control" id="num"
									value="${dept.num}" name="num"
									>
							</div>
						</div>
						<div class="hr-line-dashed"></div>
                
						<div class="form-group">
								<label class="col-sm-3 control-label">上级部门</label>
								<div class="col-sm-9">
									<input class="form-control" id="pName" readonly="readonly"
										name="pName" type="text"
										style="background-color: #ffffff !important;"
										onclick="DeptInfoDlg.showDeptSelectTree(); return false;"
										value="${pName}" /> 
										<input
										class="form-control" type="hidden" id="pid"
										value="${dept.pid}" />
								</div>
							</div>
							<div class="hr-line-dashed"></div>
                </div>
            </div>

            <!-- 父级部门的选择框 -->
            <div id="parentDeptMenu" class="menuContent"
                 style="display: none; position: absolute; z-index: 200;">
                <ul id="parentDeptMenuTree" class="ztree tree-box" style="width: 245px !important;"></ul>
            </div>


			<div class="row btn-group-m-t">
					<div class="col-sm-10" style="margin-left: 126px;">
						<button class="btn btn-info" id="ensure"
							onclick="DeptInfoDlg.addSubmit()">
							<i class="fa fa-check"></i>&nbsp;提交
						</button>
						<button class="btn btn-danger" name="取消" id="cancel"
							onclick="DeptInfoDlg.close()">
							<i class="fa fa-eraser"></i>&nbsp;取消
						</button>
					</div>
				</div>
        </div>

    </div>
</div>
</body>
</html>