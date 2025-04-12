package com.ldd.home.operate.controller;

import com.alibaba.fastjson2.JSON;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.ext.Add;
import com.ldd.home.operate.common.ext.Page;
import com.ldd.home.operate.common.ext.Upd;
import com.ldd.home.operate.common.utils.ExceptionUtil;
import com.ldd.home.operate.entity.PrivateAccInfo;
import com.ldd.home.operate.entity.req.AccInfoReq;
import com.ldd.home.operate.entity.req.IdReq;
import com.ldd.home.operate.service.IPrivateAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/private/account")
@Slf4j
public class PrivateAccountController {

    @Autowired
    IPrivateAccountService accountService;

    @PostMapping("/getAccountList")
    public Result getAccountList(@RequestBody @Validated(Page.class) AccInfoReq req){
        try{
            Result result = accountService.getAccountList(req);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @PostMapping("/addAccount")
    public Result addAccount(@RequestBody @Validated(Add.class) PrivateAccInfo PrivateAccInfo){
        return accountService.addAccount(PrivateAccInfo);
    }

    @PostMapping("/updAccount")
    public Result updAccount(@RequestBody @Validated(Upd.class) PrivateAccInfo PrivateAccInfo){
        return accountService.updAccount(PrivateAccInfo);
    }

    @PostMapping("/delAccount")
    public Result delAccount(@RequestBody @Validated IdReq idReq){
        log.info("{}.delAccount请求入参：{}",getClass().getName(), JSON.toJSONString(idReq));
        Result result =  accountService.delAccount(idReq.getId());
        log.info("{}.delAccount请求出参：{}",getClass().getName(), JSON.toJSONString(result));
        return result;
    }

    /**
     * 模板文件导入
     */
    @PostMapping("/importTemplate")
    public Result importTemplate(@RequestParam("file") MultipartFile file,@RequestParam("repId") String repId) throws IOException {
        try {
            return accountService.importTemplate(file, repId);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e instanceof BusinessException ?e.getMessage(): ExceptionUtil.getExceptionCause(e));
            throw e;
        }
    }

}
