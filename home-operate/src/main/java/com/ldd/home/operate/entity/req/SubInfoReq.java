package com.ldd.home.operate.entity.req;

import com.ldd.home.operate.common.ext.Page;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubInfoReq {

    String repId;
    private String subType;
    private String subCode;
    private String subName;
    private String subCode1;
    private String subName1;
    private String subCode2;
    private String subName2;
    private String startCreateDate;
    private String endCreateDate;
    @NotNull(groups = {Page.class})
    private Integer pageNum;
    @NotNull(groups = {Page.class})
    private Integer pageSize;
}

