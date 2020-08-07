package com.example.demo.service;

import com.example.demo.bean.Reduction;
import com.example.demo.bean.entity.SysCollect;
import com.example.demo.bean.entity.SysUpload;
import com.example.demo.bean.enums.StatusEnum;
import com.example.demo.bean.vo.SysUploadVo;
import com.example.demo.mapper.SysCollectMapper;
import com.example.demo.mapper.SysUploadMapper;
import com.example.demo.utils.DBUtil;
import com.example.demo.utils.UUID;
import com.example.demo.utils.WrapperBeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UploadServiceImpl implements UploadService {
    @Resource
    private SysUploadMapper sysUploadMapper;
    @Override
    public List<SysUpload> queryAll() {
        return sysUploadMapper.selectAll();
    }

    @Override
    public void insert(SysUploadVo sysUploadVo) {
        sysUploadMapper.insert(WrapperBeanCopier.copyProperties(sysUploadVo,SysUpload.class));
    }

    @Override
    @Transactional
    public synchronized String getReducte(Reduction reduction, String ku, String uploadPath, String fileName) {
        //插入数据库
        String uuid= UUID.uuId();
        try {

            SysUploadVo sysUploadVo=new SysUploadVo(uuid, StatusEnum.ING.getStatus());
            insert(sysUploadVo);
            if (fileName.toLowerCase().endsWith("mdf")) {
                DBUtil.reductionMDF(reduction, ku, uploadPath, fileName);
            } else {
                DBUtil.reductionMsSql(reduction, ku, uploadPath, fileName, false);
            }
            //更新状态
            sysUploadMapper.updateStatus(uuid,StatusEnum.SUCCESS.getStatus());
            return uuid;
        }catch (Exception e){
            e.printStackTrace();
            //更新状态
            sysUploadMapper.updateStatus(uuid,StatusEnum.FAILED.getStatus());
            return null;
        }

    }

    @Override
    public void updateStatus(String status, String tarStatus) {
        sysUploadMapper.updateErrorStatus(status,tarStatus);
    }
}
