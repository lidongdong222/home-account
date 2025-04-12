package com.ldd.home.operate.mapper;

import com.ldd.home.operate.entity.WxBill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 微信账单表 Mapper 接口
 * </p>
 *
 * @author ldd
 * @since 2024-10-31
 */
@Mapper
public interface WxBillMapper extends BaseMapper<WxBill> {

    void insertBatch(@Param("wxBillList") List<WxBill> wxBillList);
}
