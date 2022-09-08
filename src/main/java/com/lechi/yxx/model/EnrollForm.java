package com.lechi.yxx.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 报名表单
 * </p>
 *
 * @author zf
 * @since 2022-07-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="EnrollForm对象", description="报名表单")
public class EnrollForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "门诊id")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty(value = "活动名称")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "活动介绍")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "0上线1未上线2结束")
    @TableField("status")
    private Integer status;

    @TableField("create_dt")
    private LocalDateTime createDt;

    @ApiModelProperty(value = "封面图")
    @TableField("main_pic")
    private String mainPic;

    @ApiModelProperty(value = "图片")
    @TableField("imgs")
    private String imgs;

    @ApiModelProperty(value = "海报")
    @TableField("post_pic")
    private String postPic;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "开始时间")
    @TableField("start_dt")
    private LocalDateTime startDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    @TableField("end_dt")
    private LocalDateTime endDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "发布时间")
    @TableField("post_dt")
    private LocalDateTime postDt;

    @ApiModelProperty(value = "商品金额")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty(value = "单独购买价格")
    @TableField("alone_price")
    private BigDecimal alonePrice;


    @ApiModelProperty(value = "分销金额")
    @TableField("share_price")
    private BigDecimal sharePrice;

    @ApiModelProperty(value = "分享图")
    @TableField("share_pic")
    private String sharePic;

    @ApiModelProperty(value = "是否展示分销")
    @TableField("show_share")
    private Integer showShare;

    @ApiModelProperty(value = "是否删除1删除")
    @TableField("flag")
    private Integer flag;

    @ApiModelProperty(value = "活动类型 0义诊,1限时，2团购")
    private Integer type;

    @ApiModelProperty(value = "最低数(最低成团人数)")
    private Integer least;

    @ApiModelProperty(value = "活动数量")
    @TableField("quantity")
    private Integer quantity;

    @ApiModelProperty(value = "限购数量")
    @TableField("limit_count")
    private Integer limitCount;

    @ApiModelProperty(value = "商品id")
    @TableField("product_id")
    private Integer productId;

    @ApiModelProperty(value = "已售数量")
    @TableField("sold")
    private Integer sold;

    @ApiModelProperty(value = "活动详情")
    @TableField("details")
    private String details;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;



}
