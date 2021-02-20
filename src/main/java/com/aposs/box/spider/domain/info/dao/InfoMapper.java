package com.aposs.box.spider.domain.info.dao;

import com.aposs.box.spider.config.MyMapper;
import com.aposs.box.spider.domain.info.entity.SysInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface InfoMapper extends MyMapper<SysInformation> {

    @Select("select * from sys_information where id =#{id}")
    SysInformation selectById(@Param("id") Long id);
}
