package com.source.base.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PK implements Serializable {

    @TableId(value="id", type= IdType.AUTO)
    private Long id;

    public PK() {

    }

    public PK(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
