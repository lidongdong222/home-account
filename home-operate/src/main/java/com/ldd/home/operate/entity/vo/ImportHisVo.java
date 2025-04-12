package com.ldd.home.operate.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImportHisVo {

    Long impId;
    String impTime;
    String resName;
    int status;
    int impSucc;
    int impFail;
    String resInnerUrl;
    String impProgress;
    String impResult;

}
