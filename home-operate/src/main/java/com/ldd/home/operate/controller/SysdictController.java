package com.ldd.home.operate.controller;

import com.ldd.home.operate.common.constant.ErrorMsgConst;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.ext.Page;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.entity.SysDict;
import com.ldd.home.operate.entity.SysDictDtl;
import com.ldd.home.operate.entity.req.BaseReq;
import com.ldd.home.operate.entity.req.IdReq;
import com.ldd.home.operate.service.ISysDictService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/sys/dict")
public class SysdictController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ISysDictService sysDictService;

    @PostMapping("/getDictByType")
    public Result getDictByType(@RequestBody SysDict req) throws BusinessException {
        log.info("getAccountList 入参：{}",req);
        if(StrUtil.isEmpty(req.getDictType())){throw new BusinessException(ErrorMsgConst.PARAMS_DELETION_ERROR);}
        Result result = sysDictService.getDictByType(req);
        log.info("getAccountList 出参：{}",result);
        return result;
    }

    @PostMapping("/getDictList")
    public Result getDictTypeList(@RequestBody @Validated(Page.class) BaseReq req){
        return sysDictService.getDictTypeList(req);
    }

    @PostMapping("/addDict")
    public Result addDictType(@RequestBody @Validated SysDict dict) throws BusinessException {
        return sysDictService.addDictType(dict);
    }

    @PostMapping("/updDict")
    public Result updDictType(@RequestBody @Validated SysDict dict) throws BusinessException {
        return sysDictService.updDictType(dict);
    }

    @PostMapping("/delDict")
    public Result delDictType(@RequestBody @Validated IdReq req) throws BusinessException {
        return sysDictService.delDictType(req.getId());
    }

    @PostMapping("/getDictDtlList")
    public Result getDictDtlList(@RequestBody @Validated IdReq req){
        return sysDictService.getDictDtlList(req.getId());
    }

    @PostMapping("/addDictDtl")
    public Result addDictDtl(@RequestBody @Validated SysDictDtl dtl) throws BusinessException {
        return sysDictService.addDictDtl(dtl);
    }

    @PostMapping("/updDictDtl")
    public Result updDictDtl(@RequestBody @Validated SysDictDtl dtl) throws BusinessException {
        return sysDictService.updDictDtl(dtl);
    }

    @PostMapping("/delDictDtl")
    public Result delDictDtl(@RequestBody @Validated IdReq req) throws BusinessException {
        return sysDictService.delDictDtl(req.getId());
    }

}


