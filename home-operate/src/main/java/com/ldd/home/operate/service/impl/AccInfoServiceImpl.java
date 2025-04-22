package com.ldd.home.operate.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldd.home.operate.common.constant.ErrorMsgConst;
import com.ldd.home.operate.common.entity.ResolveCount;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.enums.StatusEnum;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.utils.DateUtil;
import com.ldd.home.operate.common.utils.ExcelUtil;
import com.ldd.home.operate.common.utils.HttpServletResponseUtil;
import com.ldd.home.operate.common.utils.IdUtil;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.config.redis.RedisLockConstant;
import com.ldd.home.operate.entity.AccBalance;
import com.ldd.home.operate.entity.AccInfo;
import com.ldd.home.operate.entity.ImpInfo;
import com.ldd.home.operate.entity.RepColumn;
import com.ldd.home.operate.entity.RepInfo;
import com.ldd.home.operate.entity.RepSheet;
import com.ldd.home.operate.entity.SubInfo;
import com.ldd.home.operate.entity.WxBill;
import com.ldd.home.operate.entity.req.AccInfoReq;
import com.ldd.home.operate.entity.req.IdReq;
import com.ldd.home.operate.entity.req.ReportReq;
import com.ldd.home.operate.entity.req.ResourceMultipartFileBusiReq;
import com.ldd.home.operate.entity.req.SubInfoReq;
import com.ldd.home.operate.entity.resp.ResourceBusiResp;
import com.ldd.home.operate.mapper.AccInfoMapper;
import com.ldd.home.operate.mapper.ImpInfoMapper;
import com.ldd.home.operate.mapper.RepColumnMapper;
import com.ldd.home.operate.mapper.RepInfoMapper;
import com.ldd.home.operate.mapper.RepSheetMapper;
import com.ldd.home.operate.mapper.SubInfoMapper;
import com.ldd.home.operate.mapper.WxBillMapper;
import com.ldd.home.operate.service.IAccInfoService;
import com.ldd.home.operate.service.IResourceService;
import com.ldd.home.operate.service.ISysConfigService;
import com.ldd.home.operate.service.ISysDictService;
import com.mchange.v2.csv.FastCsvUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.ldd.home.operate.config.redis.RedisLockConstant.COMMON_RLOCK_EXPIRE_TIME_SECONDS;
import static com.ldd.home.operate.config.redis.RedisLockConstant.COMMON_RLOCK_WAIT_TIME_SECONDS;

@Slf4j
@Service
public class AccInfoServiceImpl implements IAccInfoService {

    //文件导入最终入库表
    final String targetTable = "wx_bill";
    //导入所在菜单Id
    final int menuId = 17;

    @Autowired
    SubInfoMapper subInfoMapper;
    @Autowired
    AccInfoMapper accInfoMapper;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    ImpInfoMapper impInfoMapper;
    @Autowired
    IResourceService resourceService;
    @Autowired
    ISysConfigService configService;
    @Autowired
    WxBillMapper wxBillMapper;
    @Autowired
    RepInfoMapper repInfoMapper;
    @Autowired
    RepSheetMapper repSheetMapper;
    @Autowired
    RepColumnMapper repColumnMapper;
    @Autowired
    ISysDictService sysDictService;
    @Override
    public Result getSubList(SubInfoReq req) {
        Page<SubInfo> page =  subInfoMapper.getSubInfoPage(
                new Page<>(req.getPageNum(),req.getPageSize()),req);
        page.getRecords().forEach(s->{
            s.setSubTypeName(SubInfo.SubTypeEnum.getSubTypeName(s.getSubType()));
        });
        return Result.successPage("查询成功！",page.getTotal(),page.getRecords());
    }

    /**
     * 新增科目
     */
    @Override
    public Result addSubInfo(SubInfo req) throws BusinessException, InterruptedException {
        RLock lock = redissonClient.getLock(RedisLockConstant.RLOCK_HOME_ACC_SUB_OPT);
        try{
            if(lock.tryLock(COMMON_RLOCK_WAIT_TIME_SECONDS,
                    COMMON_RLOCK_EXPIRE_TIME_SECONDS,
                    TimeUnit.SECONDS)){
                req.setSubId(null);
                req.setStatus(StatusEnum.EFFECTIVE.getCode());
                String[] codes = req.getSubCode().split("-");
                String[] names = req.getSubName().split("-");
                if(codes.length>20) throw new BusinessException("科目级别超过20级！");
                if(!Objects.equals(codes.length,names.length)) throw new BusinessException("科目编码和科目名称级数不一致！");
                List check = subInfoMapper.selectList(Wrappers.lambdaQuery(SubInfo.class)
                        .eq(SubInfo::getSubCode,req.getSubDesc()));
                if(check.size()>0) throw new BusinessException("科目编码已存在！");
                for (int i = 1; i < codes.length+1; i++) {
                    setSubInfo(req,i,codes,names);
                }
                req.setSort(100*subInfoMapper.selectCount(Wrappers.query()));
                int r = subInfoMapper.insert(req);
                if(!Objects.equals(r,1)) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
            }
        }catch (Exception e){
            throw e;
        }finally {
            lock.unlock();
        }
        return Result.success("添加成功");
    }


