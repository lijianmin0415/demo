package com.example.demo.config;

import com.example.demo.bean.entity.SysUpload;
import com.example.demo.bean.enums.StatusEnum;
import com.example.demo.service.KattleService;
import com.example.demo.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CheckStatusConfig {
    @Autowired
    private KattleService kattleService;
    @Autowired
    private UploadService uploadService;

    @PostConstruct
    public void run() {
        List<String> status = kattleService.getCollectList().stream().filter(t->t.getStatus()!=null).map(t -> t.getStatus()).filter(t -> t.equals(StatusEnum.ING.getStatus())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(status)) {
        kattleService.updateErrorStatus(StatusEnum.ING.getStatus(),StatusEnum.ERROR.getStatus());

        }
    }

    @PostConstruct
    public void runUpload(){
        List<SysUpload> sysUploads = uploadService.queryAll();
        if (!CollectionUtils.isEmpty(sysUploads)) {
            List<String> status = sysUploads.stream().filter(t -> t.getStatus() != null).map(t -> t.getStatus()).filter(t -> t.equals(StatusEnum.ING.getStatus())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(status)) {
                uploadService.updateStatus(StatusEnum.ING.getStatus(),StatusEnum.ERROR.getStatus());
            }
        }
    }
}
