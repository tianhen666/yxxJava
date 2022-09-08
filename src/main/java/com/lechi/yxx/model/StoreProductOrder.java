package com.lechi.yxx.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author zf
 * @since 2022-08-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="StoreProductOrder对象", description="订单表")
public class StoreProductOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "店铺id")
    private Integer storeId;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "商品id")
    private Integer productId;

    @ApiModelProperty(value = "商品名")
    private String productName;

    @ApiModelProperty(value = "商品图片")
    private String productPic;

    @ApiModelProperty(value = "商品总价")
    private BigDecimal price;

    @ApiModelProperty(value = "抵用金")
    private BigDecimal creditPrice;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal payPrice;

    private LocalDateTime createDt;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payDt;

    @ApiModelProperty(value = "到期时间")
    private LocalDateTime expireDt;

    @ApiModelProperty(value = "完成时间")
    private LocalDateTime completeDt;

    @ApiModelProperty(value = "0未支付 1已支付 2已使用 3已关闭")
    private Integer status;

    @ApiModelProperty(value = "分销金额")
    private BigDecimal sharePrice;

    @ApiModelProperty(value = "活动id")
    private Integer enrollId;

    @ApiModelProperty(value = "核销码")
    private String code;

    @ApiModelProperty(value = "是否分账订单")
    private Boolean shareProfit;

    @ApiModelProperty(value = "收货地址")
    private String address;

    @ApiModelProperty(value = "邀请人id")
    private Integer inviteUserId;


}
