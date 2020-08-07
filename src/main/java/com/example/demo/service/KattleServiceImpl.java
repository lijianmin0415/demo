package com.example.demo.service;

import com.example.demo.bean.entity.SysCollect;
import com.example.demo.bean.enums.FlagEnum;
import com.example.demo.bean.enums.StatusEnum;
import com.example.demo.mapper.SysCollectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class KattleServiceImpl implements KattleService {
    @Resource
    private SysCollectMapper sysCollectMapper;
    @Override
    public List<SysCollect> getCollectList() {
        return sysCollectMapper.selectAll();
    }

    @Override
    public void resetStatus(String names) {
        Arrays.asList(names.split(",")).forEach(t->{
            sysCollectMapper.updateByName(t, StatusEnum.RESET.getStatus(), FlagEnum.ZERO.getFlag(),FlagEnum.ONE.getFlag());
        });
    }

    @Override
    public void updateStatus(String jobName, String success, Integer one) {
        sysCollectMapper.updateStatus(jobName,success,one);
    }

    @Override
    public void updateErrorStatus(String ingStatus, String errorStatus) {
        sysCollectMapper.updateErrorStatus(ingStatus,errorStatus);
    }
}
