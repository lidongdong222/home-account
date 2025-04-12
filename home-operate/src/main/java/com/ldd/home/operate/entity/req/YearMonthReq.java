package com.ldd.home.operate.entity.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearMonthReq {

    @NotBlank(message = "日期不能为空！")
    String yearMonth;

}
