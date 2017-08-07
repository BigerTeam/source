<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@include file="/WEB-INF/views/include/taglib_js.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>source - 登录</title>
</head>

<body class="gray-bg">

<div class="middle-box text-center loginscreen  animated fadeInDown">
    <div style="padding: 100px 0px;">
        <div>
            <h2 class="logo-name">s_e</h2>
        </div>
        <h3>欢迎使用 source 管理系统</h3>
        <br/>
<%--         <h4 style="color: red;">${tips!}</h4> --%>

<%--         <form class="m-t" role="form" action="${ctx}/account/login" method="post"> --%>
            <div class="form-group">
                <input type="text" id="username" name="username" class="form-control" placeholder="用户名" required="">
            </div>
            <div class="form-group">
                <input type="password" id="password" name="password" class="form-control" placeholder="密码" required="">
            </div>
<!--              <div class="form-group" style="float: left;"> -->
<!--                     <div class="col-sm-8" style="padding-left: 0px; padding-right: 0px;"> -->
<!--                         <input class="form-control" type="text" name="kaptcha" placeholder="验证码" required=""> -->
<!--                     </div> -->
<!--                     <div class="col-sm-4" style="padding-left: 0px; padding-right: 0px;"> -->
<%--                         <img src="${ctx}/kaptcha" id="kaptcha" width="10%" height="10%"/> --%>
<!--                     </div> -->
<!--                 </div> -->
            <button type="button" class="btn btn-primary block full-width m-b" onclick="login();">登 录</button>
            </p>
<!--         </form> -->
    </div>
</div>

<script>
    $(function(){
        $("#kaptcha").on('click',function(){
            $("#kaptcha").attr('src', '${ctx}/kaptcha?' + Math.floor(Math.random()*100) ).fadeIn();
        });
    });
    
    

    /**
     * 登录系统
     */
    function login() {
        if (validate()) {
            U.post({
                url: "${ctx}/account/login",
                loading: true,
                data: {
                    username: $('#username').val().trim(),
                    password: $('#password').val().trim(),
//                     captcha: $('#captcha').val().trim()
                },
                success: function (data) {
                    if (data.code == 200) {
                        if (data.body != null) {
                            U.msg('登录成功');
                            window.location.href = "${ctx}/admin/index";
                        }
                    } else if (data.code == 200002) {
                        U.msg('验证码错误');
                    } else if (data.code == 200001) {
                        U.msg('用户名或密码错误');
                    } else if (data.code == 404) {
                        U.msg('找不到该用户');
                    } else {
                        U.msg('服务器异常');
                    }
                }
            });
        }
    }

    function validate() {
        var username = $('#username').val().trim();
        var password = $('#password').val().trim();
//         var captcha = $('#captcha').val().trim();

        if (U.isEmpty(username)) {
            U.msg('请输入用户名');
            return false;
        }
        if (U.isEmpty(password) || password == '密码') {
            U.msg('请输入密码');
            return false;
        }
//         console.log(captcha);
//         if (U.isEmpty(captcha) || captcha == '验证码') {
//             U.msg('请输入验证码');
//             return false;
//         }
        return true;
    }
</script>

</body>

</html>