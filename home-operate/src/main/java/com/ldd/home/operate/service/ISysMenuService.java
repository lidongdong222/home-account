package com.ldd.home.operate.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.entity.SysMenu;
import com.ldd.home.operate.entity.req.IdReq;
import com.ldd.home.operate.entity.req.SortMenuReq;

/**
 * <p>
 * 菜单信息表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2024-01-31
 */
public interface ISysMenuService extends IService<SysMenu> {

    Result getMenuList(String menuReq);

    Result addMenu(SysMenu sysMenu) throws BusinessException;

    Result updMenu(SysMenu sysMenu) throws BusinessException;

    Result delMenu(IdReq idReq) throws BusinessException;

    Result sortMenu(SortMenuReq idReq) throws BusinessException;
}
