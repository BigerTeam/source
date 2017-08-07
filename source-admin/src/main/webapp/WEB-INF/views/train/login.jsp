<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctxstatic}/train/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${ctxstatic}/train/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctxstatic}/train/js/train_login.js"></script>
<script type="text/javascript" src="${ctxstatic}/train/js/train_utils.js"></script>
<link href="${ctxstatic}/train/css/train.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="80%" style="margin: 0 auto;" class="table table-bordered">
<tr align="center">
         	<td>用户名：</td> <td><input type="text" value="" class="form-control" name="user_name" id="user_name" placeholder="请输入用户名"></td>
   </tr>
<tr align="center">
         	<td>密    码：</td> <td><input type="password" value="" class="form-control" id="password" name="password" placeholder="请输入密码"></td>
   </tr>
<tr  align="center">
 	<td>验证码：</td> 
 	<td>
 	<iframe id="myiframe" src="${ctx}/train/img" width="500" height="200" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes">
     </iframe>
</td>
   </tr> 
   <tr align="center">
       <td colspan="2" align="center">
       	   <input type="button" class="btn btn-primary btn-fixed-lg" data-dismiss="modal" role="button" id="login" value="登录">
	       <input type="button" class="btn btn-default" data-dismiss="modal" value="返回" />
	   </td>
   </tr>
</table>
</body>
</html>

