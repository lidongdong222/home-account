package com.ldd.home.operate.entity.req;

import com.ldd.home.operate.common.ext.Page;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccInfoReq{

    private String startPeriod;
    private String endPeriod;
    private String subType;
    private String subCode;
    private String subName;
    private String startAccDate;
    private String endAccDate;
    private String digest;
    private String paymentType;
    private String startCreateDate;
    private String endCreateDate;
    private String createUser;
    private String startAmount;
    private String endAmount;
    private Long repId;
    private String exportType;
    /**
     * 页码
     */
    @NotNull(groups = {Page.class})
    Integer pageNum;
    /**
     * 每页大小
     */
    @NotNull(groups = {Page.class})
    Integer pageSize;
    /**
     * 排序 例： name asc,age desc
     */
    String order;

}
