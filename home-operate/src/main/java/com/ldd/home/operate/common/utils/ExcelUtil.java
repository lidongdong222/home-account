package com.ldd.home.operate.common.utils;

import com.alibaba.fastjson2.JSONObject;
import com.ldd.home.operate.entity.RepColumn;
import com.ldd.home.operate.entity.RepRow;
import com.ldd.home.operate.entity.RepSheet;
import com.ldd.home.operate.entity.req.ReportReq;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.ShapeTypes;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSimpleShape;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * excel工具类
 */
public class ExcelUtil {

    static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public static final BorderStyle STANDARD_BORDER_STYLE = BorderStyle.THIN;

    /**
     * 将excel文件转换为数据类型
     * @param f 上传文件
     * @param clazz 转换为类
     * @param sheet sheet 读取sheet页 从0开始
     * @param startRow 当前sheet开始行 从0开始
     * @param columns 当前sheet列集合，从左到右依次排序
     * @return
     * @throws IOException
     */
    public static <T> List<T> excel2List(File f, Class<T> clazz, int sheet, int startRow, LinkedList<String> columns) throws IOException {
        FileInputStream is = new FileInputStream(f);
        try{
            return f.getName().toLowerCase().endsWith("xls")?
                    excelXls2List(is,clazz,sheet,startRow,columns):
                    excelXlsx2List(is,clazz,sheet,startRow,columns);
        }finally {
            if(Objects.nonNull(is)) is.close();
        }
    }

    /**
     * 将excel文件转换为数据类型
     * @param f 上传文件
     * @param clazz 转换为类
     * @param sheet sheet 读取sheet页
     * @param startRow 当前sheet开始行
     * @param columns 当前sheet列集合，从左到右依次排序
     * @return
     * @throws IOException
     */
    public static List excel2List(MultipartFile f,Class clazz,int sheet, int startRow, LinkedList<String> columns) throws IOException {
        return f.getOriginalFilename().toLowerCase().endsWith("xls")?
                excelXls2List(f.getInputStream(),clazz,sheet,startRow,columns):
                excelXlsx2List(f.getInputStream(),clazz,sheet,startRow,columns);
    }

