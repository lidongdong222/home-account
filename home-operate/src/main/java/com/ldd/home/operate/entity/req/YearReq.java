package com.ldd.home.operate.entity.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearReq {
    @NotBlank(message = "年度不能为空！")
    String year;
}
