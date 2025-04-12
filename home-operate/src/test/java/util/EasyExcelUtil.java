package util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class EasyExcelUtil {

    public static void main(String[] args) {
        long s = new Date().getTime();
//        EasyExcel.read("C:\\Users\\mjh233\\Desktop/2.xlsx", Data.class, new PageReadListener<Data>(dataList -> {
//            dataList.forEach(d ->{
//                System.out.println(JSONObject.toJSONString(d));
//            });
//        })).sheet().doRead();
        EasyExcel.read("C:\\Users\\mjh233\\Desktop/2.xlsx", new NoModelDataListener()).sheet().doRead();
        long e = new Date().getTime();
        System.out.println((e-s)/1000);
    }



}
@Slf4j
class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    private List<Map<Integer, String>> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            log.info(JSONObject.toJSONString(cachedDataList));
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
//        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
//        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
//        log.info("存储数据库成功！");
    }
}