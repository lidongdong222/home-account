package com.ldd.home.operate.entity.vo;

import com.ldd.home.operate.entity.RepColumn;
import com.ldd.home.operate.entity.RepRow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportInfoVo {
    String title;
    List<Sheet> sheets;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Sheet{
        int sheetId;
        String title;
        List<String> splitHeader;
        List<RepColumn> headers;
        List<RepRow> leftHeaders;
        List data;
    }

}