    /**
     * 将excel文件转换为数据类型,该方法不会关闭任何流，请您在创建流的地方关闭流，避免混乱
     * @param is 文件流
     * @param clazz 转换为类
     * @param sheetIndex sheet 读取sheet页
     * @param startRow 当前sheet开始行
     * @param columns 当前sheet列集合，从左到右依次排序
     * @return
     * @throws IOException
     */
    public static List excelXls2List(InputStream is, Class clazz, int sheetIndex, int startRow, LinkedList<String> columns) throws IOException {
        long sTime = new Date().getTime();
        HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(is));
        List list = analysisWorkbook2List(workbook,sheetIndex,clazz,startRow,columns);
        long eTime = new Date().getTime();
        logger.info("解析excel共耗时{}秒",(eTime-sTime)/1000);
        return list;
    }



    /**
     * 将excel文件转换为数据类型
     * @param is 文件流
     * @param clazz 转换为类
     * @param sheetIndex sheet 读取sheet页
     * @param startRow 当前sheet开始行
     * @param columns 当前sheet列集合，从左到右依次排序
     * @return
     * @throws IOException
     */
    public static List excelXlsx2List(InputStream is,Class clazz,int sheetIndex,int startRow, LinkedList<String> columns) throws IOException {
        long sTime = new Date().getTime();
        SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(is));
        long eTime = new Date().getTime();
        logger.info("读取excel共耗时{}秒",(eTime-sTime)/1000);
        List list = analysisWorkbook2List(workbook,sheetIndex,clazz,startRow,columns);
        eTime = new Date().getTime();
        logger.info("解析excel共耗时{}秒",(eTime-sTime)/1000);
        return list;
    }

    /**
     * @param sheetIndex    解析sheet页
     * @param clazz    泛型
     * @param startRow 当前sheet开始行
     * @param columns  sheet页解析列
     * @return
     */
    private static List analysisWorkbook2List(Workbook workbook, int sheetIndex, Class clazz, int startRow, LinkedList<String> columns) throws IOException {
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        ArrayList result = new ArrayList();
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        //sheet列最大的行数
        int maxRow = sheet.getLastRowNum();
        logger.info("解析excel共{}行",maxRow);
        //sheet列最大的列数
        int maxCol = StrUtil.isEmpty(sheet.getRow(0))?0:sheet.getRow(0).getLastCellNum();
        for(int i=startRow;i<maxRow;i++){
            Row r = sheet.getRow(i);
            JSONObject tmp = new JSONObject();
            boolean b = true;
            for(int j=0;j<(maxCol<columns.size()?maxCol:columns.size());j++){
                Cell cell = r.getCell(j);
                if(Objects.isNull(cell)) break;
                Object value = getCellValue(evaluator,cell);
                if(Objects.isNull(value)) break;
                b = false;
                tmp.put(columns.get(j),value);
            }
            if(b) break;
            result.add(tmp.toJavaObject(clazz));
        }
        return result;
    }

    /**
     * 获取cellValue值
     * @param evaluator
     * @param cell
     * @return
     */
    private static Object getCellValue(FormulaEvaluator evaluator, Cell cell) {
        switch (cell.getCellTypeEnum()){
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return DateUtil.isCellDateFormatted(cell)?cell.getDateCellValue():cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                CellValue cellValue = evaluator.evaluate(cell);
                switch (cellValue.getCellTypeEnum()){
                    case STRING:
                        return cellValue.getStringValue();
                    case NUMERIC:
                        return cellValue.getNumberValue();
                    case BOOLEAN:
                        return cellValue.getBooleanValue();
                    default:
                        return null;
                }
            default:
                return null;
        }
    }

    /**
     * 创建sheet页title样式
     * @return
     */
    public static CellStyle createCellStyleTitle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        //字体
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontName("仿宋");
        font.setFontHeight((short) 350);
        cellStyle.setFont(font);
        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //设置背景色 header使用PALE_BLUE TITLE使用
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());

        return cellStyle;
    }

    /**
     * 创建sheet页title样式
     * @return
     */
    public static CellStyle createCellStyleHeader(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        //字体
        Font font = workbook.createFont();
        font.setFontName("仿宋");
        font.setBold(true);
        font.setFontHeight((short) 250);
        cellStyle.setFont(font);

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //设置背景色 header使用PALE_BLUE TITLE使用
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());

        //边框
        cellStyle.setBorderBottom(STANDARD_BORDER_STYLE);
        cellStyle.setBorderLeft(STANDARD_BORDER_STYLE);
        cellStyle.setBorderRight(STANDARD_BORDER_STYLE);
        cellStyle.setBorderTop(STANDARD_BORDER_STYLE);


        return cellStyle;
    }

    /**
     * 创建提示信息（红色字体）
     */
    public static CellStyle createCellStyleTip(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        //字体
        Font font = workbook.createFont();
        font.setFontName("仿宋");
        font.setFontHeight((short) 200);
        font.setColor(IndexedColors.RED.getIndex());
        cellStyle.setFont(font);

        //局左显示
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.TOP);

        //设置背景色 header使用PALE_BLUE TITLE使用
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());

        //边框
        return cellStyle;
    }

    /**
     * 创建红色字体
     * @return
     */
    public static Font createCellStyleCare(Workbook workbook) {
        //红色提示字体
        Font font = workbook.createFont();
        font.setFontName("仿宋");
        font.setFontHeight((short) 250);
        font.setColor(IndexedColors.RED.getIndex());
        return font;
    }

    /**
     * 创建正常黑色字体
     * @return
     */
    public static Font createCellStyleNormal(Workbook workbook) {
        //红色提示字体
        Font font = workbook.createFont();
        font.setFontName("仿宋");
        font.setFontHeight((short) 250);
        font.setBold(true);
        return font;
    }

    /**
     * 创建金额样式
     * @return
     */
    public static CellStyle createCellStyleDataAmt(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        //字体
        Font font = workbook.createFont();
        font.setFontName("仿宋");
        font.setFontHeight((short) 250);
        cellStyle.setFont(font);

        //局左显示
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //边框
        cellStyle.setBorderBottom(STANDARD_BORDER_STYLE);
        cellStyle.setBorderLeft(STANDARD_BORDER_STYLE);
        cellStyle.setBorderRight(STANDARD_BORDER_STYLE);
        cellStyle.setBorderTop(STANDARD_BORDER_STYLE);

        //边框
        return cellStyle;
    }

    /**
     * 创建正文黑色数据样式
     * @return
     */
    public static CellStyle createCellStyleDataText(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        //字体
        Font font = workbook.createFont();
        font.setFontName("仿宋");
        font.setFontHeight((short) 250);
        cellStyle.setFont(font);

        //局左显示
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //边框
        cellStyle.setBorderBottom(STANDARD_BORDER_STYLE);
        cellStyle.setBorderLeft(STANDARD_BORDER_STYLE);
        cellStyle.setBorderRight(STANDARD_BORDER_STYLE);
        cellStyle.setBorderTop(STANDARD_BORDER_STYLE);

        //边框
        return cellStyle;
    }

    /**
     * 生成sheet页表title
     *
     * @param sheet
     * @param repSheet
     * @param sheetColumns
     */
    public static int generateSheetTitle(Sheet sheet, RepSheet repSheet, List sheetColumns) {
        return generateSheetTitle(sheet,repSheet,sheetColumns,null);
    }

    /**
     * 生成sheet页表title
     *
     * @param sheet
     * @param repSheet
     * @param sheetColumns
     */
    public static int generateSheetTitle(Sheet sheet, RepSheet repSheet, List sheetColumns,String unit) {
//        渲染行
        int nextRow = 0;

        CellStyle cellStyleTip = ExcelUtil.createCellStyleTip(sheet.getWorkbook());
        //        title合并单元格
        CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 0, sheetColumns.size() - 1);
        sheet.addMergedRegion(cellAddresses);

        Row row = sheet.createRow(nextRow);
        row.setHeight((short) (40*20));
        Cell cell = Objects.isNull(row.getCell(0))?row.createCell(0):row.getCell(0);
        cell.setCellStyle(ExcelUtil.createCellStyleTitle(sheet.getWorkbook()));
        cell.setCellValue(repSheet.getSheetName());

        //合并单元格设置边框要设置在字体之后，否则否则边框不生效
        RegionUtil.setBorderRight(ExcelUtil.STANDARD_BORDER_STYLE,cellAddresses,sheet);

        //        创建提示信息行和金额单位行，
        row = sheet.createRow(++nextRow);
