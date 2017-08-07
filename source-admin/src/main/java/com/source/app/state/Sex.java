package com.source.app.state;

/**
 * 性别
 */
public enum Sex {

    male(1, "男"), female(2, "女");

    private Integer code;
    private String des;

    Sex(Integer code, String des) {
        this.code = code;
        this.des = des;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (Sex rt : Sex.values()) {
                if (rt.getCode() == value) {
                    return rt.getDes();
                }
            }
            return "";
        }
    }
}
