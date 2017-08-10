package com.source.base.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.source.utils.BeanCopier;

public abstract class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Page convert(Page source, Class destinationClass) {
        List result = BeanCopier.copy(source.getRecords(), destinationClass);
        source.setRecords(result);
        return source;
    }
}