//        提示信息表头使用较小字体，行高具体情况需要根据提示信息决定，暂写死
        row.setHeight((short) (20*20));
//        最后两列是单位，前面所有格合并单元格为提示信息
        cellAddresses = new CellRangeAddress(1, 1, 0, sheetColumns.size() - 3);
        sheet.addMergedRegion(cellAddresses);
        cell = Objects.isNull(row.getCell(0))?row.createCell(0):row.getCell(0);
        cell.setCellStyle(cellStyleTip);
        //合并单元格设置边框要设置在字体之后，否则否则边框不生效
        RegionUtil.setBorderBottom(ExcelUtil.STANDARD_BORDER_STYLE,cellAddresses,sheet);
        cell.setCellValue("导出时间："+ DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
//        “单位：”单元格
        cell = row.createCell(sheetColumns.size() - 2);
        if(unit!=null)cell.setCellValue("单位：");
        cellStyleTip.setAlignment(HorizontalAlignment.RIGHT);
        cell.setCellStyle(cellStyleTip);

        //“元,万元”单元格
        cell = row.createCell(sheetColumns.size() - 1);
        if(unit!=null)cell.setCellValue(ReportReq.UnitEnum.getUnitName(unit));
        CellStyle cellStyleYuan = ExcelUtil.createCellStyleTip(sheet.getWorkbook());
        cellStyleYuan.setAlignment(HorizontalAlignment.LEFT);
        cell.setCellStyle(cellStyleYuan);
        return ++nextRow;
    }

    /**
     * 生成sheet页表title
     *
     * @param sheet
     * @param repSheet
     * @param sheetColumns
     */
    public static int generateSheetTitleForTemplate(Sheet sheet, RepSheet repSheet, List<RepColumn> sheetColumns) {
//        渲染行
        int nextRow = 0;

        CellStyle cellStyleTip = ExcelUtil.createCellStyleTip(sheet.getWorkbook());
        //        title合并单元格
        CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 0, sheetColumns.size() - 1);
        sheet.addMergedRegion(cellAddresses);

        Row row = sheet.createRow(nextRow);
        row.setHeight((short) (40*20));
        Cell cell = Objects.isNull(row.getCell(0))?row.createCell(0):row.getCell(0);
        cell.setCellStyle(ExcelUtil.createCellStyleTitle(sheet.getWorkbook()));
        cell.setCellValue(repSheet.getSheetName());

        //合并单元格设置边框要设置在字体之后，否则否则边框不生效
        RegionUtil.setBorderRight(ExcelUtil.STANDARD_BORDER_STYLE,cellAddresses,sheet);

        //        创建提示信息行和金额单位行，
        row = sheet.createRow(++nextRow);
