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

<title>修改密码</title>
</head>
<body>
	<div class="col-sm-4  col-sm-offset-4">
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5>修改密码</h5>
        </div>
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-content" style="border:none !important; ">
                            <div class="form-horizontal">
                                <div class="row">
                                    <div class="col-sm-12">
                                    	<div class="form-group">
					   						 <label class="col-sm-3 control-label">原密码</label>
					    						<div class="col-sm-9">
					       						 <input class="form-control" id="oldPwd" name="oldPwd" type="password">
					       						 </div>
					       				</div>
					       				<div class="hr-line-dashed"></div>
                                    	<div class="form-group">
					   						 <label class="col-sm-3 control-label">新密码</label>
					    						<div class="col-sm-9">
					       						 <input class="form-control" id="newPwd" name="newPwd" type="password">
					       						 </div>
					       				</div>
					       				<div class="hr-line-dashed"></div>
                                    	<div class="form-group">
					   						 <label class="col-sm-3 control-label">新密码验证</label>
					    						<div class="col-sm-9">
					       						 <input class="form-control" id="rePwd" name="rePwd" type="password">
					       						 </div>
					       				</div>
					       				<div class="hr-line-dashed"></div>
                                    </div>
                                </div>
                                <div class="row btn-group-m-t">
                                    <div class="col-sm-10">
										<button class="btn btn-info"  id="ensure" onclick="UserInfoDlg.chPwd()">
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