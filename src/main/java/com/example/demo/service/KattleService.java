package com.example.demo.service;

import com.example.demo.bean.entity.SysCollect;
import com.example.demo.bean.enums.FlagEnum;
import com.example.demo.bean.enums.StatusEnum;

import java.util.List;

public interface KattleService {
    List<SysCollect> getCollectList();

    void resetStatus(String names);

    void updateStatus(String jobName, String success, Integer one);

    void updateErrorStatus(String status, String status1);
}