//        提示信息表头使用较小字体，行高具体情况需要根据提示信息决定，暂写死
        row.setHeight((short) (20*20));
//        最后两列是单位，前面所有格合并单元格为提示信息
        cellAddresses = new CellRangeAddress(1, 1, 0, sheetColumns.size() - 3);
        sheet.addMergedRegion(cellAddresses);
        cell = Objects.isNull(row.getCell(0))?row.createCell(0):row.getCell(0);
        cell.setCellStyle(cellStyleTip);
        //合并单元格设置边框要设置在字体之后，否则否则边框不生效
        RegionUtil.setBorderBottom(ExcelUtil.STANDARD_BORDER_STYLE,cellAddresses,sheet);
        cell.setCellValue("注：*为必填项，导入时请关注金额单位！");
//        “单位：”单元格
        cell = row.createCell(sheetColumns.size() - 2);
        cell.setCellValue("单位：");
        cellStyleTip.setAlignment(HorizontalAlignment.RIGHT);
        cell.setCellStyle(cellStyleTip);

            //        设置数据有效性验证
        DataValidationHelper validationHelper = sheet.getDataValidationHelper();
        DataValidationConstraint validationConstraint =
                validationHelper.createExplicitListConstraint(new String[]{"元", "万元", "亿元"});
        CellRangeAddressList rangeAddressList = new CellRangeAddressList(1,1,sheetColumns.size() - 1,sheetColumns.size() - 1);
        DataValidation validation = validationHelper.createValidation(validationConstraint,rangeAddressList);
//        设置单元格只能是列表中的内容，否则报错
        validation.setSuppressDropDownArrow(true);
        validation.setShowErrorBox(true);
        sheet.addValidationData(validation);