    /**
     * 更新科目
     */
    @Override
    public Result updSubinfo(SubInfo req) throws BusinessException {
        String[] codes = req.getSubCode().split("-");
        String[] names = req.getSubName().split("-");
        if(codes.length>20) throw new BusinessException("科目级别超过20级！");
        if(!Objects.equals(codes.length,names.length)) throw new BusinessException("科目编码和科目名称级数不一致！");
        List<SubInfo> list = subInfoMapper.selectList(Wrappers.lambdaQuery(SubInfo.class)
                .eq(SubInfo::getSubCode,req.getSubCode())
                .ne(SubInfo::getSubId,req.getSubId()));
        if(list.size()>0) throw new BusinessException("科目编码已存在！");
        int r = subInfoMapper.updateById(req);
        if(!Objects.equals(r,1)) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        return Result.success("更新成功");
    }

    /**
     * 删除科目
     */
    @Override
    public Result delSubinfo(IdReq req) throws BusinessException {
        int r = subInfoMapper.updateById(SubInfo.builder()
                .subId(StrUtil.toInt(req.getId()))
                .status(StatusEnum.INVALID.getCode()).build());
        if(!Objects.equals(r,1)) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        return Result.success("删除成功");
    }



    @Override
    public Result getAccountList(AccInfoReq req) {

        Page<AccInfo> page = accInfoMapper.getAccountPage(new Page(req.getPageNum(),req.getPageSize()),req);
        accountEnumConvert(page.getRecords());

        Map data = new HashMap();
        Map summary = accInfoMapper.getAccountSummary(req);
        data.put("summary",summary);

        return Result.successPage("查询成功！",
                page.getTotal(),page.getRecords(),data);
    }

    private void accountEnumConvert(List<AccInfo> records) {
        Map<String,String> dict= new HashMap<>();
        sysDictService.getDictByType("PAY_TYPE").forEach(d->{
            dict.put(d.getDictCode(),d.getDictValue());
        });
        records.forEach(a->{
            a.setPaymentTypeName(dict.get(a.getPaymentType()));
            a.setSubTypeName(SubInfo.SubTypeEnum.getSubTypeName(a.getSubType()));
        });
    }

    @Override
    public Result addAccountConfirm(AccInfo req) {
        StringBuffer confirmMsg = new StringBuffer();
        List<AccInfo> accInfos = accInfoMapper.getRepeat(req);
        if(accInfos.size()>0) confirmMsg.append(accInfos.get(0).getAccDate()).append("已新增过科目【").append(accInfos.get(0).getSubName())
                .append("】金额").append(accInfos.get(0).getAmount());
        if(confirmMsg.length()>0)confirmMsg.append("。是否继续新增？");
        return Result.successData("查询成功",confirmMsg);
    }

    @Override
    public Result addAccount(AccInfo accInfo) throws BusinessException {
        accInfo.setAccId(null);
        accInfo.setStatus(StatusEnum.EFFECTIVE.getCode());
        int r = accInfoMapper.insert(accInfo);
        if (!Objects.equals(r,1)) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        return Result.success("新增成功！");
    }

    @Override
    public Result updAccount(AccInfo accInfo) throws BusinessException {
        accInfo.setAccDate(accInfo.getAccDate());
        int r = accInfoMapper.updateById(accInfo);
        if (!Objects.equals(r,1)) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        return Result.success("更新成功！");
    }

    @Override
    public Result delAccount(String id) throws BusinessException {
        int r = accInfoMapper.deleteById(id);
        if (!Objects.equals(r,1)) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        return Result.success("删除成功！");
    }

