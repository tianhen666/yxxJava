package com.lechi.yxx.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 医生信息
 * </p>
 *
 * @author zf
 * @since 2022-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="StoreDoctor对象", description="医生信息")
public class StoreDoctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "门诊")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty(value = "医生姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "照片")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "医生职务")
    @TableField("post")
    private String post;

    @ApiModelProperty(value = "医生简介")
    @TableField("desc_data")
    private String descData;

    @TableField("create_dt")
    private LocalDateTime createDt;

    @ApiModelProperty(value = "权重/排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "医生详情")
    @TableField("detail")
    private String detail;

    @ApiModelProperty(value = "是否删除")
    @TableField("flag")
    private Integer flag;

}
