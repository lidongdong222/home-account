package com.ldd.home.operate.service;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.entity.SysPermission;
import com.ldd.home.operate.entity.SysRole;
import com.ldd.home.operate.entity.SysUser;
import com.ldd.home.operate.entity.req.BaseReq;
import com.ldd.home.operate.entity.req.IdReq;
import com.ldd.home.operate.entity.req.PasswordReq;
import org.springframework.security.core.userdetails.UserDetails;

public interface ISysUserService{

    Result getUserList(BaseReq req);

    Result addUser(SysUser user) throws Exception;

    Result updUser(SysUser user) throws BusinessException;

    Result delUser(IdReq req) throws BusinessException;

    Result getRoleList(BaseReq req);

    Result addRole(SysRole role) throws BusinessException;

    Result updRole(SysRole role) throws BusinessException;

    Result delRole(IdReq req) throws BusinessException;

    Result getPermissionList(BaseReq req);

    Result addPermission(SysPermission permission) throws BusinessException;

    Result updPermission(SysPermission permission) throws BusinessException;

    Result delPermission(IdReq req) throws BusinessException;

    UserDetails getUserByUsername(String username);

    Result updPassword(PasswordReq req) throws BusinessException;
}