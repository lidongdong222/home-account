package com.ldd.home.operate.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldd.home.operate.common.constant.ErrorMsgConst;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.enums.StatusEnum;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.utils.AESUtil;
import com.ldd.home.operate.common.utils.RegexUtil;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.config.security.v1.HomeContextHolderStrategy;
import com.ldd.home.operate.entity.SysPermission;
import com.ldd.home.operate.entity.SysRole;
import com.ldd.home.operate.entity.SysUser;
import com.ldd.home.operate.entity.SysUserRole;
import com.ldd.home.operate.entity.req.BaseReq;
import com.ldd.home.operate.entity.req.IdReq;
import com.ldd.home.operate.entity.req.PasswordReq;
import com.ldd.home.operate.mapper.SysPermissionMapper;
import com.ldd.home.operate.mapper.SysRoleMapper;
import com.ldd.home.operate.mapper.SysUserMapper;
import com.ldd.home.operate.mapper.SysUserRoleMapper;
import com.ldd.home.operate.service.ISysConfigService;
import com.ldd.home.operate.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author ldd
 * @since 2024-09-09
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    SysUserMapper userMapper;
    @Autowired
    SysRoleMapper roleMapper;
    @Autowired
    SysPermissionMapper permissionMapper;

    @Autowired
    SysUserRoleMapper userRoleMapper;

    @Autowired
    ISysConfigService sysConfigService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Result getUserList(BaseReq req) {
        Page<SysUser> page = userMapper.queryUserList(new Page<>(req.getPageNum(),req.getPageSize()),req.getMhcx());
        page.getRecords().forEach(t->{
            if(StrUtil.isNotEmpty(t.getViewRoleIds())){
                t.setRoleIds(Arrays.stream(t.getViewRoleIds().split(",")).toList());
            }
            t.setStatusDesc(Objects.equals(StatusEnum.EFFECTIVE.getCode(),t.getStatus())?"启用":"停用");
        });
        return Result.successPage("查询成功！",page.getTotal(),page.getRecords());
    }

    @Override
    public Result addUser(SysUser user) throws Exception {
        List repeat = userMapper.selectList(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUsercode,user.getUsercode()));
        if(repeat!=null && repeat.size()>0){
            throw new BusinessException("用户编码已存在，请勿重复新增");
        }

        //保存用户
        String defaultPassword = sysConfigService.getConfigValue("SYS_DEFAULT_USER_PD");
        user.setSalt(AESUtil.generateAESKey());
        user.setPassword(passwordEncoder.encode(defaultPassword+user.getSalt()));
        user.setStatus(StatusEnum.EFFECTIVE.getCode());
        int r = userMapper.insertObtainId(user);
        if(r != 1) throw new BusinessException(ErrorMsgConst.DATA_ERROR);

        //保存用户角色
        List<SysUserRole> userRoles = new ArrayList<>();
        for (String roleId : user.getRoleIds()) {
            userRoles.add(SysUserRole.builder().userId(user.getUserId()).roleId(StrUtil.toInt(roleId)).build());
        }
        userRoleMapper.insertBatch(userRoles);
        return Result.success("新增成功！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updUser(SysUser user) throws BusinessException {
        user.setSalt(null);
        user.setPassword(null);
        int r = userMapper.updateById(user);
        if(r != 1) throw new BusinessException(ErrorMsgConst.DATA_ERROR);

        List<SysUserRole> userRoles = new ArrayList<>();
        for (String roleId : user.getRoleIds()) {
            userRoles.add(SysUserRole.builder().userId(user.getUserId()).roleId(StrUtil.toInt(roleId)).build());
        }
        userRoleMapper.delete(Wrappers.lambdaQuery(SysUserRole.class)
                .eq(SysUserRole::getUserId,user.getUserId()));
        userRoleMapper.insertBatch(userRoles);
        return Result.success("操作成功！");
    }

    @Override
    public Result delUser(IdReq req) throws BusinessException {
        int r = userMapper.deleteById(req.getId());
        if(r != 1) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        userRoleMapper.delete(Wrappers.lambdaQuery(SysUserRole.class)
                .eq(SysUserRole::getUserId,req.getId()));
        return Result.success("删除成功！");
    }

    @Override
    public Result getRoleList(BaseReq req) {
        Page<SysRole> page = roleMapper.queryRoleList(
                new Page<>(req.getPageNum(),req.getPageSize()),
                req.getMhcx()
        );
        page.getRecords().forEach(t->{
            t.setStatusDesc(Objects.equals(StatusEnum.EFFECTIVE.getCode(),t.getStatus())?"启用":"停用");
        });
        return Result.successPage("查询成功！",page.getTotal(),page.getRecords());
    }

    @Override
    public Result addRole(SysRole role) throws BusinessException {
        List<SysRole> repeat = roleMapper.selectList(Wrappers.lambdaQuery(SysRole.class).eq(SysRole::getRoleCode,role.getRoleCode()));
        if(repeat!=null && repeat.size()>0){
            throw new BusinessException("角色编码已存在，请修改后重试！");
        }
        role.setStatus(StatusEnum.EFFECTIVE.getCode());
        int r = roleMapper.insert(role);
        if(r != 1) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        return Result.success("新增成功！");
    }

    @Override
    public Result updRole(SysRole role) throws BusinessException {
        if(Objects.equals(role.getStatus(),StatusEnum.INVALID.getCode())){
            List<SysUserRole> users = userRoleMapper.selectList(
                    Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getRoleId,role.getRoleId()));
            if(users!=null && users.size()>0){
                throw new BusinessException("");
            }
        }
        int r = roleMapper.updateById(role);
        if(r != 1) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        return Result.success("操作成功！");
    }

    @Override
    public Result delRole(IdReq req) throws BusinessException {
        int r = userMapper.deleteById(req.getId());
        if(r != 1) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        return Result.success("删除成功！");
    }

    @Override
    public Result getPermissionList(BaseReq req) {
        Page<SysPermission> page = permissionMapper.selectPage(
                new Page<>(req.getPageNum(),req.getPageSize()),
                Wrappers.lambdaQuery(SysPermission.class)
                        .eq(Objects.nonNull(req.getMhcx()),SysPermission::getPermCode,req.getMhcx())
                        .or(Objects.nonNull(req.getMhcx()),l->l.eq(SysPermission::getPermDesc,req.getMhcx()))
        );
        return Result.successPage("查询成功！",page.getTotal(),page.getRecords());
    }

    @Override
    public Result addPermission(SysPermission permission) throws BusinessException {
        int r = permissionMapper.insert(permission);
        if(r != 1) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        return Result.success("新增成功！");
    }

    @Override
    public Result updPermission(SysPermission permission) throws BusinessException {
        int r = permissionMapper.insert(permission);
        if(r != 1) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        return Result.success("新增成功！");
    }

    @Override
    public Result delPermission(IdReq req) throws BusinessException {
        int r = permissionMapper.deleteById(req.getId());
        if(r != 1) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        return Result.success("删除成功！");
    }

    @Override
    public UserDetails getUserByUsername(String usercode) {
        return userMapper.selectByUsercode(usercode);
    }

    @Override
    public Result updPassword(PasswordReq req) throws BusinessException {
        SysUser user = userMapper.selectById(HomeContextHolderStrategy.getUser().getUserId());
        if(!passwordEncoder.matches(req.getOldPass()+user.getSalt(),user.getPassword())){
            throw new BusinessException("您输入的旧密码有误，请重新输入！");
        }
        boolean chkInvalid=true;
        if(!req.getNewPass().matches(RegexUtil.REGEX_LOGIN_PASSWORD)){
            chkInvalid = false;
        }
        if(!req.getNewPass().matches(".*[0-9]+.*")){
            chkInvalid = false;
        }
        if(!req.getNewPass().matches(".*[a-zA-Z]+.*")){
            chkInvalid = false;
        }
        if(!req.getNewPass().matches(".*[!@#\\$%\\^&*()_+\\-=\\{\\}\\[\\]:\";'\\,\\.\\?/\\\\\\|]+.*")){
            chkInvalid = false;
        }
        if(!chkInvalid){
            throw new BusinessException("密码需要6-20位的包含数字字母和特殊符号的组合！");
        }
        userMapper.updateById(SysUser.builder()
                .userId(user.getUserId())
                .password(passwordEncoder.encode(req.getNewPass()+user.getSalt())).build());
        return Result.success("修改成功！");
    }
}
