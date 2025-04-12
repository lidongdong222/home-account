package com.ldd.home.operate.mapper;

import com.ldd.home.operate.common.entity.ResolveCount;
import com.ldd.home.operate.entity.PrivateAccInfoTmp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 科目流水信息临时导入表 Mapper 接口
 * </p>
 *
 * @author ldd
 * @since 2024-08-06
 */
@Mapper
public interface PrivateAccInfoTmpMapper extends BaseMapper<PrivateAccInfoTmp> {

    int insertBatch(@Param("list") List<PrivateAccInfoTmp> cachedDataList);

    void updateBatch(@Param("list") List<PrivateAccInfoTmp> tmps);

    ResolveCount selectResolveCount(@Param("impId") Long impId);
}
