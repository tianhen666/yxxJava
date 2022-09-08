package com.lechi.yxx.model;

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
 * 评论表
 * </p>
 *
 * @author zf
 * @since 2022-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="StoreOrderReview对象", description="评论表")
public class StoreOrderReview implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "评论内容")
    private String content;

    private LocalDateTime createDt;

    @ApiModelProperty(value = "是否删除")
    private Boolean flag;

    @ApiModelProperty(value = "是否虚拟")
    private Boolean isVirtual;

    private LocalDateTime modifyDt;

    @ApiModelProperty(value = "订单id")
    private Integer orderId;

    @ApiModelProperty(value = "图片")
    private String pics;

    @ApiModelProperty(value = "评分")
    private Integer rate;

    @ApiModelProperty(value = "店铺id")
    private Integer storeId;

    @ApiModelProperty(value = "目标id（商品、活动id）")
    private Integer targetId;

    @ApiModelProperty(value = "0商品 1活动")
    private Integer type;

    @ApiModelProperty(value = "用户id")
    private Integer userId;


}
