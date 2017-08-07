<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>source - 404 页面</title>
    <link rel="shortcut icon" href="favicon.ico"> <link href="css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="${ctxstatic}/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">

    <link href="${ctxstatic}/css/animate.min.css" rel="stylesheet">
    <link href="${ctxstatic}/css/style.min862f.css?v=4.1.0" rel="stylesheet">
</head>
<body class="gray-bg">
    <div class="middle-box text-center animated fadeInDown">
        <h1>404</h1>
        <h3 class="font-bold">页面未找到！</h3>

        <div class="error-desc">
            抱歉，页面好像去火星了~
            <form class="form-inline m-t" role="form">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="请输入您需要查找的内容 …">
                </div>
                <button type="submit" class="btn btn-primary">搜索</button>
            </form>
        </div>
    </div>
    <script src="${ctxstatic}/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ctxstatic}/js/bootstrap.min.js?v=3.3.6"></script>
    <script type="${ctxstatic}/stext/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>
</html>