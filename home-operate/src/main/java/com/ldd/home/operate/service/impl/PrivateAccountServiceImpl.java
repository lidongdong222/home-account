package com.ldd.home.operate.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldd.home.operate.common.constant.BaseConst;
import com.ldd.home.operate.common.constant.ErrorMsgConst;
import com.ldd.home.operate.common.entity.ResolveCount;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.utils.DateUtil;
import com.ldd.home.operate.common.utils.ExceptionUtil;
import com.ldd.home.operate.common.utils.IdUtil;
import com.ldd.home.operate.common.utils.RegexUtil;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.entity.PrivateAccInfo;
import com.ldd.home.operate.entity.PrivateAccInfoTmp;
import com.ldd.home.operate.entity.ImpInfo;
import com.ldd.home.operate.entity.req.AccInfoReq;
import com.ldd.home.operate.entity.req.ResourceMultipartFileBusiReq;
import com.ldd.home.operate.entity.resp.ResourceBusiResp;
import com.ldd.home.operate.entity.tmp.AccountGather;
import com.ldd.home.operate.mapper.ImpInfoMapper;
import com.ldd.home.operate.mapper.PrivateAccInfoMapper;
import com.ldd.home.operate.mapper.PrivateAccInfoTmpMapper;
import com.ldd.home.operate.service.IPrivateAccountService;
import com.ldd.home.operate.service.IPrivateSubInfoService;
import com.ldd.home.operate.service.IResourceService;
import com.ldd.home.operate.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class PrivateAccountServiceImpl implements IPrivateAccountService {

    //        菜单id，通知跳转使用
    int menuId = 17;
    //        导入临时表
    String tmpTable = "imp_tmp";
    //        最终数据表
    final String targetTable = "private_acc_info";

    @Autowired
    PrivateAccInfoMapper accInfoMapper;

    @Autowired
    IResourceService resourceService;

    @Autowired
    ImpInfoMapper impInfoMapper;

    @Autowired
    PrivateAccInfoTmpMapper accInfoTmpMapper;
    @Autowired
    IPrivateSubInfoService subInfoService;
    @Autowired
    ISysConfigService configService;

    @Override
    public Result getAccountList(AccInfoReq req) {
        int year = Calendar.getInstance().get(Calendar.YEAR);

        Page<PrivateAccInfo> page = accInfoMapper.getAccountList(new Page(req.getPageNum(),req.getPageSize()),req);
        for (PrivateAccInfo a : page.getRecords()) {
            a.setAccountType(PrivateAccInfo.AccountType.getValue(a.getSubCode().substring(0,2)));
        }

        return Result.successPage("查询成功！",
                page.getTotal(),page.getRecords(),accountCurrentYearGather(year));
    }

    /**
     * 汇总总余额、当年总收入，总消费，净存款
     * 单位：万元
     * @return
     */
    private AccountGather accountCurrentYearGather(int year) {
        AccountGather accountGather =  accInfoMapper.getCurrentYearGather(year);
        if(Objects.isNull(accountGather)) accountGather = new AccountGather();
        accountGather.setTotalBalance(
                new BigDecimal(StrUtil.toDouble(accountGather.getTotalBalance(),0.00)).divide(new BigDecimal(10000))
                        .setScale(2,RoundingMode.HALF_UP).doubleValue());
        accountGather.setCurrentYearIncome(
                new BigDecimal(StrUtil.toDouble(accountGather.getCurrentYearIncome(),0.00)).divide(new BigDecimal(10000))
                        .setScale(2,RoundingMode.HALF_UP).doubleValue());
        accountGather.setCurrentYearConsumption(
                new BigDecimal(StrUtil.toDouble(accountGather.getCurrentYearConsumption(),0.00)).divide(new BigDecimal(10000))
                        .setScale(2,RoundingMode.HALF_UP).doubleValue());
        accountGather.setCurrentYearNetDeposit(
                new BigDecimal(StrUtil.toDouble(accountGather.getCurrentYearNetDeposit(),0.00)).divide(new BigDecimal(10000))
                        .setScale(2,RoundingMode.HALF_UP).doubleValue());
        return accountGather;
    }

    @Override
    public Result addAccount(PrivateAccInfo PrivateAccInfo) {
        PrivateAccInfo.setAccId(null);
        PrivateAccInfo.setStatus("1");
        PrivateAccInfo.setAccYear(StrUtil.toInt(PrivateAccInfo.getAccDate().substring(0,4)));
        PrivateAccInfo.setAccMonth(StrUtil.toInt(PrivateAccInfo.getAccDate().substring(5,7)));
        int r = accInfoMapper.insert(PrivateAccInfo);
        if (Objects.equals(r,1)) return Result.success("新增成功！");
        else return Result.fail(ErrorMsgConst.DATA_ERROR);
    }

    @Override
    public Result updAccount(PrivateAccInfo PrivateAccInfo) {
        PrivateAccInfo.setAccYear(StrUtil.toInt(PrivateAccInfo.getAccDate().substring(0,4)));
        PrivateAccInfo.setAccMonth(StrUtil.toInt(PrivateAccInfo.getAccDate().substring(5,7)));
        PrivateAccInfo.setAccDate(PrivateAccInfo.getAccDate());
        int r = accInfoMapper.updateById(PrivateAccInfo);
        if (Objects.equals(r,1)) return Result.success("更新成功！");
        else return Result.fail(ErrorMsgConst.DATA_ERROR);
    }

    @Override
    public Result delAccount(String id) {
        int r = accInfoMapper.updateById(PrivateAccInfo.builder().accId(StrUtil.toLong(id)).status("0").build());
        if (Objects.equals(r,1)) return Result.success("删除成功！");
        else return Result.fail(ErrorMsgConst.DATA_ERROR);
    }

    /**
     * 1.导入记录表，2.上传资源，3.导入临时表，4.主表
     */
    @Override
    public Result   importTemplate(MultipartFile file, String repId) throws IOException {
        String resultMsg = "等待导入中！";
        int importStatus = ImpInfo.ImpStatusEnum.WAIT.getCode();
        ImpInfo impInfo =ImpInfo.builder().menuId(menuId).status(importStatus).build();
        impInfoMapper.insertObtainId(impInfo);
        long resolveBeginTime =System.currentTimeMillis();
        ResourceBusiResp resourceBusiResp = new ResourceBusiResp();
        ResolveCount resolveCount = new ResolveCount();
        ResourceMultipartFileBusiReq multipartFileBusiReq = new ResourceMultipartFileBusiReq(file,StrUtil.toStr(impInfo.getImpId()),tmpTable,false);
        try{
            resourceBusiResp = resourceService.saveFile(multipartFileBusiReq);
            return Result.success(resultMsg);
        }catch (Exception e){
            resultMsg = e.getMessage();
            importStatus = ImpInfo.ImpStatusEnum.FAIL.getCode();
            throw e;
        }finally {
            impInfoMapper.updateById(ImpInfo.builder()
                    .impId(impInfo.getImpId())
                    .resId(resourceBusiResp.getResId()).resName(file.getOriginalFilename())
                    .impTime(new Date()).impTimeComsum(Long.valueOf((resolveBeginTime-System.currentTimeMillis())/1000).intValue())
                    .impTmpTable(tmpTable).impTargetTable(targetTable).status(importStatus)
                    .impSucc(resolveCount.getSuccessCount()).impFail(resolveCount.getFailCount()).impTotal(resolveCount.getTotalCount())
                    .analysisBean("accountServiceImpl").analysisMethod("resolveExcelData")
                    .impMessage(resultMsg)
                    .menuId(menuId).build());
        }
    }

    /**
     * 排队导入处理方法，
     * @param impId
     * @throws Exception
     */
    public void resolveExcelData(long impId) throws Exception {
        impInfoMapper.updateById(ImpInfo.builder().impId(impId).status(ImpInfo.ImpStatusEnum.HANDING.getCode()).build());
        File f = null;
        try{
            ImpInfo impInfo = impInfoMapper.selectById(impId);
            String tempPath = configService.getConfigValue("SYS_TEMP_FILE_PATH");
            f = resourceService.getFile(impInfo.getResId(),
                    tempPath+File.separator+
                            IdUtil.getUUid()+
                            impInfo.getResName().substring(impInfo.getResName().lastIndexOf(".")));
//            将excel中的数据解析到临时表中
            resolveExcelDataToTmp(f,impId,impInfo.getResId(),3,0);
//            将临时表中的数据解析到主表中
            ResolveCount resolveCount = resolveTmpToMain(impInfo.getImpId());
            impInfoMapper.updateById(ImpInfo.builder()
                    .impId(impId).status(ImpInfo.ImpStatusEnum.SUCCESS.getCode())
                    .impSucc(resolveCount.getSuccessCount()).impFail(resolveCount.getFailCount()).impTotal(resolveCount.getTotalCount())
                    .build());
        }catch (Exception e){
            impInfoMapper.updateById(ImpInfo.builder().impId(impId)
                    .status(ImpInfo.ImpStatusEnum.FAIL.getCode())
                    .impMessage(ExceptionUtil.getExceptionCause(e,200)).build());
        }finally {
            if(Objects.nonNull(f) && f.exists()) f.delete();
        }

    }

    /**
     * 将临时表中的数据解析到主表中
     *
     * @return
     */
    private ResolveCount resolveTmpToMain(Long impId) {

        List<String> subCodes = subInfoService.getSubInfoList().stream().map(t->t.getSubCode()).toList();
        while (true){
            List succList = new ArrayList();
            List<PrivateAccInfoTmp> tmps = accInfoTmpMapper.selectList(new LambdaQueryWrapper<PrivateAccInfoTmp>()
                    .eq(PrivateAccInfoTmp::getDealStatus,0)
                    .eq(PrivateAccInfoTmp::getImpId,impId)
                    .last("limit "+ BaseConst.DB_QUERY_BATCH_MAX));
            if(tmps.size()<1)break;
            for (PrivateAccInfoTmp tmp:tmps){
                StringBuffer errMsg = new StringBuffer();
                if(StrUtil.isEmpty(tmp.getSubCode())||!subCodes.contains(tmp.getSubCode())){
                    errMsg.append("代码不能为空或代码不在科目信息列表中！");
                }
                if(StrUtil.isEmpty(tmp.getAmount())||!tmp.getAmount().matches(RegexUtil.REGEX_EXCEL_DOUBLE)){
                    errMsg.append("金额不能为空或金额格式异常！");
                }
                if(StrUtil.isEmpty(tmp.getAccDate())||!tmp.getAccDate().matches(RegexUtil.REGEX_DATE_YYYY_MM_DD)){
                    errMsg.append("账务日期不能为空或账务日期格式异常（请使用yyyy-mm-dd格式）！");
                }
                if(errMsg.length()>0){
                    tmp.setDealStatus(2);
                    tmp.setDealMsg(errMsg.toString());
                    continue;
                }
                tmp.setAccYear(tmp.getAccDate().substring(0,4));
                tmp.setAccMonth(tmp.getAccDate().substring(5,7));
                tmp.setDealStatus(1);
                succList.add(tmp);
            }
            if(succList.size()>0) accInfoMapper.insertBatch(succList);
            accInfoTmpMapper.updateBatch(tmps);
        }
        return accInfoTmpMapper.selectResolveCount(impId);
    }

    /**
     * 将excel中的数据解析到临时表中
     */
    private void resolveExcelDataToTmp(File file, Long impId, Long resId, int startRow, int sheetIndex) throws Exception {
        try {
            EasyExcel.read(file, new NoModelDataListener(impId,resId,startRow,sheetIndex)).sheet().doRead();
        }catch (Exception e){
            throw e;
        }
    }

    class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {

        Long impId = 0L;
        Long resId = 0L;
        int sheetIndex = 0;
        int startRow = 0;
        /**
         * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
         */
        private static final int BATCH_COUNT = 100;
        private List<PrivateAccInfoTmp> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

        public NoModelDataListener(Long impId, Long resId,int startRow, int sheetIndex) {
            this.impId = impId;
            this.resId = resId;
            this.startRow = startRow;
            this.sheetIndex = sheetIndex;
        }

        @Override
        public void invoke(Map<Integer, String> data, AnalysisContext context) {
            Date now  = new Date();
            if(context.readRowHolder().getRowIndex()<startRow) return;
            PrivateAccInfoTmp tmp = PrivateAccInfoTmp.builder()
                    .tmpId(IdUtil.getUUid())
                    .resId(resId)
                    .importDate(now)
                    .impId(impId).build();
            for (Map.Entry<Integer, String> entry : data.entrySet()) {
                switch (entry.getKey()){
                    case 0:tmp.setAccountType(StringUtils.trim(entry.getValue()));break;
                    case 1:tmp.setSubCode(StringUtils.trim(entry.getValue()));break;
                    case 2:tmp.setSubName(StringUtils.trim(entry.getValue()));break;
                    case 3:tmp.setAmount(StringUtils.trim(entry.getValue()));break;
                    case 4:tmp.setAccYear(StringUtils.trim(entry.getValue()));break;
                    case 5:tmp.setAccMonth(StringUtils.trim(entry.getValue()));break;
                    case 6:
                        try {
                            tmp.setAccDate(DateUtil.convertCommonDateParttern(StringUtils.trim(entry.getValue())));
                        } catch (BusinessException e) {
                            tmp.setAccDate(StringUtils.trim(entry.getValue()));
                        }
                        break;
                    case 7:tmp.setDigest(StringUtils.trim(entry.getValue()));break;
                    case 8:tmp.setExchangeDesc(StringUtils.trim(entry.getValue()));break;
                    case 9:tmp.setSourceDesc(StringUtils.trim(entry.getValue()));break;
                }
            }

            cachedDataList.add(tmp);
            if (cachedDataList.size() >= BATCH_COUNT) {
                try {
                    saveImpTmpData(cachedDataList);
                } catch (BusinessException e) {
                    throw new RuntimeException(e);
                }
                cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            try {
                if(cachedDataList.size()>0)saveImpTmpData(cachedDataList);
                cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
            } catch (BusinessException e) {
                throw new RuntimeException(e);
            }
            log.info("存储数据库临时表结束！");
        }

        /**
         * 加上存储数据库
         */
        private void saveImpTmpData(List<PrivateAccInfoTmp> cachedDataList) throws BusinessException {
            int r = accInfoTmpMapper.insertBatch(cachedDataList);
            if(!Objects.equals(r ,cachedDataList.size())){
                log.info("存储数据库临时表异常！本次存储{}条，实际导入{}条，临时表数据:{}",r,cachedDataList.size(), JSONObject.toJSONString(cachedDataList));
                throw new BusinessException("存储数据库异常,导入数据与存储数据量不一致！导入数据量：{}"+JSONObject.toJSONString(cachedDataList));
            }
            log.info("存储数据库临时表成功！本次存储{}条",cachedDataList.size());
        }
    }

}