    private void setSubInfo(SubInfo info,int i,String[] codes,String[] names) {
        if(i==1){
            info.setSubCode1(codes[i-1]);
            info.setSubName1(names[i-1]);
        }
        if(i==2){
            info.setSubCode2(codes[i-1]);
            info.setSubName2(names[i-1]);
        }
        if(i==3){
            info.setSubCode3(codes[i-1]);
            info.setSubName3(names[i-1]);
        }
        if(i==4){
            info.setSubCode4(codes[i-1]);
            info.setSubName4(names[i-1]);
        }
        if(i==5){
            info.setSubCode5(codes[i-1]);
            info.setSubName5(names[i-1]);
        }
        if(i==6){
            info.setSubCode6(codes[i-1]);
            info.setSubName6(names[i-1]);
        }
        if(i==7){
            info.setSubCode7(codes[i-1]);
            info.setSubName7(names[i-1]);
        }
        if(i==8){
            info.setSubCode8(codes[i-1]);
            info.setSubName8(names[i-1]);
        }
        if(i==9){
            info.setSubCode9(codes[i-1]);
            info.setSubName9(names[i-1]);
        }
        if(i==10){
            info.setSubCode10(codes[i-1]);
            info.setSubName10(names[i-1]);
        }
        if(i==11){
            info.setSubCode11(codes[i-1]);
            info.setSubName11(names[i-1]);
        }
        if(i==12){
            info.setSubCode12(codes[i-1]);
            info.setSubName12(names[i-1]);
        }
        if(i==13){
            info.setSubCode13(codes[i-1]);
            info.setSubName13(names[i-1]);
        }
        if(i==14){
            info.setSubCode14(codes[i-1]);
            info.setSubName14(names[i-1]);
        }
        if(i==15){
            info.setSubCode15(codes[i-1]);
            info.setSubName15(names[i-1]);
        }
        if(i==16){
            info.setSubCode16(codes[i-1]);
            info.setSubName16(names[i-1]);
        }
        if(i==17){
            info.setSubCode17(codes[i-1]);
            info.setSubName17(names[i-1]);
        }
        if(i==18){
            info.setSubCode18(codes[i-1]);
            info.setSubName18(names[i-1]);
        }
        if(i==19){
            info.setSubCode19(codes[i-1]);
            info.setSubName19(names[i-1]);
        }
        if(i==20){
            info.setSubCode20(codes[i-1]);
            info.setSubName20(names[i-1]);
        }
    }

    @Override
    public List<AccBalance> getAccMonthSummaryInfo(String year) {
        return accInfoMapper.getAccMonthSummaryInfo(year);
    }

    /**
     * 微信账单导入功能
     * @param file
     * @param params
     * @return
     */
    @Override
    public Result importWxBill(MultipartFile file, Map params) throws Exception {
        String resultMsg = "等待导入中！";
        int importStatus = ImpInfo.ImpStatusEnum.WAIT.getCode();
        ImpInfo impInfo =ImpInfo.builder().menuId(menuId).resName(file.getOriginalFilename()).status(importStatus).build();
        impInfoMapper.insertObtainId(impInfo);
        long resolveBeginTime =System.currentTimeMillis();
        ResourceBusiResp resourceBusiResp = new ResourceBusiResp();
        ResolveCount resolveCount = new ResolveCount();
        ResourceMultipartFileBusiReq multipartFileBusiReq =
                new ResourceMultipartFileBusiReq(file,StrUtil.toStr(impInfo.getImpId()),targetTable,false);
        try{
            //保存文件
            resourceBusiResp = resourceService.saveFile(multipartFileBusiReq);
            impInfo.setResId(resourceBusiResp.getResId());
            //获取导入数据列表
            List wxBillList = resolveExcelData_wxBill(resourceBusiResp.getResId(),impInfo.getImpId(),file.getOriginalFilename());
            resolveCount.setSuccessCount(wxBillList.size());
            resolveCount.setTotalCount(wxBillList.size());
            //返回微信导入数据，供前台编辑
            return Result.successList(resultMsg,wxBillList);
        }catch (Exception e){
            log.error("微信账单导入异常！"+e.getMessage());
            resultMsg = e.getMessage();
            importStatus = ImpInfo.ImpStatusEnum.FAIL.getCode();
            throw e;
        }finally {
            impInfoMapper.updateById(ImpInfo.builder()
                    .impId(impInfo.getImpId())
                    .resId(resourceBusiResp.getResId()).resName(file.getOriginalFilename())
                    .impTime(new Date()).impTimeComsum(Long.valueOf((resolveBeginTime-System.currentTimeMillis())/1000).intValue())
                    .impTmpTable(targetTable).impTargetTable(targetTable).status(importStatus)
                    .impSucc(resolveCount.getSuccessCount()).impFail(resolveCount.getFailCount()).impTotal(resolveCount.getTotalCount())
                    .analysisBean("").analysisMethod("")
                    .impMessage(resultMsg)
                    .menuId(null).build());
        }
    }

