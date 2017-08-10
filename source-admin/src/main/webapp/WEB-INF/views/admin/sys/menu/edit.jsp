<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/easyui-common.jsp"%>
<%@ include file="/WEB-INF/views/include/commoncss.jsp"%>
<script src="${ctxstatic}/js/admin/sys/menu_info.js"
	type="text/javascript"></script>

<title>编辑菜单</title>
</head>
<body>

	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="form-horizontal" id="menuInfoForm">

				<input type="hidden" id="id" value="${menu.id}"> <input
					type="hidden" id="ismenuValue" value="${menu.ismenu}">

				<div class="row">
					<div class="col-sm-6 b-r">

						<div class="col-sm-6 b-r">
							<div class="form-group">
								<label class="col-sm-3 control-label">名称</label>
								<div class="col-sm-9">
									<input class="form-control" id="name" value="${menu.name}"
										name="name">
								</div>
							</div>
							<div class="hr-line-dashed"></div>

							<div class="form-group">
								<label class="col-sm-3 control-label">菜单编号</label>
								<div class="col-sm-9">
									<input class="form-control" id="code" value="${menu.code}"
										name="code">
								</div>
							</div>
							<div class="hr-line-dashed"></div>

							<div class="form-group">
								<label class="col-sm-3 control-label">父级编号</label>
								<div class="col-sm-9">
									<input class="form-control" id="pcodeName" readonly="readonly"
										name="pcodeName" type="text"
										style="background-color: #ffffff !important;"
										onclick="MenuInfoDlg.showMenuSelectTree(); return false;"
										value="${menu.pcodeName}" /> <input class="form-control"
										type="hidden" id="pcode" value="${menu.pcode}" />
									<div id="pcodeTreeDiv"
										style="display: none; position: absolute; z-index: 200;">
										<ul id="pcodeTree" class="ztree tree-box"
											style="width: 244px !important;"></ul>
									</div>
								</div>
							</div>
							<div class="hr-line-dashed"></div>

							<div class="form-group">
								<label class="col-sm-3 control-label">是否是菜单</label>
								<div class="col-sm-9">
									<select class="form-control" id="ismenu">
										<option value="1">是</option>
										<option value="0">不是</option>
									</select>
								</div>
							</div>
							<div class="hr-line-dashed"></div>


						</div>
						<div class="col-sm-6">

							<div class="form-group">
								<label class="col-sm-3 control-label">请求地址</label>
								<div class="col-sm-9">
									<input class="form-control" id="url" value="${menu.url}"
										name="url">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-3 control-label">排序</label>
								<div class="col-sm-9">
									<input class="form-control" id="num" value="${menu.num}"
										name="num">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-3 control-label">图标</label>
								<div class="col-sm-9">
									<input class="form-control" id="icon" value="${menu.icon}"
										name="icon">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
						</div>
					</div>

					<div class="row btn-group-m-t">
						<div class="col-sm-10">

							<div class="row btn-group-m-t">
								<div class="col-sm-10" style="margin-left: 126px;">
									<button class="btn btn-info" id="ensure"
										onclick="MenuInfoDlg.editSubmit()">
										<i class="fa fa-check"></i>&nbsp;提交
									</button>
									<button class="btn btn-danger" name="取消" id="cancel"
										onclick="MenuInfoDlg.close()">
										<i class="fa fa-eraser"></i>&nbsp;取消
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>


</body>
</html>