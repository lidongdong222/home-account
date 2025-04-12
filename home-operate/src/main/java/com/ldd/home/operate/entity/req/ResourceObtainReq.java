package com.ldd.home.operate.entity.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.FileOutputStream;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceObtainReq {
    private Long resId;

    FileOutputStream fos;
}
