package util;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Objects;


/**
 * @description: * @author: laizhenghua * @date: 2022/5/17 20:03
 * 此方法解析可行，需要投入大量的时间和精力才能完善数据的解析，后续再做
 */
public class SAXTest {
    public static void main(String[] args) throws IOException {
        String filePath = "C:\\Users\\mjh233\\Desktop/2.xlsx";
        InputStream sheet = null;
        OPCPackage pkg = null;
        SheetHandler sheetHandler = null;
        try {
            pkg = OPCPackage.open(filePath); /*可以指定一个文件路径/File类/输入流等*/
            XSSFReader reader = new XSSFReader(pkg);
            SharedStringsTable table = reader.getSharedStringsTable();  /*共享字符串表*/
            sheetHandler = new SheetHandler(table);
            XMLReader parser = XMLReaderFactory.createXMLReader("com.sun.org.apache.xerces.internal.parsers.SAXParser");
            parser.setContentHandler(sheetHandler);
            sheet = reader.getSheet("rId1");
            /* rId1是sheet1 rId2是sheet2 以此类推*/
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            /*解析excel的每条记录 在这个过程中startElement() characters() endElement() 这三个函数会依次执行*/
            LinkedHashMap<String, String> rowContents = sheetHandler.getRowContents();
            System.out.println(rowContents);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(sheet)) {
                sheet.close();
            }
            if (Objects.nonNull(pkg)) {
                pkg.close();
            }
        }
    }
}
