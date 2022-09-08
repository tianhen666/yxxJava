package com.lechi.yxx.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zf
 * @since 2022-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="StoreCase对象", description="")
public class StoreCase implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "店铺id")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty(value = "分类Id")
    @TableField("category_id")
    private Integer categoryId;

    @ApiModelProperty(value = "案例标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "主图")
    @TableField("main_pic")
    private String mainPic;

    @ApiModelProperty(value = "案例详情")
    @TableField("detail")
    private String detail;

    @ApiModelProperty(value = "治疗前描述")
    @TableField("before_desc")
    private String beforeDesc;

    @ApiModelProperty(value = "治疗前图片")
    @TableField("before_pics")
    private String beforePics;

    @ApiModelProperty(value = "治疗后描述")
    @TableField("after_desc")
    private String afterDesc;

    @ApiModelProperty(value = "治疗后图片")
    @TableField("after_pics")
    private String afterPics;

    @ApiModelProperty(value = "注意事项")
    @TableField("notice_desc")
    private String noticeDesc;

    @ApiModelProperty(value = "患者自评")
    @TableField("evaluation_desc")
    private String evaluationDesc;

    @ApiModelProperty(value = "开始时间")
    @TableField("start_dt")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDt;

    @ApiModelProperty(value = "结束时间")
    @TableField("end_dt")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDt;

    @TableField("create_dt")
    private LocalDateTime createDt;

    @TableField("modify_dt")
    private LocalDateTime modifyDt;

    @ApiModelProperty(value = "是否删除")
    @TableField("flag")
    private Integer flag;

    @ApiModelProperty(value = "是否排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "分享图")
    @TableField("share_pic")
    private String sharePic;

    @ApiModelProperty(value = "浏览量")
    @TableField("views")
    private Integer views;

}
