package com.ldd.home.operate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldd.home.operate.common.constant.ErrorMsgConst;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.config.redis.RedisKeyConstant;
import com.ldd.home.operate.entity.SysDict;
import com.ldd.home.operate.entity.SysDictDtl;
import com.ldd.home.operate.entity.req.BaseReq;
import com.ldd.home.operate.mapper.SysDictDtlMapper;
import com.ldd.home.operate.mapper.SysDictMapper;
import com.ldd.home.operate.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Service
public class SysDictServiceImpl implements ISysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;
    @Autowired
    private SysDictDtlMapper sysDictDtlMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Result getDictByType(SysDict req) {
        String redisKey = RedisKeyConstant.SYS_DICT+":"+req.getDictType();
        List list = null;
        if(redisTemplate.hasKey(req.getDictType())){
            list = (List) redisTemplate.opsForValue().get(redisKey);
        }else{
            list = sysDictDtlMapper.selectList(Wrappers.lambdaQuery(SysDictDtl.class)
                    .eq(SysDictDtl::getDictType,req.getDictType()));
            redisTemplate.opsForValue().set(redisKey,list, Duration.ofMinutes(RedisKeyConstant.COMMON_EXPIRE_TIME_SECONDS));
        }
        return Result.successList("查询成功",list);
    }

    @Override
    public List<SysDictDtl> getDictByType(String dictType) {
        String redisKey = RedisKeyConstant.SYS_DICT+":"+dictType;
        List list = null;
        if(redisTemplate.hasKey(dictType)){
            list = (List) redisTemplate.opsForValue().get(redisKey);
        }else{
            list = sysDictDtlMapper.selectList(Wrappers.lambdaQuery(SysDictDtl.class)
                    .eq(SysDictDtl::getDictType,dictType));
            redisTemplate.opsForValue().set(redisKey,list,Duration.ofMinutes(RedisKeyConstant.COMMON_EXPIRE_TIME_SECONDS));
        }
        return list;
    }

    @Override
    public Result getDictTypeList(BaseReq req) {
        Page<SysDict> page =  sysDictMapper.getDictTypeList(new Page(req.getPageNum(),req.getPageSize()),req);
        page.getRecords().forEach(d->d.setStatusName(Objects.equals("1",d.getStatus())?"启用":"禁用"));
        return Result.successPage("查询成功",page.getTotal(),page.getRecords());
    }

    @Override
    public Result addDictType(SysDict req) throws BusinessException {
        int r = sysDictMapper.insert(req);
        if(!Objects.equals(r,1))  throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        return Result.success("新增成功！");
    }

    @Override
    public Result updDictType(SysDict req) throws BusinessException {
        int r = sysDictMapper.updateById(req);
        if(!Objects.equals(r,1))  throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        return Result.success("更新成功！");
    }

    /**
     * 删除业务字典时需要谨慎操作，业务数据表中关联字典时会出现问题
      * @param id
     * @return
     */
    @Override
    public Result delDictType(String id) throws BusinessException {
        int r = sysDictMapper.updateById(SysDict.builder().dictType(id).status("0").build());
        if(!Objects.equals(r,1))  throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        return Result.success("删除成功！");
    }

    @Override
    public Result getDictDtlList(String dictType) {
        List<SysDictDtl> list = sysDictDtlMapper.selectList(
                new LambdaQueryWrapper<SysDictDtl>()
                        .eq(SysDictDtl::getDictType,dictType));
        list.forEach(d->d.setStatusName(Objects.equals("1",d.getStatus())?"启用":"禁用"));
        return Result.successList("查询成功",list);
    }

    @Override
    public Result addDictDtl(SysDictDtl req) throws BusinessException {
        String redisKey = RedisKeyConstant.SYS_DICT+":"+req.getDictType();
        int r = sysDictDtlMapper.insert(req);
        if(!Objects.equals(r,1))  throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        if(redisTemplate.hasKey(redisKey)){
            redisTemplate.opsForValue().set(redisKey,sysDictDtlMapper.selectList(Wrappers.lambdaQuery(SysDictDtl.class)
                    .eq(SysDictDtl::getDictType,req.getDictType())),Duration.ofMinutes(RedisKeyConstant.COMMON_EXPIRE_TIME_SECONDS));
        }
        return Result.success("新增成功！");
    }

    @Override
    public Result updDictDtl(SysDictDtl req) throws BusinessException {
        String redisKey = RedisKeyConstant.SYS_DICT+":"+req.getDictType();
        int r = sysDictDtlMapper.updateById(req);
        if(!Objects.equals(r,1))  throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        if(redisTemplate.hasKey(redisKey)){
            redisTemplate.opsForValue().set(redisKey,sysDictDtlMapper.selectList(Wrappers.lambdaQuery(SysDictDtl.class)
                    .eq(SysDictDtl::getDictType,req.getDictType())),Duration.ofMinutes(RedisKeyConstant.COMMON_EXPIRE_TIME_SECONDS));
        }
        return Result.success("修改成功！");
    }

    @Override
    public Result delDictDtl(String id) throws BusinessException {
        SysDictDtl sysDictDtl = sysDictDtlMapper.selectById(id);
        String redisKey = RedisKeyConstant.SYS_DICT+":"+sysDictDtl.getDictType();
        int r = sysDictDtlMapper.updateById(SysDictDtl.builder().dictId(StrUtil.toInt(id)).status("0").build());
        if(!Objects.equals(r,1))  throw new BusinessException(ErrorMsgConst.DATA_ERROR);

        List list = sysDictDtlMapper.selectList(Wrappers.lambdaQuery(SysDictDtl.class)
                .eq(SysDictDtl::getDictType,sysDictDtl.getDictType()));
        if(list.size()>0 && redisTemplate.hasKey(redisKey)){
            redisTemplate.opsForValue().set(redisKey,sysDictDtlMapper.selectList(Wrappers.lambdaQuery(SysDictDtl.class)
                    .eq(SysDictDtl::getDictType,sysDictDtl.getDictType())),Duration.ofMinutes(RedisKeyConstant.COMMON_EXPIRE_TIME_SECONDS));
        }else{
            redisTemplate.delete(redisKey);
        }
        return Result.success("删除成功！");
    }
}
