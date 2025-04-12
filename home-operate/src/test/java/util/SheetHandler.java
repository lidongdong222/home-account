package util;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedHashMap;
import java.util.Objects;


/**
 * @description: 重写startElement() endElement() characters() 用于把Excel数据封装到 LinkedHashMap<String, String> 中 * @date: 2021/12/16 15:10
 */
public class SheetHandler extends DefaultHandler {
    private SharedStringsTable sst;
    private String lastContents;
    private boolean nextIsString;
    private String cellPosition;
    private LinkedHashMap<String, String> rowContents = new LinkedHashMap<String, String>();

    public LinkedHashMap<String, String> getRowContents() {
        return rowContents;
    }

    public void setRowContents(LinkedHashMap<String, String> rowContents) {
        this.rowContents = rowContents;
    }

    public SheetHandler(SharedStringsTable sst) {
        this.sst = sst;
    }

    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        if (name.equals("c")) {
            cellPosition = attributes.getValue("r");
            String cellType = attributes.getValue("t");
            if (Objects.nonNull(cellType) && cellType.equals("s")) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }
        }
//        清除緩存內容
        lastContents = "";
    }

    public void endElement(String uri, String localName, String name) throws SAXException {
        int idx = 0;
        if (nextIsString) {
            idx = Integer.parseInt(lastContents);
            lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
            nextIsString = false;
        }
        if (name.equals("v")) {
//            读取数据结束后，将单元格坐标内容放入map中
            rowContents.put(cellPosition, lastContents);
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        lastContents += new String(ch, start, length);
    }
}
    
