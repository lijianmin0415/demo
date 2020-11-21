package com.example.demo.mapper;

import com.example.demo.bean.BaseMapper;
import com.example.demo.bean.entity.SysCollect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

public interface SysCollectMapper extends BaseMapper<SysCollect,String> {

    void updateByName(@Param("name") String name,@Param("success") String success,@Param("flag")Integer flag,@Param("oneFlag") Integer oneFlag);

    void updateStatus(@Param("name") String name,@Param("success") String success,@Param("flag") Integer flag);

    void updateErrorStatus(@Param("ingStatus") String ingStatus,@Param("errorStatus") String errorStatus);
}
