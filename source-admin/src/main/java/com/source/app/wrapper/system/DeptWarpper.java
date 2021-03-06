package com.source.app.wrapper.system;

import java.util.Map;

import com.source.app.constant.factory.ConstantFactory;
import com.source.base.warpper.BaseControllerWarpper;
import com.source.utils.ToolUtil;


/**
 * 部门管理包装类
 * @author zhuyangxu
 * @date 2017年8月10日下午2:01:11
 */
public class DeptWarpper  extends BaseControllerWarpper {

    public DeptWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        Integer pid = (Integer) map.get("pid");
        if (ToolUtil.isEmpty(pid) || pid.equals(0)) {
            map.put("pName", "--");
        } else {
            map.put("pName", ConstantFactory.me().getDeptName(pid));
        }
    }
}
