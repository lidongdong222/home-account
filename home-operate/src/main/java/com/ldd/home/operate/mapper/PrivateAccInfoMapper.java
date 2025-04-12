package com.ldd.home.operate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldd.home.operate.entity.PrivateAccInfo;
import com.ldd.home.operate.entity.req.AccInfoReq;
import com.ldd.home.operate.entity.tmp.AccountGather;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 科目流水信息表 Mapper 接口
 * </p>
 *
 * @author ldd
 * @since 2024-03-21
 */
@Mapper
public interface PrivateAccInfoMapper extends BaseMapper<PrivateAccInfo> {

    Page<PrivateAccInfo> getAccountList(Page page, @Param("req")AccInfoReq req);

    AccountGather getCurrentYearGather(@Param("year") int year);

    void insertBatch(@Param("list")List succList);

    List<PrivateAccInfo> getStatisticsByMonths(@Param("yearMonths")List months);

    List<PrivateAccInfo> getExpenditureByYearMonth(@Param("year")Integer year,@Param("month") Integer month);
}
