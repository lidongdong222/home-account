package com.ldd.home.operate.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ldd.home.operate.common.constant.ErrorMsgConst;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.ext.Add;
import com.ldd.home.operate.common.ext.Page;
import com.ldd.home.operate.common.ext.Upd;
import com.ldd.home.operate.common.utils.ExceptionUtil;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.entity.AccInfo;
import com.ldd.home.operate.entity.SubInfo;
import com.ldd.home.operate.entity.WxBill;
import com.ldd.home.operate.entity.WxBillMatchSubjectRule;
import com.ldd.home.operate.entity.req.AccInfoReq;
import com.ldd.home.operate.entity.req.IdReq;
import com.ldd.home.operate.entity.req.SubInfoReq;
import com.ldd.home.operate.service.IAccInfoService;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 横式科目信息表 前端控制器
 * </p>
 *
 * @author ldd
 * @since 2024-09-10
 */
@RestController
@Slf4j
@RequestMapping("/account")
public class AccInfoController {

    @Autowired
    IAccInfoService accInfoService;

    @PostMapping("/subject/getSubList")
    public Result getSubList(@RequestBody @Validated(Page.class) SubInfoReq req){
        log.info("getSubList 入参：{}",req);
        Result result = accInfoService.getSubList(req);
        log.info("getSubList 出参：{}",req);
        return result;
    }

    @PostMapping("/subject/addSubInfo")
    public Result addSubInfo(@RequestBody @Validated(Add.class)  SubInfo req) throws BusinessException, InterruptedException {
        log.info("addSubInfo 入参：{}",req);
        Result result = accInfoService.addSubInfo(req);
        log.info("addSubInfo 出参：{}",req);
        return result;
    }

    @PostMapping("/subject/updSubinfo")
    public Result updSubinfo(@RequestBody @Validated(Upd.class) SubInfo req) throws BusinessException {
        log.info("updSubinfo 入参：{}",req);
        Result result = accInfoService.updSubinfo(req);
        log.info("updSubinfo 出参：{}",req);
        return result;
    }

    @PostMapping("/subject/delSubinfo")
    public Result delSubinfo(@RequestBody IdReq req) throws BusinessException {
        log.info("delSubinfo 入参：{}",req);
        Result result = accInfoService.delSubinfo(req);
        log.info("delSubinfo 出参：{}",req);
        return result;
    }

    @PostMapping("/getAccountList")
    public Result getAccountList(@RequestBody @Validated(Page.class) AccInfoReq req){
        log.info("getAccountList 入参：{}",req);
        Result result = accInfoService.getAccountList(req);
        log.info("getAccountList 出参：{}",result);
        return result;
    }

    @PostMapping("/addAccountConfirm")
    public Result addAccountConfirm(@RequestBody @Validated(Add.class)  AccInfo req){
        log.info("delSubinfo 入参：{}",req);
        Result result = accInfoService.addAccountConfirm(req);
        log.info("getAccountList 出参：{}",result);
        return result;
    }

    @PostMapping("/addAccount")
    public Result addAccount(@RequestBody @Validated(Add.class) AccInfo req) throws BusinessException {
        log.info("delSubinfo 入参：{}",req);
        Result result = accInfoService.addAccount(req);
        log.info("getAccountList 出参：{}",result);
        return result;
    }

    @PostMapping("/updAccount")
    public Result updAccount(@RequestBody @Validated(Upd.class) AccInfo req) throws BusinessException {
        log.info("delSubinfo 入参：{}",req);
        return accInfoService.updAccount(req);
    }

    @PostMapping("/delAccount")
    public Result delAccount(@RequestBody @Validated IdReq req) throws BusinessException {
        log.info("{}.delAccount请求入参：{}",getClass().getName(), JSON.toJSONString(req));
        Result result =  accInfoService.delAccount(req.getId());
        log.info("{}.delAccount请求出参：{}",getClass().getName(), JSON.toJSONString(result));
        return result;
    }

    /**
     * 导入微信账单
     */
    @PostMapping("/importWxBill")
    public Result importWxBill(@RequestParam("file")MultipartFile file, @RequestParam Map params) throws Exception {
        log.info("importWxBill 入参：MultipartFile:{},params{}",file.getOriginalFilename(),params);
        Result result =  accInfoService.importWxBill(file,params);
        log.info("importWxBill 请求出参：{}", JSON.toJSONString(result));
        return result;
    }

