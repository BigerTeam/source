<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/easyui-common.jsp"%>
<%@ include file="/WEB-INF/views/include/commoncss.jsp"%>

<script type="text/javascript">
    $(function () {
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        $("#btn_close").bind("click", function () {
            parent.layer.close(index);
        });
        $("#btn_save").bind("click", function () {
            var ids = Feng.zTreeCheckedNodes("zTree");
            var ajax = new $ax(Feng.ctxPath + "/system/user/setRole", function (data) {
              if(data.code==200){
            	  Feng.success("分配角色成功!");
                  window.parent.MgrUser.init();
                  parent.layer.close(index);
              }else{
                  Feng.error("分配角色失败!" + data.message + "!");

              }
            }, function (data) {
                Feng.error("分配角色失败!");
            });
            ajax.set("roleIds", ids);
            ajax.set("userId", "${userId}");
            ajax.start();
        });

        initZtree();
    });

    function initZtree() {
        var setting = {
            check: {
                enable: true,
                chkboxType: {
                    "Y": "",
                    "N": ""
                }
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };
        var ztree = new $ZTree("zTree", "/system/role/roleTreeListByUserId/${userId}");
        ztree.setSettings(setting);
        ztree.init();
    }
</script>
<title>分配权限</title>
</head>
<body>
<!-- 配置grid -->
<div class="container"
     style="padding:  10px 40px !important; margin-top: 10px; text-align: center !important;">
    <div class="row">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>${userAccount}</h5>
            </div>
            <div class="ibox-content">
                <ul id="zTree" class="ztree"></ul>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <button class="btn btn-sm btn-info" type="button" id="btn_save">
                <i class="ace-icon fa fa-check bigger-110"></i> 保存
            </button>
            &nbsp;
            <button class="btn btn-sm btn-danger" type="button" id="btn_close">
                <i class="ace-icon fa fa-close bigger-110"></i> 关闭
            </button>
        </div>
    </div>
</div>
</body>
</html>