package com.ldd.home.operate.service;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.entity.SysDict;
import com.ldd.home.operate.entity.SysDictDtl;
import com.ldd.home.operate.entity.req.BaseReq;

import java.util.List;

public interface ISysDictService {
    Result getDictTypeList(BaseReq req);

    Result addDictType(SysDict dict) throws BusinessException;

    Result updDictType(SysDict dict) throws BusinessException;

    Result delDictType(String id) throws BusinessException;

    Result getDictDtlList(String dictType);

    Result addDictDtl(SysDictDtl dtl) throws BusinessException;

    Result updDictDtl(SysDictDtl dtl) throws BusinessException;

    Result delDictDtl(String id) throws BusinessException;

    Result getDictByType(SysDict req);

    List<SysDictDtl> getDictByType(String dictType);
}
