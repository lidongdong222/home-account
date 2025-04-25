package com.ldd.home.operate.mapper;

import com.ldd.home.operate.entity.WxBillMatchSubjectRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 科目规则匹配表 Mapper 接口
 * </p>
 *
 * @author ldd
 * @since 2025-04-23
 */
@Mapper
public interface WxBillMatchSubjectRuleMapper extends BaseMapper<WxBillMatchSubjectRule> {

    void insertBatch(@Param("list")List<WxBillMatchSubjectRule> rules);

}
