package com.example.demo.bean.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "SYS_COLLECT")
public class SysCollect {
    @Id
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
