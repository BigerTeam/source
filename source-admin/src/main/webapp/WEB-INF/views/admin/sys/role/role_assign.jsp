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
<script type="text/javascript">
	$(function() {
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

		$("#btn_close").bind("click", function() {
			parent.layer.close(index);
		});

		$("#btn_save").bind("click",function() {
					var ids = Feng.zTreeCheckedNodes("zTree");
					var ajax = new $ax(Feng.ctxPath + "/system/role/setAuthority",
							function(data) {
								if (data.code == 200) {
									Feng.success("分配角色成功!");
									window.parent.Role.table.refresh();
									parent.layer.close(index);
								} else {
									Feng.error("分配角色失败!");
								}
							}, function(data) {
								Feng.error("分配角色失败!");
							});
					ajax.set("roleId", "${roleId}");
					ajax.set("ids", ids);
					ajax.start();
				});

		initZtree();
	});

	function initZtree() {
		var setting = {
			check : {
				enable : true,
				chkboxType : {
					"Y" : "ps",
					"N" : "ps"
				}
			},
			data : {
				simpleData : {
					enable : true
				}
			}
		};

		var ztree = new $ZTree("zTree", "/system/menu/menuTreeListByRoleId/"
				+ "${roleId}");
		ztree.setSettings(setting);
		ztree.init();
	}
</script>
<title>添加角色</title>
</head>
<body>
	<!-- 配置grid -->
	<div class="container"
		style="padding: 10px 50px !important; margin-top: 12px; text-align: center !important;">
		<div class="row">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>${roleName}</h5>
				</div>
				<div class="ibox-content">
					<ul id="zTree" class="ztree"></ul>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<button class="btn btn-sm btn-info" type="button" id="btn_save">
					<i class="ace-icon fa fa-check bigger-110"></i>保存
				</button>
				&nbsp;
				<button class="btn btn-sm btn-danger" type="button" id="btn_close">
					<i class="ace-icon fa fa-close bigger-110"></i>关闭
				</button>
			</div>
		</div>
	</div>
</body>
</html>