    private List resolveExcelData_wxBill(Long resId,long impId,String fileName) throws Exception {
        File f = null;
        BufferedReader bufferedReader = null;
        try{
            int beginRow = 0;
            List<WxBill> wxBillList = new ArrayList();
            f = resourceService.getFile(resId,
                    configService.getConfigValue("SYS_TEMP_FILE_PATH")+ File.separator+
                            IdUtil.getUUid()+
                            fileName.substring(fileName.lastIndexOf(".")));
            bufferedReader = new BufferedReader(new FileReader(f));
            int i=0;
            while(true){
                String s = FastCsvUtils.csvReadLine(bufferedReader);
                if(s==null) break;
                if(s.startsWith("交易时间,")){beginRow=i;}
                if(beginRow!=0 && i>beginRow) wxBillList.add(convertWxBill(impId,s.split(",")));
                i++;
            }
//            wxBillMapper.delete(
//                    Wrappers.lambdaQuery(WxBill.class)
//                    .in(WxBill::getTranOrder,wxBillList.stream().map(w->w.getTranOrder()).toArray()));
            wxBillMapper.insertBatch(wxBillList);
            return wxBillList;
        }catch (Exception e){
            throw e;
        }finally {
            if(bufferedReader!=null) bufferedReader.close();
            if(f!=null && f.exists()) f.delete();
        }
    }

    private WxBill convertWxBill(Long impId, String[] strs) {
        WxBill wxBill = WxBill.builder()
                .subId(null)
                .impId(impId)
                .tranDate(handlerMark(StringUtils.strip(strs.length>=0?strs[0]:null)))
                .tranType(handlerMark(StringUtils.strip(strs.length>=1?strs[1]:null)))
                .merchant(handlerMark(StringUtils.strip(strs.length>=2?strs[2]:null)))
                .goods(handlerMark(StringUtils.strip(strs.length>=3?strs[3]:null)))
                .accType(handlerMark(StringUtils.strip(strs.length>=4?strs[4]:null)))
                .amount(handlerMark(StringUtils.strip(strs.length>=5?strs[5]:null)))
                .payType(handlerMark(StringUtils.strip(strs.length>=6?strs[6]:null)))
                .wxStatus(handlerMark(StringUtils.strip(strs.length>=7?strs[7]:null)))
                .tranOrder(handlerMark(StringUtils.strip(strs.length>=8?strs[8]:null)))
                .merchantOrder(handlerMark(StringUtils.strip(strs.length>=9?strs[9]:null)))
                .remark(handlerMark(StringUtils.strip(strs.length>=10?strs[10]:null)))
                .build();
        return wxBill;
    }

    private String handlerMark(String str){
        if(StrUtil.isEmpty(str))return str;
        if(str.matches("^\".*\"$")){
            return str.substring(1,str.length()-1);
        }
        return str;
    }

    /**
     * 导入微信生成的账单信息
     */
    @Override
    public Result importWxBillData(List<Map> wxBillList) throws Exception {
        List<AccInfo> accInfos = wxBill2AccInfo(wxBillList);
        List<String> orders = wxBillList.stream().filter(t->StrUtil.isNotEmpty(t.get("tranOrder")))
                .map(t -> StrUtil.toStr(t.get("tranOrder"))).collect(Collectors.toList());
        if(orders.size()>0){
            List<AccInfo> repeat = accInfoMapper.selectList(Wrappers.lambdaQuery(AccInfo.class)
                    .in(AccInfo::getWxTranOrder,orders));
            if(repeat.size()>0) throw new BusinessException(
                    "微信交易单号["+repeat.stream().map(t->t.getWxTranOrder()).collect(Collectors.joining(","))+"]已经导入，请勿重复导入！");
        }
        accInfoMapper.insertBatch(accInfos);
        return Result.success("导入成功");
    }

