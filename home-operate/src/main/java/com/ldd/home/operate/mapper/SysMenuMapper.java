package com.ldd.home.operate.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.ldd.home.operate.entity.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单信息表 Mapper 接口
 * </p>
 *
 * @author ldd
 * @since 2024-01-31
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 模糊查询 通过子节点查到所有父节点
     * @param mhcx
     * @return
     */
    List<SysMenu> getMenuListMhcx(String mhcx);

    /**
     * 获取最大的sortId
     * @return
     */
    Integer getSortId();
}
