package com.ldd.home.operate.controller;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.ext.Add;
import com.ldd.home.operate.common.ext.Page;
import com.ldd.home.operate.common.ext.Upd;
import com.ldd.home.operate.entity.SysConfig;
import com.ldd.home.operate.entity.req.BaseReq;
import com.ldd.home.operate.entity.req.IdReq;
import com.ldd.home.operate.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统配置
 * @author ldd
 * @since 2024-09-05
 */
@Slf4j
@RestController
@RequestMapping("/sys/config")
public class SysConfigController {

    @Autowired
    ISysConfigService configService;

    @PostMapping("/getSysConfigList")
    public Result getSysConfigList(@RequestBody  @Validated(Page.class) BaseReq req){
        log.info("{}.getSysConfigList 入参：{}",getClass().getName(), req);
        Result result = configService.getSysConfigList(req);
        log.info("{}.getSysConfigList 出参：{}",getClass().getName(), result);
        return result;
    }

    @PostMapping("/addSysConfig")
    public Result addSysConfig(@RequestBody @Validated(Add.class) SysConfig config) throws BusinessException, InterruptedException {
        log.info("{}.addSysConfig 入参：{}",getClass().getName(), config);
        Result result = configService.addSysConfig(config);
        log.info("{}.addSysConfig 出参：{}",getClass().getName(), result);
        return result;
    }

    @PostMapping("/updSysConfig")
    public Result updSysConfig(@RequestBody  @Validated(Upd.class) SysConfig req) throws BusinessException, InterruptedException {
        log.info("{}.updSysConfig 入参：{}",getClass().getName(), req);
        Result result = configService.updSysConfig(req);
        log.info("{}.updSysConfig 出参：{}",getClass().getName(), result);
        return result;
    }

    @PostMapping("/delSysConfig")
    public Result delSysConfig(@RequestBody  @Validated IdReq req) throws BusinessException {
        log.info("{}.delSysConfig 入参：{}",getClass().getName(), req);
        Result result = configService.delSysConfig(req);
        log.info("{}.delSysConfig 出参：{}",getClass().getName(), result);
        return result;
    }


}