    private List<AccInfo> wxBill2AccInfo(List<Map> wxBillList) throws Exception{
        String wxPayType = "4";
        List<AccInfo> list  = new ArrayList<>();
        try{
            for (Map m : wxBillList) {
                list.add(AccInfo.builder()
                        .impId(StrUtil.toLong(m.get("impId")))
                        .accDate(StrUtil.toStr(m.get("tranDate")))
                        .accPeriod(DateUtil.formatDate(DateUtil.parseDatetime(StrUtil.toStr(m.get("tranDate"))),"yyyyMM"))
                        .subId(StrUtil.toStr(m.get("subId")))
                        .amount(new BigDecimal(StringUtils.replace(StrUtil.toStr(m.get("amount")),"¥","")))
                        .paymentType(wxPayType)
                        .digest(StrUtil.toStr(m.get("digest")))
                        .status(StatusEnum.EFFECTIVE.getCode())
                        .wxTranOrder(StrUtil.toStr(m.get("tranOrder")))
                        .build());
            }
        }catch (ParseException e){
            throw new BusinessException("日期转换异常，请联系管理员！");
        }
        return list;
    }

    /**
     * 导出账户列表
     * @param response
     */
    @Override
    public void exportAccountList(AccInfoReq req, HttpServletResponse response) throws Exception {
        if("EXCEL".equalsIgnoreCase(req.getExportType())){
            exportAccountXLSX(req,response);
        }else if("CSV".equalsIgnoreCase(req.getExportType())){
            exportAccountCSV(req,response);
        }else{
            throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        }
    }

    @Override
    public void exporSubjectList(SubInfoReq req, HttpServletResponse response) throws Exception {
        exportSubjectXLSX(req,response);
    }

    /**
     * 导出EXCEL
     */
    private void exportSubjectXLSX(SubInfoReq req, HttpServletResponse response) throws BusinessException, IOException {
        int sysDefaultBatchHandlerSize = StrUtil.toInt(configService.getConfigValue("SYS_DEFAULT_BATCH_HANDLER_SIZE"));
        SXSSFWorkbook sxssfWorkbook = null;
        FileOutputStream out = null;
        RepInfo repInfo = repInfoMapper.selectById(req.getRepId());
        List<RepSheet> sheets = repSheetMapper.selectList(new LambdaQueryWrapper<RepSheet>().eq(RepSheet::getRepId,req.getRepId()))
                .stream().peek(s->s.setSheetName(s.getSheetName())).toList();
        List<RepColumn> columns = repColumnMapper.selectList(new LambdaQueryWrapper<RepColumn>().eq(RepColumn::getRepId,req.getRepId()));

        String fileName = repInfo.getRepName()+"-"+ DateFormatUtils.format(new Date(),"yyyy-MM-dd") + ".xlsx";
        HttpServletResponseUtil.excelResponse(response, fileName);
        try{
            sxssfWorkbook = new SXSSFWorkbook();
            for (RepSheet s : sheets) {
                Sheet sheet = sxssfWorkbook.createSheet(s.getSheetName());
                List<RepColumn> sheetColumns = columns.stream().filter(c->c.getSheetId().equals(s.getSheetId())).toList();
                //输出title
                int nextRow= ExcelUtil.generateSheetTitle(sheet,s,sheetColumns);
                //输出表头
                nextRow = ExcelUtil.generateSheetHeader(sheet,sheetColumns,false,nextRow);
                //输出数据
                int pageNum = 1;
                while (true){
                    List<SubInfo> list = subInfoMapper.getSubInfoList((pageNum-1)*sysDefaultBatchHandlerSize,sysDefaultBatchHandlerSize,req);
                    list.forEach(m->{
                        m.setSubTypeName(SubInfo.SubTypeEnum.getSubTypeName(m.getSubType()));
                    });
                    if(list==null || list.size()<1){break;}
                    nextRow = ExcelUtil.generateSheetData(sheet,sheetColumns,list,nextRow);
                    pageNum++;
                }
            }
            sxssfWorkbook.write(response.getOutputStream());
        }catch (Exception e){
            throw e;
        }finally {
            if(sxssfWorkbook!=null){sxssfWorkbook.close();}
            if(out!=null){out.close();}
        }
    }

