package com.ldd.home.operate.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@TableName("resource_info")
public class ResourceInfo {

    /**
     * 主键
     */
    @TableId(value = "res_id", type = IdType.AUTO)
    private Long resId;

    /**
     * 资源存储方式
     */
    @TableField("res_store_type")
    private Integer resStoreType;

    /**
     * 资源名称
     */
    @TableField("res_name")
    private String resName;

    /**
     * 系统资源唯一名称
     */
    @TableField("res_unique_name")
    private String resUniqueName;

    /**
     * 资源大小
     */
    @TableField("res_size")
    private Long resSize;

    /**
     * 上传时间
     */
    @TableField("upload_time")
    private Date uploadTime;

    /**
     * 资源互联网url
     */
    @TableField("res_inner_url")
    private String resInnerUrl;

    /**
     * 资源互联网url
     */
    @TableField("res_internet_url")
    private String resInternetUrl;

    /**
     * 资源所在服务器ip
     */
    @TableField("res_server_ip")
    private String resServerIp;

    /**
     * 资源地址
     */
    @TableField("res_server_path")
    private String resServerPath;

    /**
     * 创建日期
     */
    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 创建用户
     */
    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 更新日期
     */
    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private Date updateDate;

    /**
     * 更新用户
     */
    @TableField(value = "update_user",fill = FieldFill.UPDATE)
    private String updateUser;

    public enum ResStoreTypeEnum{
        LOCAL(1,"本地存储"),
        FTP_SFTP(2,"FTP、SFTP服务存储");

        int code;
        String name;

        ResStoreTypeEnum(int code,String name){
            this.code = code;
            this.name = name;
        }

        public int getCode(){
            return this.code;
        }
    }
}
