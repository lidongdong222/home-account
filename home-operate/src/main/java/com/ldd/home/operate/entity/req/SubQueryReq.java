package com.ldd.home.operate.entity.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubQueryReq extends MhcxReq{
    private String subId;
}
