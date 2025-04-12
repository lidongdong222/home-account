package com.ldd.home.operate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.entity.SysMenu;
import com.ldd.home.operate.entity.req.IdReq;
import com.ldd.home.operate.entity.req.SortMenuReq;
import com.ldd.home.operate.mapper.SysMenuMapper;
import com.ldd.home.operate.service.ISysMenuService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.ldd.home.operate.common.constant.ErrorMsgConst.DATA_ERROR;
import static com.ldd.home.operate.common.constant.ErrorMsgConst.SYSTEM_BUSY_ERROR;
import static com.ldd.home.operate.config.redis.RedisLockConstant.COMMON_RLOCK_EXPIRE_TIME_SECONDS;
import static com.ldd.home.operate.config.redis.RedisLockConstant.COMMON_RLOCK_WAIT_TIME_SECONDS;
import static com.ldd.home.operate.config.redis.RedisLockConstant.RLOCK_HOME_SYS_MENU_GET_SORT_ID;
import static com.ldd.home.operate.config.redis.RedisLockConstant.RLOCK_HOME_SYS_MENU_SORT;

/**
 * <p>
 * 菜单信息表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2024-01-31
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    SysMenuMapper sysMenuMapper;

    @Autowired
    RedissonClient redissonClient;

    /**
     *
     * @return
     */
    @Override
    public Result getMenuList(String mhcx) {
        List menuTree = new ArrayList<>();
        List<SysMenu> sysMenus;
//        if(StrUtil.isNotEmpty(mhcx)){
//            sysMenus = sysMenuMapper.getMenuListMhcx(mhcx).stream().peek(m->m.setStatusDesc(MenuStatusDesc.getDesc(m.getStatus()))).toList();
//        }else{
            LambdaQueryWrapper<SysMenu> queryWrapper =
                    new LambdaQueryWrapper<SysMenu>()
//                            .like(!StrUtil.isEmpty(mhcx),SysMenu::getMenuName,mhcx)
                            .eq(SysMenu::getStatus,"1")
                            .orderByAsc(SysMenu::getSort);
            sysMenus = sysMenuMapper.selectList(
                    queryWrapper).stream().peek(m->m.setStatusDesc(MenuStatusDesc.getDesc(m.getStatus()))).toList();
//        }
        for(SysMenu menu:sysMenus){
            if(Objects.equals(0,menu.getParentId())) menuTree.add(menu);
            for (SysMenu menu1:sysMenus){
                if(menu1.getParentId() .equals(menu.getMenuId())){
                    if(Objects.isNull(menu.getChildren())) menu.setChildren(new ArrayList<>());
                    menu.getChildren().add(menu1);
                }
            }
        }
        return Result.successList("查询成功",menuTree);
    }

    @Override
    public Result addMenu(SysMenu sysMenu) throws BusinessException {
        sysMenu.setMenuId(null);
        List<SysMenu> checkRepeat =
                sysMenuMapper.selectList(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getMenuCode,sysMenu.getMenuCode()));
        if(checkRepeat.size()>0) throw new BusinessException("菜单编码重复，请核对后再次操作！");
        RLock lock = redissonClient.getLock(RLOCK_HOME_SYS_MENU_GET_SORT_ID);
        try {
            if(lock.tryLock(COMMON_RLOCK_WAIT_TIME_SECONDS,
                    COMMON_RLOCK_EXPIRE_TIME_SECONDS,
                    TimeUnit.SECONDS)){
                sysMenu.setSort(sysMenuMapper.getSortId());
            }
        } catch (InterruptedException e) {
            throw new BusinessException(SYSTEM_BUSY_ERROR);
        }finally {
            if(lock.isLocked()) lock.unlock();
        }

        int row = sysMenuMapper.insert(sysMenu);
        if(!Objects.equals(row,1)) throw new BusinessException(DATA_ERROR);
        return Result.success("操作成功！");
    }

    @Override
    public Result updMenu(SysMenu sysMenu) throws BusinessException {
        sysMenu.setMenuCode(null);
        int row = sysMenuMapper.updateById(sysMenu);
        if(!Objects.equals(row,1)) throw new BusinessException(DATA_ERROR);
        return Result.success("操作成功！");
    }

    @Override
    public Result delMenu(IdReq idReq) throws BusinessException {
        //TODO 删除时的权限删除
        checkHasChild(idReq.getId());
        int row = sysMenuMapper.deleteById(SysMenu.builder().menuId(StrUtil.toInt(idReq.getId())).build());
        if(!Objects.equals(row,1)) throw new BusinessException(DATA_ERROR);
        return Result.success("操作成功！");
    }

    /**
     * 菜单排序，上移、下移
     */
    @Override
    public Result sortMenu(SortMenuReq req) throws BusinessException {
        SysMenu menu = sysMenuMapper.selectById(req.getMenuId());
        RLock lock = redissonClient.getLock(RLOCK_HOME_SYS_MENU_SORT);
        try {
            if(lock.tryLock(COMMON_RLOCK_WAIT_TIME_SECONDS,
                    COMMON_RLOCK_EXPIRE_TIME_SECONDS,
                    TimeUnit.SECONDS)){
                SysMenu changeMenu = sysMenuMapper.selectOne(Wrappers.lambdaQuery(SysMenu.class)
                        .eq(SysMenu::getParentId,menu.getParentId())
                        .last(" and sort "+(Objects.equals(1,req.getSortType())?" < ":" > ")+menu.getSort()+
                                " order by sort "+(Objects.equals(1,req.getSortType())?" desc ":" asc ")+" limit 1"));
                if(Objects.isNull(changeMenu)) throw new BusinessException(DATA_ERROR);
                sysMenuMapper.updateById(SysMenu.builder().menuId(menu.getMenuId()).sort(changeMenu.getSort()).build());
                sysMenuMapper.updateById(SysMenu.builder().menuId(changeMenu.getMenuId()).sort(menu.getSort()).build());
            }
        } catch (InterruptedException e) {
            throw new BusinessException(SYSTEM_BUSY_ERROR);
        }finally {
            if(lock.isLocked()) lock.unlock();
        }
        return Result.success("操作成功！");
    }

    /**
     * 检查删除菜单是否含有子级菜单，有则不能删除
     */
    private void checkHasChild(String id) throws BusinessException {
        List childs = sysMenuMapper.selectList(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId,id));
        if(childs.size()>0) throw new BusinessException("删除菜单含有子级菜单，请先删除子级菜单！");
    }

    enum MenuStatusDesc{
        ENABLE("1","启用"),
        DISABLE("0","停用");

        String status;
        String statusDesc;

        MenuStatusDesc(String status,String statusDesc){
            this.status = status;
            this.statusDesc = statusDesc;
        }

        public static String getDesc(String status){
            for (MenuStatusDesc value : MenuStatusDesc.values()) {
                if(value.status.equals(status)) return value.statusDesc;
            }
            return null;
        }
    }
}

