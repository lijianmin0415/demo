package com.example.demo.service;

import com.example.demo.bean.Reduction;
import com.example.demo.bean.entity.SysUpload;
import com.example.demo.bean.vo.SysUploadVo;

import java.util.List;

public interface UploadService {
    List<SysUpload> queryAll();

    void insert(SysUploadVo sysUploadVo);

    String getReducte(Reduction reduction, String ku, String uploadPath, String fileName);

    void updateStatus(String status, String status1);
}
