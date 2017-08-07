package com.source.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;

import java.util.Date;

@SuppressWarnings("serial")
public class BaseEntity extends PK {

    /**
     * 创建时间
     */
    @TableField("create_time")
    protected Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    protected Date updateTime;


    public BaseEntity() {
        super();
    }

    public BaseEntity(Long id) {
        super(id);
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
