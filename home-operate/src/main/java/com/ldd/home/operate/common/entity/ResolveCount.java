package com.ldd.home.operate.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 解析处理条数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResolveCount {
//    全部条数
    int totalCount;
//    成功条数
    int successCount;
//    失败条数
    int failCount;
}
