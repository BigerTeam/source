<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxstatic" value="${pageContext.request.contextPath}/static"/>
<script src="${ctxstatic}/js/jquery.min.js?v=2.1.4"></script>
<script src="${ctxstatic}/js/bootstrap.min.js?v=3.3.6"></script>
<script src="${ctxstatic}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctxstatic}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${ctxstatic}/js/plugins/layer/layer.min.js"></script>
<script src="${ctxstatic}/js/hplus.min.js?v=4.1.0"></script>
<script type="text/javascript" src="${ctxstatic}/js/contabs.min.js"></script>
<script src="${ctxstatic}/js/plugins/pace/pace.min.js"></script>
<script src="${ctxstatic}/js/utils.js"></script>
<script src="${ctxstatic}/js/common/ajax-object.js"></script>
<script src="${ctxstatic}/js/common/bootstrap-table-object.js"></script>
<script src="${ctxstatic}/js/common/Feng.js"></script>
<script src="${ctxstatic}/js/common/tree-table-object.js"></script>
<script src="${ctxstatic}/js/common/web-upload-object.js"></script>
<script src="${ctxstatic}/js/common/ztree-object.js"></script>
<script type="text/javascript">
        Feng.addCtx("${ctxPath}");
        Feng.sessionTimeoutRegistry();
</script>
<script src="${ctxPath}/static/js/plugins/validate/bootstrapValidator.min.js"></script>
<script src="${ctxPath}/static/js/plugins/validate/zh_CN.js"></script>
<script src="${ctxPath}/static/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
<script src="${ctxPath}/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${ctxPath}/static/js/plugins/jquery-treegrid/js/jquery.treegrid.min.js"></script>
<script src="${ctxPath}/static/js/plugins/jquery-treegrid/js/jquery.treegrid.bootstrap3.js"></script>
<script src="${ctxPath}/static/js/plugins/jquery-treegrid/extension/jquery.treegrid.extension.js"></script>
<script src="${ctxPath}/static/js/plugins/layer/layer.min.js"></script>
<script src="${ctxPath}/static/js/plugins/iCheck/icheck.min.js"></script>
<script src="${ctxPath}/static/js/plugins/layer/laydate/laydate.js"></script>