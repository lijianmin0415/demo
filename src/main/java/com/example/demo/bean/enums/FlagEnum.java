package com.example.demo.bean.enums;

public enum FlagEnum {
    ZERO(0),
    ONE(1)
    ;
    private Integer flag;

    public Integer getFlag() {
        return flag;
    }

    FlagEnum(Integer flag) {
        this.flag = flag;
    }
}
