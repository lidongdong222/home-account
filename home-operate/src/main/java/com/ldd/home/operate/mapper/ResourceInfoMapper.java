package com.ldd.home.operate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldd.home.operate.entity.ResourceInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 资源信息表 Mapper 接口
 * </p>
 *
 * @author ldd
 * @since 2024-04-30
 */
@Mapper
public interface ResourceInfoMapper extends BaseMapper<ResourceInfo> {

//    插入数据是获取主键id
    int insertObtainId(ResourceInfo build);
}
