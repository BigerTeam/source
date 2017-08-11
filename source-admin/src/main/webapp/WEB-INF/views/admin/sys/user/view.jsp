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
<title>查看用户</title>
</head>
<body>
	<div class="col-sm-6  col-sm-offset-3">
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5>用户管理</h5>
        </div>
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-content" style="border:none !important; ">
                            <div class="form-horizontal" id="userInfoForm">
                                <input type="hidden" id="id" value="${user.id}">
                                <input type="hidden" id="sexValue" value="${user.sex}">
                                <input type="hidden" id ="avatar" value="${user.avatar}">
                                
                                <div class="row">
                                    <div class="col-sm-6 b-r">
                                    	<div class="form-group">
										    <label class="col-sm-3 control-label head-scu-label">头像</label>
										    <div class="col-sm-4">
										        <div id="avatarPreId">
										            <div>
										          	 <c:if test="${empty user.avatar}">
										            	<img width="100px" height="100px" src="${ctxPath}/static/img/a1.jpg">
										              </c:if>
										              <c:if test="${not empty user.avatar}">
										              <img width="100px" height="100px" src="${ctxPath}/system/user/kaptcha/${user.avatar}">
										              
										              </c:if>
										             </div>
										        </div>
										    </div>
										    <div class="col-sm-2">
										        <div class="head-scu-btn upload-btn" id="avatarBtnId">
										            <i class="fa fa-upload"></i>&nbsp;上传
										        </div>
										    </div>
										</div>
										    <div class="hr-line-dashed"></div>
                                    

										<div class="form-group">
											    <label class="col-sm-3 control-label">账户</label>
											    <div class="col-sm-9">
											        <input class="form-control" id="account" name="account" value="${user.account}" />
											          <input class="form-control" id="password" name="password" value="${user.password}" type="hidden" />
											        
											    </div>
											</div>
										 <div class="hr-line-dashed"></div>

										<div class="form-group">
										    <label class="col-sm-3 control-label">性别</label>
										    <div class="col-sm-9">
										        <select class="form-control" id="sex" value="${user.sex}">
										            <option value="1">男</option>
                                            		<option value="2">女</option>
										        </select>
										    </div>
										</div>
										 <div class="hr-line-dashed"></div>

										<div class="form-group">
											    <label class="col-sm-3 control-label">角色</label>
											    <div class="col-sm-9">
											        <input class="form-control" id="roleid" name="roleid" value="${roleName}" disabled="disabled" />
											    </div>
											</div>
										 <div class="hr-line-dashed"></div>
										 
										<div class="form-group">
											    <label class="col-sm-3 control-label">邮箱</label>
											    <div class="col-sm-9">
											        <input class="form-control" id="email" name="email" value="${user.email}" type="email" />
											    </div>
											</div>
										 <div class="hr-line-dashed"></div>
                                    </div>

                                    <div class="col-sm-6">
                                        <div id="driverInfoContent">
                                        
                                        	<div class="form-group">
											    <label class="col-sm-3 control-label">姓名</label>
											    <div class="col-sm-9">
											        <input class="form-control" id="name" name="name" value="${user.name}" />
											    </div>
											</div>
										 <div class="hr-line-dashed"></div>
                                        
                                        <div class="form-group">
											    <label class="col-sm-3 control-label">出生日期</label>
											    <div class="col-sm-9">
											        <input class="form-control" id="birthday" name="birthday" type="date"
                                                   value='<fmt:formatDate value="${user.birthday}" type="date" pattern="yyyy-MM-dd"/>'	
                                                    onclick="laydate({istime: false, format: 'YYYY-MM-DD'})"/>
											    </div>
											</div>
										 <div class="hr-line-dashed"></div>
                                        
                                        <div class="form-group">
											    <label class="col-sm-3 control-label">部门</label>
											    <div class="col-sm-9">
											        <input class="form-control" id="citySel" name="citySel" readonly="readonly" value="${deptName}" 
											        	onclick="UserInfoDlg.showInfoDeptSelectTree(); return false;" 
											        	style="background-color: #ffffff !important;"
											        	/>
											         <input class="form-control" id="deptid" name="deptid" readonly="readonly" value="${user.deptid}" type="hidden" />
											        
											        <div id="menuContent" style="display: none; position: absolute; z-index: 200;">
												                <ul id="treeDemo" class="ztree tree-box" style="width:250px !important;"></ul>
												      </div>
											    </div>
											</div>
										 <div class="hr-line-dashed"></div>
                                        
										
										<div class="form-group">
											    <label class="col-sm-3 control-label">电话</label>
											    <div class="col-sm-9">
											        <input class="form-control" id="phone" name="phone" value="${user.phone}" />
											    </div>
											</div>
										 <div class="hr-line-dashed"></div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row btn-group-m-t">
                                    <div class="col-sm-10">
                                    	<button type="button" class="btn btn-info" onclick="UserInfoDlg.editSubmit()" id="ensure">
											    <i class="fa fa-check"></i>&nbsp;提交
											</button>
                                    </div>
                                </div>
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