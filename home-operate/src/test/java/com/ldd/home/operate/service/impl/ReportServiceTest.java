package com.ldd.home.operate.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReportServiceTest {


//    @Test
    public void t2() throws IOException {
        File f = new File("D:/3/31.xlsx");
        if(f.exists())f.delete();
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet("1");
        Row row = sheet.createRow(0);
        int i = 0;
        for (IndexedColors value : IndexedColors.values()) {
            Cell cell = row.createCell(i);
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(value.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(value.getIndex());
            i++;
        }
        XSSFColor color = new XSSFColor();
        color.setRGB(new byte[]{(byte) 189, (byte) 215, (byte) 238});
        row = sheet.createRow(1);
        System.out.println(color.getIndex());
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(color.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        row.setRowStyle(cellStyle);
        FileOutputStream fileOutputStream = new FileOutputStream(f);
        workbook.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();

    }
}