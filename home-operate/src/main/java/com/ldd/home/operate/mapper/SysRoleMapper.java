package com.ldd.home.operate.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldd.home.operate.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author ldd
 * @since 2024-09-09
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    Page<SysRole> queryRoleList(Page<Object> objectPage, @Param("mhcx") String mhcx);
}
