package com.example.demo.bean.vo;

import lombok.Data;

@Data
public class SysCollectVo {
    //任务显示名字
    private String name;
    //当前任务状态
    private String status;
    //给前端的标志0是可执行1是置灰
    private String flag;
    //job名字
    private String jobName;
    //job路径
    private String jobPath;
}
