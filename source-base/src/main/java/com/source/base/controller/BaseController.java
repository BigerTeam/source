package com.source.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.source.base.common.Const;
import com.source.base.model.request.PageRequest;
import com.source.base.model.vo.DataGrid;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public abstract class BaseController {

    /**
     * 获取保存在Session中的用户对象
     * @param request
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
	protected <T> T getSessionUser(HttpServletRequest request) {
        return (T) request.getSession().getAttribute(Const.SESSION_USER);
    }


    public List<String> getGlobalErrors(BindingResult bindingResult) {
        List<String> globalErrors = new ArrayList<String>();
        for (ObjectError oe : bindingResult.getGlobalErrors()) {
            globalErrors.add(oe.getDefaultMessage());
        }
        return globalErrors;
    }

    public Map<String, List<String>> getFieldErros(BindingResult bindingResult) {
        Map<String, List<String>> fieldErrors = new HashMap<String, List<String>>();
        for (FieldError fe : bindingResult.getFieldErrors()) {
            String f = fe.getField();

            if (fieldErrors.get(f) != null) {
                fieldErrors.get(f).add(fe.getDefaultMessage());
            } else {
                List<String> list = new LinkedList<String>();
                list.add(fe.getDefaultMessage());
                fieldErrors.put(f, list);
            }
        }
        return fieldErrors;
    }

    public String getValidateErrorMessage(BindingResult bindingResult) {
        String error = JSONObject.toJSONString(getFieldErros(bindingResult));
        return error;
    }

    /**
     * 构建DataGrid.
     *
     * @param page
     * @return
     */
    public <T> DataGrid buildDataGrid(Page<T> page) {
        DataGrid dataGrid = new DataGrid();
        dataGrid.setRows(page.getRecords());
        dataGrid.setTotal(page.getTotal());
        return dataGrid;
    }

    /**
     * 构建DataGrid.
     *
     * @param rows
     * @param total
     * @return
     */
    public <T> DataGrid buildDataGrid(List<T> rows, Integer total) {
        DataGrid dataGrid = new DataGrid();
        dataGrid.setRows(rows);
        dataGrid.setTotal(total);
        return dataGrid;
    }

    protected <T> Page<T> getPagination(PageRequest pageRequest) {
        Page<T> page =  new Page<T>(pageRequest.getPage(), pageRequest.getRows());
        page.setAsc("asc".equalsIgnoreCase(pageRequest.getOrder()));//升序 降序
        page.setOrderByField(pageRequest.getSort());//排序字段名称
        return page;
    }
    
    
    
}
