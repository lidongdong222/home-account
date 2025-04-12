package com.ldd.home.operate.entity.resp;

import lombok.*;

import java.util.Date;

/**
 * <p>
 * 资源信息表
 * </p>
 *
 * @author ldd
 * @since 2024-04-30
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceInfoResp {

    /**
     * 主键
     */
    private Long resId;
//    业务id
    private String busiId;

    /**
     * 资源名称
     */
    private String resName;

    /**
     * 资源大小
     */
    private Integer resSize;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 资源互联网url
     */
    private String resUrl;

}
