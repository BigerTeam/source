package com.source.app.wrapper.system;

import java.util.List;
import java.util.Map;

import com.source.app.constant.factory.ConstantFactory;
import com.source.app.state.IsMenu;
import com.source.base.warpper.BaseControllerWarpper;

/**
 * 菜单管理包装类
 * @author zhuyangxu
 * @date 2017年8月10日下午2:00:49
 */
public class MenuWarpper extends BaseControllerWarpper {

    public MenuWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("statusName", ConstantFactory.me().getMenuStatusName((Integer) map.get("status")));
        map.put("isMenuName", IsMenu.valueOf((Integer) map.get("ismenu")));
    }

}
