package com.lechi.yxx.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 店铺服务表
 * </p>
 *
 * @author zf
 * @since 2022-08-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="StoreServe对象", description="店铺服务表")
@Builder
public class StoreServe implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "门诊id")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty(value = "图片")
    @TableField("pic")
    private String pic;

    @ApiModelProperty(value = "服务名称")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "服务描述")
    @TableField("desc_data")
    private String descData;

    @TableField("create_dt")
    private LocalDateTime createDt;

    @TableField("modify_dt")
    private LocalDateTime modifyDt;

    @ApiModelProperty(value = "是否删除")
    @TableField("flag")
    private Integer flag;

    @ApiModelProperty(value = "排序")
    @TableField("weight")
    private Integer weight;

    @ApiModelProperty(value = "浏览量")
    @TableField("views")
    private Integer views;

    @ApiModelProperty(value = "服务详情")
    @TableField("details")
    private String details;

    @ApiModelProperty(value = "海报")
    @TableField("post_pic")
    private String postPic;

    @ApiModelProperty(value = "分享图")
    @TableField("share_pic")
    private String sharePic;

    @ApiModelProperty(value = "商品id")
    @TableField("product_id")
    private String productId;

    @TableField(exist = false)
    private List<StoreProduct> productList;


}
