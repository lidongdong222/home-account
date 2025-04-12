package com.ldd.home.operate.controller;

import com.alibaba.fastjson2.JSON;
import com.ldd.home.operate.common.constant.ErrorMsgConst;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.ext.Add;
import com.ldd.home.operate.common.ext.Upd;
import com.ldd.home.operate.entity.SysMenu;
import com.ldd.home.operate.entity.req.BaseReq;
import com.ldd.home.operate.entity.req.IdReq;
import com.ldd.home.operate.entity.req.SortMenuReq;
import com.ldd.home.operate.service.ISysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * <p>
 * 菜单信息表 前端控制器
 * </p>
 *
 * @author ldd
 * @since 2024-01-31
 */
@RestController
@RequestMapping("/sysMenu")
@Slf4j
public class SysMenuController {

    @Autowired
    ISysMenuService menuService;

    @PostMapping("/getMenuList")
    public Result getMenuList(@RequestBody @Validated() BaseReq mhcx){
        return menuService.getMenuList(mhcx.getMhcx());
    }

    @PostMapping("/addMenu")
    public Result addMenu(@RequestBody @Validated(Add.class) SysMenu sysMenu) throws BusinessException {
        return menuService.addMenu(sysMenu);
    }

    @PostMapping("/updMenu")
    public Result updMenu(@RequestBody @Validated(Upd.class)SysMenu sysMenu) throws BusinessException {
        return menuService.updMenu(sysMenu);
    }

    @PostMapping("/delMenu")
    public Result delMenu(@RequestBody IdReq idReq) throws BusinessException {
        return menuService.delMenu(idReq);
    }

    @PostMapping("/sortMenu")
    public Result sortMenu(@RequestBody @Validated SortMenuReq req) throws BusinessException {
        log.info("{}.sortMenu入参:{}",getClass().getName(), JSON.toJSONString(req));
        if(Objects.equals(1,req.getSortType()) && Objects.equals(2,req.getSortType())){
            throw new BusinessException(ErrorMsgConst.PARAMS_DELETION_ERROR);
        }
        Result result= menuService.sortMenu(req);
        log.info("{}.sortMenu出参:{}",getClass().getName(), JSON.toJSONString(result));
        return result;
    }



}
