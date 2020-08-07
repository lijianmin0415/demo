package com.example.demo.Controller;

import com.example.demo.bean.entity.SysCollect;
import com.example.demo.bean.enums.FlagEnum;
import com.example.demo.bean.enums.StatusEnum;
import com.example.demo.bean.vo.SysCollectVo;
import com.example.demo.service.KattleService;
import com.example.demo.utils.KattleUtil;
import com.example.demo.utils.SQApiResponse;
import com.example.demo.utils.WrapperBeanCopier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class KattleController {
    @Autowired
    private KattleService kattleService;

    @PostMapping("/list")
    public SQApiResponse<List<SysCollectVo>> getCollectList(){

        List<SysCollect> sysCollects=kattleService.getCollectList();
        return SQApiResponse.success(WrapperBeanCopier.copyPropertiesOfList(sysCollects,SysCollectVo.class));

    }

    @PostMapping("/reset")
    public SQApiResponse<String> resetStatus(@RequestParam("names") String names){
        try {
            kattleService.resetStatus(names);
            return SQApiResponse.success("重置成功");
        }catch (Exception e){
            e.printStackTrace();
            log.error("【重置失败，方法:resetStatus,入参names={}】",names);
        }
        return SQApiResponse.error("重置失败");
    }

    @PostMapping("/kattle")
    public SQApiResponse<String> kattleWord(@RequestParam("jobName") String jobName, @RequestParam(value = "jobPath",required = false) String jobPath) {
        try {
            kattleService.updateStatus(jobName,StatusEnum.ING.getStatus(),FlagEnum.ZERO.getFlag());
            boolean runJob = KattleUtil.runJob(KattleUtil.RepositoryCon(), jobName, jobPath);
            if (runJob) {
                log.info("【采集入库成功】");
                //更新执行状态
                kattleService.updateStatus(jobName, StatusEnum.SUCCESS.getStatus(), FlagEnum.ONE.getFlag());
                return SQApiResponse.success("采集入库成功");
            }
        } catch (Exception e) {
            kattleService.updateStatus(jobName,StatusEnum.FAILED.getStatus(),FlagEnum.ZERO.getFlag());
            e.printStackTrace();
        }

        return SQApiResponse.error("100000","任务执行失败",StatusEnum.FAILED.getStatus());
    }


}
