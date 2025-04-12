package com.ldd.home.operate.entity.req;

import com.ldd.home.operate.common.ext.Page;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 分页查询对象
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BaseReq extends MhcxReq{

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
