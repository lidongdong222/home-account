package com.ldd.home.operate.controller;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.ext.Add;
import com.ldd.home.operate.common.ext.Upd;
import com.ldd.home.operate.entity.PrivateSubInfo;
import com.ldd.home.operate.entity.req.IdReq;
import com.ldd.home.operate.entity.req.SubQueryReq;
import com.ldd.home.operate.service.IPrivateSubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private/account/subject")
public class PrivateSubjectController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IPrivateSubjectService subjectService;

    @PostMapping("getSubjectList")
    public Result getSubjectList(@RequestBody @Validated SubQueryReq req){
        return subjectService.getSubjectList(req);
    }

    /**
     * 获取下一个自然排序的科目代码
     * @param idReq
     * @return
     */
    @PostMapping("getNextSubCode")
    public Result getNextSubCode(@RequestBody IdReq idReq){
        return subjectService.getNextSubCode(idReq.getId());
    }

    @PostMapping("addSubject")
    public Result addSubject(@RequestBody @Validated(Add.class) PrivateSubInfo PrivateSubInfo){
        try {
            return subjectService.addSubject(PrivateSubInfo);
        } catch (BusinessException e) {
            logger.info(e.getMessage());
            return Result.fail(e.getMessage());
        }
    }

    @PostMapping("updSubject")
    public Result updSubject(@RequestBody @Validated(Upd.class) PrivateSubInfo PrivateSubInfo){
        try {
            return subjectService.updSubject(PrivateSubInfo);
        } catch (BusinessException e) {
            logger.info(e.getMessage());
            return Result.fail(e.getMessage());
        }
    }

    @PostMapping("delSubject")
    public Result delSubject(@RequestBody @Validated IdReq req){
        try {
            return subjectService.delSubject(req.getId());
        } catch (BusinessException e) {
            logger.info(e.getMessage());
            return Result.fail(e.getMessage());
        }
    }
}