    /**
     * 导出CSV
     */
    private void exportAccountCSV(AccInfoReq req, HttpServletResponse response) throws Exception{
        int sysDefaultBatchHandlerSize = StrUtil.toInt(configService.getConfigValue("SYS_DEFAULT_BATCH_HANDLER_SIZE"));
        int pageNum=1;
        File f = null;
        CSVPrinter csvPrinter = null;

        RepInfo repInfo = repInfoMapper.selectById(req.getRepId());
        List<RepColumn> columns = repColumnMapper.selectList(Wrappers.lambdaQuery(RepColumn.class)
                .eq(RepColumn::getRepId,req.getRepId())
                .orderByAsc(RepColumn::getColIndex));
        String fileName = repInfo.getRepName()+"-"+ DateFormatUtils.format(new Date(),"yyyy-MM-dd") + ".csv";
        HttpServletResponseUtil.excelResponse(response, fileName);

        try{
            String tempPath = configService.getConfigValue("SYS_TEMP_FILE_PATH");
            f = new File(tempPath+File.separator+IdUtil.getUUid()+".csv");
            csvPrinter = new CSVPrinter(new FileWriter(f, Charset.forName("UTF-8")),
                    CSVFormat.DEFAULT);
            while (true){
                List<AccInfo> list = accInfoMapper.getAccountList((pageNum-1)*sysDefaultBatchHandlerSize,sysDefaultBatchHandlerSize,req);
                accountEnumConvert(list);
                if(list==null || list.size()<1){break;}
                //输出UTF-8BOM头，否则excel打开乱码
                csvPrinter.print(new String(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}, "UTF-8"));
                csvPrinter.printRecord(columns.stream().map(c->c.getColName()).toArray());
                csvPrinter.printRecords(list.stream().map(a->{
                    JSONObject json = JSONObject.from(a);
                    String[] row = new String[columns.size()];
                    for(int i=0;i<columns.size();i++){
                        row[i]=json.getString(columns.get(i).getColProp());
                    }
                    return row;
                }).toArray());
                pageNum++;
            }
            csvPrinter.flush();
        }catch (Exception e){
            throw e;
        }finally {
            if(csvPrinter!=null){
                csvPrinter.close();
            }
        }


        FileInputStream is = new FileInputStream(f);;
        int len=0;
        byte[] bytes = new byte[1024];
        try{
            while((len=is.read(bytes))!=-1){
                response.getOutputStream().write(bytes,0,len);
            }
        }catch (Exception e){
            throw e;
        }finally {
            if(is!=null){is.close();}
            if(f!=null){f.delete();}
        }
    }

    /**
     * 导出EXCEL
     */
    private void exportAccountXLSX(AccInfoReq req, HttpServletResponse response) throws Exception {
        int sysDefaultBatchHandlerSize = StrUtil.toInt(configService.getConfigValue("SYS_DEFAULT_BATCH_HANDLER_SIZE"));
        SXSSFWorkbook sxssfWorkbook = null;
        FileOutputStream out = null;
        RepInfo repInfo = repInfoMapper.selectById(req.getRepId());
        List<RepSheet> sheets = repSheetMapper.selectList(new LambdaQueryWrapper<RepSheet>().eq(RepSheet::getRepId,req.getRepId()))
                .stream().peek(s->s.setSheetName(s.getSheetName())).toList();
        List<RepColumn> columns = repColumnMapper.selectList(new LambdaQueryWrapper<RepColumn>().eq(RepColumn::getRepId,req.getRepId()));

        String fileName = repInfo.getRepName()+"-"+ DateFormatUtils.format(new Date(),"yyyy-MM-dd") + ".xlsx";
        HttpServletResponseUtil.excelResponse(response, fileName);
        try{
            Result result = getAccountList(req);
            if(result.getTotal()>10000){
                throw new BusinessException("数据量过大，推荐您使用CSV导出!");
            }
            sxssfWorkbook = new SXSSFWorkbook();
            for (RepSheet s : sheets) {
                Sheet sheet = sxssfWorkbook.createSheet(s.getSheetName());
                List<RepColumn> sheetColumns = columns.stream().filter(c->c.getSheetId().equals(s.getSheetId())).toList();
                //输出title
                int nextRow= ExcelUtil.generateSheetTitle(sheet,s,sheetColumns, ReportReq.UnitEnum.YUAN.getCode());
                //输出表头
                nextRow = ExcelUtil.generateSheetHeader(sheet,sheetColumns,false,nextRow);
                //输出数据
                int pageNum = 1;
                while (true){
                    List list = accInfoMapper.getAccountList((pageNum-1)*sysDefaultBatchHandlerSize,sysDefaultBatchHandlerSize,req);
                    accountEnumConvert(list);
                    if(list==null || list.size()<1){break;}
                    nextRow = ExcelUtil.generateSheetData(sheet,sheetColumns,list,nextRow);
                    pageNum++;
                }
            }
            sxssfWorkbook.write(response.getOutputStream());
        }catch (Exception e){
            throw e;
        }finally {
            if(sxssfWorkbook!=null){sxssfWorkbook.close();}
            if(out!=null){out.close();}
        }
    }

}
