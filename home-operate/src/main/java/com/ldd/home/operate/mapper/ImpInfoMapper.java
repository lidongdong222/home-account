package com.ldd.home.operate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldd.home.operate.entity.ImpInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 导入信息表 Mapper 接口
 * </p>
 *
 * @author ldd
 * @since 2024-04-30
 */
@Mapper
public interface ImpInfoMapper extends BaseMapper<ImpInfo> {
    //插入数据后获取主键信息
    void insertObtainId(ImpInfo build);

}
