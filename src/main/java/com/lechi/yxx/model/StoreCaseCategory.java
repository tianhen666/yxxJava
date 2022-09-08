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
 * 店铺案例分类表
 * </p>
 *
 * @author zf
 * @since 2022-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="StoreCaseCategory对象", description="店铺案例分类表")
public class StoreCaseCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "上级id")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value = "店铺id")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty(value = "分类名称")
    @TableField("name")
    private String name;

    @TableField("create_dt")
    private LocalDateTime createDt;

    @TableField("modify_dt")
    private LocalDateTime modifyDt;

    @ApiModelProperty("/是否排序")
    @TableField("sort")
    private Integer sort;


}
