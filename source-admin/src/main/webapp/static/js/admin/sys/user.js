
var MgrUser = {
	    layerIndex: -1,
	    datagrid:null,
	    seItem: null
};



//初始化数据
MgrUser.init = function() {
	this.datagrid = $('#dg').datagrid({
		method : "get",
		url : Feng.ctxPath + '/system/user/pages',
		fit : true,
		fitColumns : true,
		border : true,
		idField : 'id',
		striped : true,
		pagination : true,
		rownumbers : true,
		pageNumber : 1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 50, 100 ],
		singleSelect : true,
		selectOnCheck : true,
		checkOnSelect : true,
		toolbar : '#tb',
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'id',
			title : 'id',
			hidden : true
		}, {
			field : 'account',
			title : '用户名',
			sortable : true,
			width : 50
		}, {
			field : 'name',
			title : '姓名',
			sortable : true,
			width : 50
		}, {
			field : 'sexName',
			title : '性别',
			sortable : false,
		}, {
			field : 'roleid',
			title : '角色id',
			sortable : true,
			width : 50,
			hidden : true
		}, {
			field : 'roleName',
			title : '角色',
			sortable : false,
			width : 50
		}, {
			field : 'deptid',
			title : '部门id',
			sortable : true,
			width : 50,
			hidden : true
		}, {
			field : 'deptName',
			title : '部门',
			sortable : false,
			width : 50
		}, {
			field : 'email',
			title : '邮箱',
			sortable : true,
			width : 50
		}, {
			field : 'phone',
			title : '电话',
			sortable : true,
			width : 50
		}, {
			field : 'birthday',
			title : '生日',
			sortable : true,
			width : 50
		}, {
			field : 'statusName',
			title : '状态',
			sortable : false,
			width : 50,
			formatter : function(value, row, index) {
				var d = "<a  class='btn btn-success radius'>" + value + "</a>";
				var r = "<p  class='btn btn-danger radius'>" + value + "</p>";
				if (value == '启用') {
					return d;
				} else {
					return r;
				}
			}

		}, {
			field : 'status',
			title : '状态',
			sortable : true,
			width : 100,
			hidden : true

		} ] ],
		onLoadSuccess : function(data) {
			if (data) {
				$.each(data.rows, function(index, item) {
					if (item.checked) {
						$('#dg').datagrid('checkRow', index);
					}
				});
			}
		},
		queryParams : {
			name : $('#username').val(),
			phone : $('#mobile').val(),
			sex : $('#gender').val()
		}
	});
	
}
	
	
	



/**
 * 根据部门id 查询列表
 * 
 * @param id
 * @returns
 */
function queryUsersBydepat(id) {
	$(MgrUser.datagrid).datagrid('load', {
		name : $('#username').val(),
		phone : $('#mobile').val(),
		sex : $('#gender').val(),
		deptid : id
	});
}

/**
 * 点击部门事件
 * 
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
function onClickDept(e, treeId, treeNode) {
	queryUsersBydepat(treeNode.id)
}

function queryUsers() {
	$(MgrUser.datagrid).datagrid('load', {
		name : $('#username').val(),
		phone : $('#mobile').val(),
		sex : $('#gender').val()
	});
}

/**
 * 添加管理员
 */
MgrUser.add = function() {
	var index = layer.open({
		type : 2,
		title : '添加管理员',
		area : [ '800px', '560px' ], // 宽高
		fix : false, // 不固定
		maxmin : true,
		content : Feng.ctxPath + '/user/add'
	});
	this.layerIndex = index;
};


/**
 * 编辑管理员
 */
 MgrUser.edit=function() {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '编辑管理员',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/user/new_edit/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 点击角色分配
 * @param
 */
MgrUser.roleAssign = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '角色分配',
            area: ['300px', '400px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/system/user/role_assign/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};


/**
 * 删除用户
 */
MgrUser.delMgrUser = function () {
    if (this.check()) {
        var operation = function(){
            var userId = MgrUser.seItem.id;
            var ajax = new $ax(Feng.ctxPath + "/system/user/delete", function (data) {
            	if(data.code==200){
            		   Feng.success("删除成功!");
                       MgrUser.init();
            	}else{
            		 Feng.error("删除失败!" + data.message + "!");
            	}
            });
            ajax.set("userId", userId);
            ajax.start();
        };
        Feng.confirm("是否删除用户" + MgrUser.seItem.account + "?",operation);
    }
};

/**
 * 冻结用户账户
 * @param userId
 */
MgrUser.freezeAccount = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        var ajax = new $ax(Feng.ctxPath + "/system/user/freeze", function (data) {
        	if(data.code==200){
        		Feng.success("冻结成功!");
                MgrUser.init();
        	}else{
        		 Feng.error("冻结失败!" + data.message + "!");
        	}
        });
        ajax.set("userId", userId);
        ajax.start();
    }
};

/**
 * 解除冻结用户账户
 * @param userId
 */
MgrUser.unfreeze = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        var ajax = new $ax(Feng.ctxPath + "/system/user/unfreeze", function (data) {
        	if(data.code==200){
        		Feng.success("解除冻结成功!");
                MgrUser.init();
        	}else{
        		 Feng.error("解除冻结失败!" + data.message + "!");
        	}
        });
        ajax.set("userId", userId);
        ajax.start();
    }
}

/**
 * 重置密码
 */
MgrUser.resetPwd = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        parent.layer.confirm('是否重置密码为111111？', {
            btn: ['确定', '取消'],
            shade: false //不显示遮罩
        }, function () {
            var ajax = new $ax(Feng.ctxPath + "/system/user/reset", function (data) {
            	if(data.code==200){
            		Feng.success("重置密码成功!");
                    MgrUser.init();
            	}else{
            		 Feng.error("重置密码失败!" + data.message + "!");
            	}
            });
            ajax.set("userId", userId);
            ajax.start();
        });
    }
};

/**
 * 检查是否选中
 */
MgrUser.check = function () {
	var row =this.datagrid.datagrid('getSelected');
	if (row == null) {
		 Feng.info("请先选中表格中的某一记录！");
		return false
	} else {
		this.seItem = row
		return true
	}
	
}


$(function() {
	var ztree = new $ZTree("deptTree", "/system/dept/tree");
	ztree.bindOnClick(onClickDept);
	ztree.init();
	MgrUser.init();
	
});

	