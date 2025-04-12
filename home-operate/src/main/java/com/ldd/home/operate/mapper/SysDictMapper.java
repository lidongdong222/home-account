package com.ldd.home.operate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldd.home.operate.entity.SysDict;
import com.ldd.home.operate.entity.req.BaseReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 数据字典信息表 Mapper 接口
 * </p>
 *
 * @author ldd
 * @since 2024-03-14
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {

    Page getDictTypeList(Page page, @Param("req") BaseReq req);
}
