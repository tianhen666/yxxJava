package com.lechi.yxx.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 店铺爆品
 * </p>
 *
 * @author zf
 * @since 2022-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="StoreProduct对象", description="店铺爆品")
public class StoreProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "店铺id")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty(value = "商品名")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "描述")
    @TableField("desc_data")
    private String descData;

    @ApiModelProperty(value = "商品图片")
    @TableField("pics")
    private String pics;

    @ApiModelProperty(value = "到期时间")
    @TableField("expire_day")
    private Integer expireDay;

    @ApiModelProperty(value = "库存")
    @TableField("avaliable_count")
    private Integer avaliableCount;

    @TableField("limit_count")
    private Integer limitCount;

    @ApiModelProperty(value = "价格")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty(value = "折扣价格")
    @TableField("price_normal")
    private BigDecimal priceNormal;

    @ApiModelProperty(value = "使用须知")
    @TableField("notice")
    private String notice;

    @ApiModelProperty(value = "0 仓库 1上架")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_dt")
    private LocalDateTime createDt;

    @ApiModelProperty(value = "修改时间")
    @TableField("modify_dt")
    private LocalDateTime modifyDt;

    @TableField("flag")
    private Boolean flag;

    @ApiModelProperty(value = "排序")
    @TableField("order_num")
    private Integer orderNum;

    @ApiModelProperty(value = "微信分享图")
    @TableField("share_pic")
    private String sharePic;

    @TableField("start_dt")
    private LocalDateTime startDt;

    @TableField("end_dt")
    private LocalDateTime endDt;

    @ApiModelProperty(value = "商品详情")
    @TableField("detail")
    private String detail;

    @ApiModelProperty(value = "上架时间")
    @TableField("sale_dt")
    private LocalDateTime saleDt;

    @ApiModelProperty(value = "分销金额")
    @TableField("share_price")
    private BigDecimal sharePrice;

    @ApiModelProperty(value = "购买须知")
    @TableField("buy_notice")
    private String buyNotice;

    @ApiModelProperty(value = "是否需要发货")
    @TableField("need_express")
    private Boolean needExpress;

    @ApiModelProperty(value = "海报")
    @TableField("post_pic")
    private String postPic;

    @ApiModelProperty(value = "已售数量")
    @TableField("sold")
    private Integer sold;


}
