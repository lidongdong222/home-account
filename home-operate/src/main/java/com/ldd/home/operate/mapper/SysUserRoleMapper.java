package com.ldd.home.operate.mapper;

import com.ldd.home.operate.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户角色列表信息表 Mapper 接口
 * </p>
 *
 * @author ldd
 * @since 2024-09-09
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    void insertBatch(@Param("userRoles") List<SysUserRole> userRoles);
}
