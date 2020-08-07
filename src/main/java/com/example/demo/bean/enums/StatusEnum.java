package com.example.demo.bean.enums;

public enum StatusEnum {
    SUCCESS("执行成功"),
    FAILED("执行失败"),
    ERROR("异常退出"),
    ING("执行中"),
    QUIT("任务终止"),
    RESET("已重置")
    ;
    private String status;

    public String getStatus() {
        return status;
    }
    StatusEnum(String status){
        this.status=status;
    }
}
