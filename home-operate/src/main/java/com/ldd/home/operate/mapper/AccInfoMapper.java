package com.ldd.home.operate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldd.home.operate.entity.AccBalance;
import com.ldd.home.operate.entity.AccInfo;
import com.ldd.home.operate.entity.req.AccInfoReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 科目流水信息表 Mapper 接口
 * </p>
 *
 * @author ldd
 * @since 2024-09-11
 */
@Mapper
public interface AccInfoMapper extends BaseMapper<AccInfo> {

    Page<AccInfo> getAccountPage(@Param("page")Page page, @Param("req") AccInfoReq req);

    List<AccInfo> getAccountList(@Param("start") int start,@Param("end") int end,@Param("req") AccInfoReq req);
    List<AccInfo> getRepeat(AccInfo req);

    List<AccBalance> getAccMonthSummaryInfo(String year);

    void insertBatch(@Param("accInfos") List<AccInfo> accInfos);

    Map getAccountSummary(@Param("req") AccInfoReq req);
}
