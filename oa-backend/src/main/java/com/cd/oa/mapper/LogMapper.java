package com.cd.oa.mapper;

import com.cd.oa.entity.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogMapper {

    int insert(Log log);

    int deleteById(Integer id);

    Log selectById(Integer id);

    List<Log> selectAll();

}
