package com.example.demo.mapper;

import com.example.demo.bean.BaseMapper;
import com.example.demo.bean.entity.SysUpload;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUploadMapper extends BaseMapper<SysUpload,String> {

    void updateStatus(@Param("uuid") String uuid,@Param("status") String status);

    void updateErrorStatus(@Param("status") String status, @Param("tarStatus") String tarStatus);
}
