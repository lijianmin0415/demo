package com.example.demo.bean;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * 通用基础 Mapper 接口，其他 mapper 接口都继承此接口 <br/>
 * 1、其中 Mapper 接口提供基本的增删查改操作方法集合，IdListMapper 接口提供根据 id 集合批量查询和删除操作的方法集合，InsertListMapper
 * 提供批量插入方法<br/>
 * 2、简单的操作可使用 Mapper 接口提供的方法和注解来实现数据库访问，复杂的需要写 SQL 的操作使用 xxxMapper.xml 中写 SQL 方式实现<br/>
 * 注意：此接口不能被扫描到
 * 
 * @author zhao.wang Mar 27, 2019
 *
 * @param <T>
 */
public interface BaseMapper<T, PK> extends Mapper<T>, IdListMapper<T, PK>, InsertListMapper<T> {

}
