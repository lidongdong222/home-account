package com.ldd.home.operate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldd.home.operate.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author ldd
 * @since 2024-09-09
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    Page<SysUser> queryUserList(Page<Object> objectPage, @Param("mhcx") String mhcx);
    SysUser selectByUsercode(@Param("usercode") String usercode);

    int insertObtainId(SysUser user);
}
