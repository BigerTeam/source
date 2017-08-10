<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/easyui-common.jsp"%>
<%@ include file="/WEB-INF/views/include/commoncss.jsp"%>
<script src="${ctxstatic}/js/admin/sys/user_info.js" type="text/javascript"></script>

<title>添加用户</title>
</head>
<body>
	<div class="ibox float-e-margins">
	<div class="ibox-content">
		<div class="form-horizontal" id="userInfoForm">
			<input type="hidden" id="id" value="">
			<div class="row">
				<div class="col-sm-6 b-r">
					<div class="form-group">
   						 <label class="col-sm-3 control-label">账户</label>
    						<div class="col-sm-9">
       						 <input class="form-control" id="account" name="account">
       						 </div>
       				</div>
       				<div class="hr-line-dashed"></div>
					<div class="form-group">
    				<label class="col-sm-3 control-label">性别</label>
					    <div class="col-sm-9">
					        <select class="form-control" id="sex">
					        <option value="1">男</option>
							<option value="2">女</option>
					        </select>
					    </div>
					</div>
       				<div class="hr-line-dashed"></div>
					<div class="form-group">
   						 <label class="col-sm-3 control-label">密码</label>
    						<div class="col-sm-9">
       						 <input class="form-control" id="password" name="password" type="password">
       						 </div>
       				</div>
       				<div class="hr-line-dashed"></div>
       				<div class="form-group">
   						 <label class="col-sm-3 control-label">角色</label>
    						<div class="col-sm-9">
       						 <input class="form-control" id="roleid" name="roleid" disabled="disabled">
       						 </div>
       				</div>
       				<div class="hr-line-dashed"></div>
       				
       				<div class="form-group">
   						 <label class="col-sm-3 control-label">邮箱</label>
    						<div class="col-sm-9">
       						 <input class="form-control" id="email" name="email" type="email">
       						 </div>
       				</div>
       				<div class="hr-line-dashed"></div>
				</div>
				<div class="col-sm-6">
					<div id="driverInfoContent">
						
						<div class="form-group">
   						 <label class="col-sm-3 control-label">姓名</label>
    						<div class="col-sm-9">
       						 <input class="form-control" id="name" name="name" >
       						 </div>
       					</div>
       					<div class="hr-line-dashed"></div>
       					
						
						<div class="form-group">
   						 <label class="col-sm-3 control-label">出生日期</label>
    						<div class="col-sm-9">
       						 <input class="form-control" id="birthday" onclick="laydate({istime: false, format: 'YYYY-MM-DD'})" name="birthday" type="date" >
       						 </div>
       					</div>
       					<div class="hr-line-dashed"></div>
						
						
						<div class="form-group">
   						 <label class="col-sm-3 control-label">确认密码</label>
    						<div class="col-sm-9">
       						 <input class="form-control" id="rePassword"  name="rePassword" type="password" />
       						 </div>
       					</div>
       					 <div class="hr-line-dashed"></div>
       					
						<div class="form-group">
   						 <label class="col-sm-3 control-label">部门</label>
    						<div class="col-sm-9">
       						 <input class="form-control" id="citySel" readonly="readonly" 
       						 	name="citySel" type="text"  style="background-color: #ffffff !important;"
  								onclick="UserInfoDlg.showDeptSelectTree(); return false;"
       						 	/>
       							<input class="form-control" type="hidden" id="deptid" value="" />
       						 </div>
       					</div>
       					 <div class="hr-line-dashed"></div>
						
						
						<div class="form-group">
   						 <label class="col-sm-3 control-label">电话</label>
    						<div class="col-sm-9">
       						 <input class="form-control" id="phone" 	name="phone" type="text"  />
       						 </div>
       					</div>
					</div>
				</div>
			</div>

			<!-- 这是部门选择的下拉框 -->
			<div id="menuContent" class="menuContent"
				style="display: none; position: absolute; z-index: 200;">
				<ul id="treeDemo" class="ztree tree-box" style="width: 249px !important;"></ul>
			</div>

			<div class="row btn-group-m-t">
				<div class="col-sm-10" style="margin-left: 126px;">
					<button class="btn btn-info"  id="ensure" onclick="UserInfoDlg.addSubmit()">
						<i class="fa fa-check"></i>&nbsp;提交
					</button>
					<button class="btn btn-danger" name="取消" id="cancel" onclick="UserInfoDlg.close()">
					<i class="fa fa-eraser"></i>&nbsp;取消
					</button>
				</div>
			</div>
		</div>

	</div>
</div>
</body>
</html>