//      “元,万元”单元格
        cell = row.createCell(sheetColumns.size() - 1);
        cell.setCellValue(ReportReq.UnitEnum.YUAN.getName());
        CellStyle cellStyleYuan = ExcelUtil.createCellStyleTip(sheet.getWorkbook());
        cellStyleYuan.setAlignment(HorizontalAlignment.LEFT);
        cell.setCellStyle(cellStyleYuan);

        return ++nextRow;
    }

    /**
     * 生成表头列
     *
     * @param sheet
     * @param sheetColumns
     * @param headerRow
     */
    public static int generateSheetHeader(Sheet sheet, List<RepColumn> sheetColumns, boolean isTemplate, final int headerRow) {

        //多级表头处理
        int nextRow = headerRow;
        //        表头
        Row row = sheet.createRow(nextRow);
        row.setHeight((short) (sheetColumns.get(0).getHeight()*20));
        int i = 0;
        for (RepColumn t : sheetColumns) {
            if(t.getRowSpan()==0 || t.getColSpan()==0){continue;}
            if(t.getRowSpan()>1 || t.getColSpan()>1){
                createMergeCell(sheet,nextRow,nextRow+t.getRowSpan()-1,i,i+t.getColSpan()-1);
            }
            Cell cell = row.createCell(i);
            sheet.setColumnWidth(i,StrUtil.toInt(t.getWidth(),150)*50);
            cell.setCellStyle(ExcelUtil.createCellStyleHeader(sheet.getWorkbook()));
            if(isTemplate && RepColumn.NecessaryStatus.YES.getValue().equals(t.getIsNecessary())){
                /**
                 * SXSSFWorkbook导出会直接忽略富文本样式XSSFRichTextString
                 * 导出模板时使用XSSFWorkbook,导出大量数据时使用SXSSFWorkbook
                 */
                XSSFRichTextString xssfRichTextString = new XSSFRichTextString("*"+t.getColName());
                xssfRichTextString.applyFont(0,1,ExcelUtil.createCellStyleCare(sheet.getWorkbook()));
                xssfRichTextString.applyFont(1,xssfRichTextString.length(),ExcelUtil.createCellStyleNormal(sheet.getWorkbook()));
                cell.setCellValue(xssfRichTextString);
            }else{
                cell.setCellValue(t.getColName());
            }
            i+=t.getColSpan();
        }
        return ++nextRow;
    }

    /**
     * 生成表数据
     *
     * @param sheet
     * @param columns
     * @param startRow 数据写入的开始行，行从0开始
     */
    public static int generateSheetData(Sheet sheet, List<RepColumn> columns, List list, int startRow) {
        CellStyle cellStyleText = ExcelUtil.createCellStyleDataText(sheet.getWorkbook());
        CellStyle cellStyleAmt = ExcelUtil.createCellStyleDataAmt(sheet.getWorkbook());

        //save start row number of every col after merged
        Map<Integer,Integer> columnMergeStartRowMap = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            JSONObject obj = JSONObject.from(list.get(i));
            Row row = sheet.getRow(startRow+i);
            if(row==null){row =sheet.createRow(startRow+i);};
            for (int j = 0; j < columns.size(); j++) {
                int mergeRow = startRow+i;
                //Initialize the first row number to row number of the data list
                if(!columnMergeStartRowMap.containsKey(j)){columnMergeStartRowMap.put(j,startRow);}
                String colProp = columns.get(j).getColProp();
                if(startRow+i<columnMergeStartRowMap.get(j)){continue;}
                //if the cell is in merged column data mode,all adjacent data that are the same in the current column
                //will be merged
                if(Objects.equals(columns.get(j).getIsDataMergeCell(),"1")
                        && columnMergeStartRowMap.get(j)<=mergeRow){
                    for(int k=i+1;k<list.size();k++){
                        JSONObject next = JSONObject.from(list.get(k));
                        if(Objects.equals(next.get(colProp),obj.get(colProp))){
                            mergeRow=startRow+k;
                        }else{
                            break;
                        }
                    }
                    if(mergeRow!=columnMergeStartRowMap.get(j)){
                        createMergeCell(sheet,columnMergeStartRowMap.get(j),mergeRow,j,j);
                        columnMergeStartRowMap.put(j,mergeRow+1);
                    }
                }
                if(StrUtil.isEmpty(columns.get(j).getColProp())){continue;}
                Cell cell = row.getCell(j);
                if(cell==null){cell=row.createCell(j);}
                if(RepColumn.ColDataType.NUMBER.getValue().equalsIgnoreCase(columns.get(j).getColDataType())){
                    cell.setCellStyle(cellStyleAmt);
                }else{
                    cell.setCellStyle(cellStyleText);
                }
                if(Objects.equals(columns.get(j).getIsNumberFormat(),"1")
                        && MathUtil.isMath(obj.getString(columns.get(j).getColProp()))){
                    cell.setCellValue(String.format("%.2f",obj.getDouble(columns.get(j).getColProp())));
                }else{
                    //解决报表出现的 "--" 问题
                    cell.setCellValue(obj.getString(columns.get(j).getColProp()));
                }
            }
        }
        return startRow+list.size();
    }

    /**
     * 创建分割表头
     * @param sheet
     * @param splitHeader
     * @param nextRow
     */
    public static void generateSheetSplitHeader(Sheet sheet, List<String> splitHeader, int nextRow) {
        Row row = sheet.getRow(nextRow-1);
        if(row==null){
            row=sheet.createRow(nextRow-1);
        }
        row.setHeight((short) (18*20*2));
        Cell cell = row.getCell(0);
        if(cell==null){
            cell = row.createCell(0);
        }

        int startRow=nextRow-1,endRow=nextRow-1,startColumn=0,endColumn=0;
        for (CellRangeAddress address : sheet.getMergedRegions()) {
            if(address.isInRange(cell)){
                startRow = address.getFirstRow();
                endRow = address.getLastRow();
                startColumn = address.getFirstColumn();
                endColumn = address.getLastColumn();
                break;
            }
        }

        CellStyle cellStyle = ExcelUtil.createCellStyleHeader(sheet.getWorkbook());
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setWrapText(true);
        cell.setCellStyle(cellStyle);
        StringBuffer leftSpaceStr = new StringBuffer();
        int splitColumnWidth =0;
        for(int i=startColumn;i<=endColumn;i++){
            splitColumnWidth+=sheet.getColumnWidth(i);
        }
        int spaceTimes = splitColumnWidth/334-splitHeader.get(0).length()*2;
        for (int i = 0; i < spaceTimes; i++) {
            leftSpaceStr.append(" ");
        }
        XSSFRichTextString richTextString = new XSSFRichTextString(leftSpaceStr.append(splitHeader.get(0)).append("\n").toString());
        richTextString.append(splitHeader.get(1));
        cell.setCellValue(richTextString);
        // 创建分割线
        XSSFDrawing drawing = (XSSFDrawing)sheet.createDrawingPatriarch();
        XSSFSimpleShape shape = drawing.createSimpleShape(drawing.createAnchor(0, 0, 0, 0, startColumn, startRow,endColumn+1,endRow+1 ));
        shape.setLineStyleColor(0,0,0);
        shape.setLineWidth(0.5);
        shape.setShapeType(ShapeTypes.LINE);
    }

    /**
     * 创建左边表头
     * @param sheet
     * @param leftHeader
     * @param nextRow
     */
    public static void generateSheetLeftHeader(Sheet sheet, List<RepRow> leftHeader,int nextRow) {
        for (int i = 0; i < leftHeader.size(); i++) {
            RepRow repRow= leftHeader.get(i);
            Row row=sheet.getRow(nextRow+i);
            if(row==null){row=sheet.createRow(nextRow+i);}
            Cell cell = row.getCell(repRow.getHeaderIndex()-1);
            if(cell==null){cell=row.createCell(repRow.getHeaderIndex()-1);}
            cell.setCellStyle(createCellStyleHeader(sheet.getWorkbook()));
            cell.setCellValue(repRow.getRowName());
        }
    }

    public static void createMergeCell(Sheet sheet,int firstRow,int lastRow,int firstCol,int lastCol){
        CellRangeAddress cellAddresses = new CellRangeAddress(firstRow,lastRow,firstCol,lastCol);
        sheet.addMergedRegion(cellAddresses);
    }


}
