package com.ldd.home.operate.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.utils.ExcelUtil;
import com.ldd.home.operate.common.utils.HttpServletResponseUtil;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.common.utils.VeriableUtil;
import com.ldd.home.operate.entity.RepColumn;
import com.ldd.home.operate.entity.RepInfo;
import com.ldd.home.operate.entity.RepRow;
import com.ldd.home.operate.entity.RepSheet;
import com.ldd.home.operate.entity.req.ReportReq;
import com.ldd.home.operate.entity.vo.ReportInfoVo;
import com.ldd.home.operate.mapper.RepColumnMapper;
import com.ldd.home.operate.mapper.RepInfoMapper;
import com.ldd.home.operate.mapper.RepRowMapper;
import com.ldd.home.operate.mapper.RepSheetMapper;
import com.ldd.home.operate.service.IReportService;
import com.ldd.home.operate.service.IResourceService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    RequestMappingHandlerMapping handlerMapping;

    @Autowired
    private RepInfoMapper repInfoMapper;
    @Autowired
    private RepSheetMapper repSheetMapper;
    @Autowired
    private RepColumnMapper repColumnMapper;
    @Autowired
    private RepRowMapper repRowMapper;
    @Autowired
    IResourceService resourceService;

    @Autowired
    SqlSession sqlSession;

    @Autowired
    DataSource dataSource;

    /**
     * 获取报表列定义信息
     */
    @Override
    public Result getRepColumns(String repId) {
        return Result.successList("查询成功！",repColumnMapper.selectList(
                new LambdaQueryWrapper<RepColumn>().eq(RepColumn::getRepId,repId).orderByAsc(RepColumn::getColIndex)));
    }

    /**
     * 获取报表对应的表头 sheet页、列定义信息，生成模板文件
     * @param id
     * @param response
     */
    @Override
    public void reportTemplate(String id, HttpServletResponse response) throws Exception {
        RepInfo repInfo = repInfoMapper.selectById(id);
        List<RepSheet> sheets = repSheetMapper.selectList(Wrappers.lambdaQuery(RepSheet.class)
                .eq(RepSheet::getRepId,id)
                .orderByAsc(RepSheet::getSheetIndex));
        List<RepColumn> columns = repColumnMapper.selectList(Wrappers.lambdaQuery(RepColumn.class)
                .eq(RepColumn::getRepId,id)
                .orderByAsc(RepColumn::getColIndex));
        String fileName = repInfo.getRepName()+"_导入模板" + ".xlsx";

        HttpServletResponseUtil.excelResponse(response, fileName);
        Workbook workbook = new SXSSFWorkbook();
        try {
            for (RepSheet s : sheets) {
                Sheet sheet = workbook.createSheet(s.getSheetName());
                List<RepColumn> sheetColumns = columns.stream().filter(c->c.getSheetId().equals(s.getSheetId())).toList();
                //输出title
                int nextRow= ExcelUtil.generateSheetTitleForTemplate(sheet,s,sheetColumns);
                //输出表头
                ExcelUtil.generateSheetHeader(sheet,sheetColumns,false,nextRow);
            }
            workbook.write(response.getOutputStream());
        }catch (Exception e){
            throw e;
        }finally {
            workbook.close();
        }
    }

    /**
     * 获取报表定义信息
     */
    @Override
    public Result getReportInfo(ReportReq req) throws Exception {
        JSONObject requestParam = JSONObject.parseObject(req.getRequestParam());

        RepInfo repInfo = repInfoMapper.selectById(req.getRepId());
        List<RepSheet> sheets = repSheetMapper.selectList(Wrappers.lambdaQuery(RepSheet.class)
                .eq(RepSheet::getRepId,req.getRepId())
                .orderByAsc(RepSheet::getSheetIndex));
        List<RepColumn> columns = repColumnMapper.selectList(Wrappers.lambdaQuery(RepColumn.class)
                .eq(RepColumn::getRepId,req.getRepId())
                .orderByAsc(RepColumn::getColIndex));
        List<RepRow> leftHeader  = repRowMapper.selectList(Wrappers.lambdaQuery(RepRow.class)
                .eq(RepRow::getRepId,req.getRepId()).orderByAsc(RepRow::getRowIndex));

        repInfo.setRepName(VeriableUtil.replaceVariable(repInfo.getRepName(),requestParam));
        sheets.forEach(s->s.setTitle(VeriableUtil.replaceVariable(s.getTitle(),requestParam)));

        List<ReportInfoVo.Sheet> sheetInfos = new ArrayList();
        for (RepSheet sheet : sheets) {
            //获取分割表头
            List<String> splitHeader = new ArrayList<>();
            Iterator iterator = columns.iterator();
            if(iterator.hasNext()){
                RepColumn r = (RepColumn) iterator.next();
                if(StrUtil.isNotEmpty(r.getColName()) && r.getColName().contains("|")){
                    splitHeader.addAll(Arrays.stream(r.getColName().split("\\|")).toList());
                }
            }
            List data = getReportData(req.getRepId(),requestParam);

            sheetInfos.add(ReportInfoVo.Sheet.builder()
                    .title(sheet.getTitle())
                    .sheetId(sheet.getSheetId())
                    .headers(columns)
                    .leftHeaders(leftHeader)
                    .splitHeader(splitHeader)
                    .data(data)
                    .build());
        }
        return Result.successData("查询成功", ReportInfoVo.builder()
                .title(repInfo.getRepName())
                .sheets(sheetInfos).build());
    }

    private List getReportData(long repId,JSONObject params) throws SQLException, BusinessException {
        List<RepSheet> sheets = repSheetMapper.selectList(Wrappers.lambdaQuery(RepSheet.class)
                .eq(RepSheet::getRepId,repId));
        for (RepSheet sheet : sheets) {
            if(StrUtil.isNotEmpty(sheet.getDealSql())
                    && Objects.equals(sheet.getDealType(), RepSheet.DealTypeEnum.SQL.getCode())){
                return getReportData_SQL(sheet.getDealSql(),params);
            }
        }
        return Collections.emptyList();
    }


    private List getReportData_SQL(String dealSql, JSONObject params) throws SQLException, BusinessException {
        List paramList = new ArrayList();
        while(dealSql.contains("#{")){
            int paramBeginIndex = dealSql.indexOf("#{");
            int paramEndIndex = dealSql.indexOf("}",paramBeginIndex+2);
            String key  = StringUtils.trim(dealSql.substring(paramBeginIndex+2,paramEndIndex));
            if(params.containsKey(key)){
                paramList.add(params.get(key));
            }
            dealSql = StringUtils.replaceOnce(dealSql,dealSql.substring(paramBeginIndex,paramEndIndex+1),"?");
        }
        dealSql = VeriableUtil.replaceVariable(dealSql, params);
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(dealSql);
        for(int i=0;i<paramList.size();i++){
            ps.setObject(i+1,paramList.get(i));
        }
        ResultSet rs = ps.executeQuery();
        List result =  convertResultSetToList(rs);
        rs.close();
        ps.close();
        connection.close();
        return result;
    }

    private static List<Map<String, Object>> convertResultSetToList(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                row.put(rsmd.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }
        return list;
    }

    /**
     * 导出报表
     */
    @Override
    public void exportStatistics(ReportReq req, HttpServletResponse response) throws Exception {
        JSONObject requestParam = JSONObject.parseObject(req.getRequestParam());
        String unit = requestParam.getString("unit");
        RepInfo repInfo = repInfoMapper.selectById(req.getRepId());
        List<RepSheet> sheets = repSheetMapper.selectList(Wrappers.lambdaQuery(RepSheet.class)
                .eq(RepSheet::getRepId,req.getRepId())
                .orderByAsc(RepSheet::getSheetIndex));
        List<RepColumn> columns = repColumnMapper.selectList(Wrappers.lambdaQuery(RepColumn.class)
                .eq(RepColumn::getRepId,req.getRepId())
                .orderByAsc(RepColumn::getColIndex));
        List<RepRow> leftHeader  = repRowMapper.selectList(Wrappers.lambdaQuery(RepRow.class)
                .eq(RepRow::getRepId,req.getRepId()).orderByAsc(RepRow::getRowIndex));

        repInfo.setRepName(VeriableUtil.replaceVariable(repInfo.getRepName(),requestParam));
        sheets.forEach(s->s.setTitle(VeriableUtil.replaceVariable(s.getTitle(),requestParam)));

        String fileName = repInfo.getRepName()+"_"+ DateFormatUtils.format(new Date(),"yyyyMMdd") + ".xlsx";

        //获取分割表头
        List<String> splitHeader = new ArrayList<>();
        Iterator iterator = columns.iterator();
        if(iterator.hasNext()){
            RepColumn r = (RepColumn) iterator.next();
            if(StrUtil.isNotEmpty(r.getColName()) && r.getColName().contains("|")){
                splitHeader.addAll(Arrays.stream(r.getColName().split("\\|")).toList());
            }
        }

        HttpServletResponseUtil.excelResponse(response, fileName);
        Workbook workbook = new XSSFWorkbook();
        try {
            for (RepSheet s : sheets) {
                Sheet sheet = workbook.createSheet(s.getSheetName());
                List<RepColumn> sheetColumns = columns.stream().filter(c->c.getSheetId().equals(s.getSheetId())).toList();
                //输出title
                int nextRow= ExcelUtil.generateSheetTitle(sheet,s,sheetColumns,unit);
                //输出表头
                nextRow = ExcelUtil.generateSheetHeader(sheet,sheetColumns,false,nextRow);
                ExcelUtil.generateSheetSplitHeader(sheet,splitHeader,nextRow);
                ExcelUtil.generateSheetLeftHeader(sheet,leftHeader,nextRow);
                List data = getReportData(req.getRepId(),JSONObject.parseObject(req.getRequestParam()));
                nextRow = ExcelUtil.generateSheetData(sheet,sheetColumns,data,nextRow);

            }
            workbook.write(response.getOutputStream());
        }catch (Exception e){
            throw e;
        }finally {
            workbook.close();
        }
    }
}
