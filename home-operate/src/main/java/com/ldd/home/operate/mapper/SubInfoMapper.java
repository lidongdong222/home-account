package com.ldd.home.operate.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldd.home.operate.entity.SubInfo;
import com.ldd.home.operate.entity.req.SubInfoReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubInfoMapper extends BaseMapper<SubInfo> {

    Page<SubInfo> getSubInfoPage(@Param("page")Page<Object> objectPage, @Param("req") SubInfoReq req);

    List<SubInfo> getSubInfoList(@Param("start") int start, @Param("end") int end, @Param("req") SubInfoReq req);
}
