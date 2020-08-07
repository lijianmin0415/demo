package com.example.demo.bean.enums;


public enum CollectEnum {
    LANG("浪潮"),
    DIE("金蝶"),
    WL("物流NC"),
    YD("缘达畅捷通"),
    HQ("联合外经宏桥")
    ;
    private String name;

    public String getName() {
        return name;
    }

    CollectEnum(String name) {
        this.name = name;
    }
}
