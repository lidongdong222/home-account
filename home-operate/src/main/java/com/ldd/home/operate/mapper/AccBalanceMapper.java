package com.ldd.home.operate.mapper;

import com.ldd.home.operate.entity.AccBalance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 账务余额表 Mapper 接口
 * </p>
 *
 * @author ldd
 * @since 2024-08-20
 */
@Mapper
public interface AccBalanceMapper extends BaseMapper<AccBalance> {

    void insertBatch(@Param("list") List<AccBalance> accInfoList);

    void deleteByPeriodType(@Param("periodType") String periodType);
}
