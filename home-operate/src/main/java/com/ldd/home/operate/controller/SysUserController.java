package com.ldd.home.operate.controller;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.ext.Add;
import com.ldd.home.operate.common.ext.Page;
import com.ldd.home.operate.common.ext.Upd;
import com.ldd.home.operate.entity.SysPermission;
import com.ldd.home.operate.entity.SysRole;
import com.ldd.home.operate.entity.SysUser;
import com.ldd.home.operate.entity.req.BaseReq;
import com.ldd.home.operate.entity.req.IdReq;
import com.ldd.home.operate.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author ldd
 * @since 2024-09-09
 */
@RestController
@Slf4j
@RequestMapping("/sys/User")
public class SysUserController {

    final String regex_usercode = "\\S{3,20}";

    @Autowired
    ISysUserService userService;

    @PostMapping("getUserList")
    public Result getUserList(@RequestBody @Validated(Page.class) BaseReq req) {
        log.info("getUserList 入参：{}", req);
        Result r = userService.getUserList(req);
        log.info("getUserList 出参：{}", r);
        return r;
    }

    @PostMapping("addUser")
    public Result addUser(@RequestBody @Validated(Add.class) SysUser user) throws Exception {
        log.info("addUser 入参：{}", user);
        if(!user.getUsercode().matches(regex_usercode)){
            throw new BusinessException("用户账号为3~20位字符，不含空格");
        }
        Result r = userService.addUser(user);
        log.info("addUser 出参：{}", r);
        return r;
    }

    @PostMapping("updUser")
    public Result updUser(@RequestBody @Validated(Upd.class) SysUser user) throws BusinessException {
        log.info("updUser 入参：{}", user);
        Result r = userService.updUser(user);
        log.info("updUser 出参：{}", r);
        return r;
    }

    @PostMapping("delUser")
    public Result delUser(@RequestBody @Validated IdReq req) throws BusinessException {
        log.info("delUser 入参：{}", req);
        Result r = userService.delUser(req);
        log.info("delUser 出参：{}", r);
        return r;
    }

    @PostMapping("getRoleList")
    public Result getRoleList(@RequestBody @Validated(Page.class) BaseReq req) {
        log.info("getRoleList 入参：{}", req);
        Result r = userService.getRoleList(req);
        log.info("getRoleList 出参：{}", r);
        return r;
    }

    @PostMapping("addRole")
    public Result addRole(@RequestBody @Validated(Add.class) SysRole req) throws BusinessException {
        log.info("addRole 入参：{}", req);
        Result r = userService.addRole(req);
        log.info("addRole 出参：{}", r);
        return r;
    }

    @PostMapping("updRole")
    public Result updRole(@RequestBody @Validated(Upd.class) SysRole req) throws Exception {
        log.info("updRole 入参：{}", req);
        Result r = userService.updRole(req);
        log.info("updRole 出参：{}", r);
        return r;
    }

    @PostMapping("delRole")
    public Result delRole(@RequestBody @Validated IdReq req) throws Exception {
        log.info("delRole 入参：{}", req);
        Result r = userService.delRole(req);
        log.info("delRole 出参：{}", r);
        return r;
    }

    @PostMapping("getPermissionList")
    public Result getPermissionList(@RequestBody @Validated(Page.class) BaseReq req) throws Exception {
        log.info("getPermissionList 入参：{}", req);
        Result r = userService.getPermissionList(req);
        log.info("getPermissionList 出参：{}", r);
        return r;
    }

    @PostMapping("addPermission")
    public Result addPermission(@RequestBody @Validated(Add.class) SysPermission req) throws Exception {
        log.info("addPermission 入参：{}", req);
        Result r = userService.addPermission(req);
        log.info("addPermission 出参：{}", r);
        return r;
    }

    @PostMapping("updPermission")
    public Result updPermission(@RequestBody @Validated(Upd.class) SysPermission req) throws Exception {
        log.info("updPermission 入参：{}", req);
        Result r = userService.updPermission(req);
        log.info("updPermission 出参：{}", r);
        return r;
    }

    @PostMapping("delPermission")
    public Result delPermission(@RequestBody @Validated IdReq req) throws Exception {
        log.info("delPermission 入参：{}", req);
        Result r = userService.delPermission(req);
        log.info("delPermission 出参：{}", r);
        return r;
    }


}