    /**
     * 导入微信账单
     */
    @PostMapping("/importWxBillData")
    public Result importWxBillData(@RequestBody Map params) throws Exception {
        log.info("importWxBillData 请求入参：{}", JSON.toJSONString(params));
        List<Map> wxBillList = (List) params.get("wxBillList");

        if(wxBillList==null || wxBillList.size()<1){
            throw new BusinessException(ErrorMsgConst.PARAMS_DELETION_ERROR);
        }
        for(Map m:wxBillList){
            if(StrUtil.isEmpty(m.get("subId"))){
                throw new BusinessException("未选择科目名称！");
            }
        }

        Result result =  accInfoService.importWxBillData(wxBillList);
        log.info("importWxBillData 请求出参：{}", JSON.toJSONString(result));
        return result;
    }

    @PostMapping("/exportAccountList")
    public void exportAccountList(@RequestBody AccInfoReq params, HttpServletResponse response){
        log.info("exportAccountList 入参：params {}",params);
        try {
            accInfoService.exportAccountList(params,response);
            log.info("exportAccountList 导出完成。");
        } catch (Exception e) {
            log.error(e instanceof BusinessException?e.getMessage(): ExceptionUtil.getExceptionCause(e));
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            try {
                response.getOutputStream().write(JSONObject.toJSONString(Result.fail(e.getMessage())).getBytes());
            } catch (IOException ex) {
            }
            log.info("exportAccountList 导出异常。");
        }
    }

    @PostMapping("/addWxMatchRule")
    public Result addWxMatchRule(@RequestBody JSONObject params) throws BusinessException {
        log.info("{}.addWxMatchRule 请求入参：{}",getClass().getName(), JSON.toJSONString(params));
        List<WxBillMatchSubjectRule> mySubMatchRuleList = checkParamsAndTransObj(params.getJSONArray("wxMatchRuleList"));
        Result result =  accInfoService.addWxMatchRule(mySubMatchRuleList);
        log.info("{}.addWxMatchRule 请求出参：{}",getClass().getName(), result);
        return result;
    }

    @PostMapping("/getWxMatchRuleList")
    public Result getWxMatchRuleList() throws BusinessException {
        log.info("{}.getWxMatchRuleList 请求入参。",getClass().getName());
        Result result =  accInfoService.getWxMatchRuleList();
        log.info("{}.getWxMatchRuleList 请求出参：{}",getClass().getName(), result);
        return result;
    }

    //把请求参数转换为实体类，校验请求数据
    private List<WxBillMatchSubjectRule> checkParamsAndTransObj(JSONArray arr) throws BusinessException {
        List<WxBillMatchSubjectRule> list  = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            WxBillMatchSubjectRule rule = JSONObject.parseObject(arr.getJSONObject(i).toString(),WxBillMatchSubjectRule.class);
            if(rule==null
                ||StrUtil.isEmpty(rule.getSubType())
                ||StrUtil.isEmpty(rule.getMatchType())
                ||StrUtil.isEmpty(rule.getMatchContent())
                ||StrUtil.isEmpty(rule.getMatchSubId())
                ||StrUtil.isEmpty(rule.getWxDataItem())
                ||!StrUtil.lengthBetween(rule.getSubType(),0,2)
                ||!StrUtil.lengthBetween(rule.getMatchType(),0,10)
                ||!StrUtil.lengthBetween(rule.getMatchContent(),0,100)
                ||!StrUtil.lengthBetween(rule.getMatchSubId().toString(),0,20)
                ||!StrUtil.lengthBetween(rule.getWxDataItem(),0,20)
            ){
                throw new BusinessException(ErrorMsgConst.PARAMS_DELETION_ERROR);
            }
            list.add(rule);
        }
        return list;
    }

    /**
     * 使用规则快速匹配科目
     */
    @PostMapping("/matchSubjectByRule")
    public Result matchSubjectByRule(@RequestBody JSONObject params){
        List<WxBill> wxBills = params.getList("wxBillList",WxBill.class);
        return accInfoService.matchSubjectByRule(wxBills);
    }

    @PostMapping("/subject/exporSubjectList")
    public void exporSubjectList(@RequestBody SubInfoReq params, HttpServletResponse response){
        log.info("exporSubjectList 入参：params {}",params);
        try {
            accInfoService.exporSubjectList(params,response);
            log.info("exporSubjectList 导出完成。");
        } catch (Exception e) {
            log.error(e instanceof BusinessException?e.getMessage(): ExceptionUtil.getExceptionCause(e));
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            try {
                response.getOutputStream().write(JSONObject.toJSONString(Result.fail(e.getMessage())).getBytes());
            } catch (IOException ex) {
            }
            log.info("exportAccountList 导出异常。");
        }
    }
}
