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
import java.time.LocalDateTime;

/**
 * <p>
 * 店铺表
 * </p>
 *
 * @author zf
 * @since 2022-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Store对象", description="店铺表")
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "store_id", type = IdType.AUTO)
    private Integer storeId;

    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "商家名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "联系电话")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty(value = "商家图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "门面照片")
    @TableField("pics")
    private String pics;

    @ApiModelProperty(value = "店内环境")
    @TableField("inner_pics")
    private String innerPics;

    @ApiModelProperty(value = "地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "详细地址，门牌号")
    @TableField("address_detail")
    private String addressDetail;

    @ApiModelProperty(value = "邮编")
    @TableField("area_id")
    private Integer areaId;

    @ApiModelProperty(value = "营业时间")
    @TableField("business_dt")
    private String businessDt;

    @ApiModelProperty(value = "开通时间")
    @TableField("open_time")
    private String openTime;

    @ApiModelProperty(value = "商家介绍")
    @TableField("desc_data")
    private String descData;

    @ApiModelProperty(value = "经度")
    @TableField("lng")
    private Double lng;

    @ApiModelProperty(value = "纬度")
    @TableField("lat")
    private Double lat;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_dt")
    private LocalDateTime createDt;

    @ApiModelProperty(value = "修改时间")
    @TableField("modify_dt")
    private LocalDateTime modifyDt;

    @ApiModelProperty(value = "查看次数")
    @TableField("look_num")
    private Integer lookNum;

    @ApiModelProperty(value = "联系人")
    @TableField("contact_name")
    private String contactName;

    @TableField("activity_store")
    private Boolean activityStore;

    @TableField("order_num")
    private Integer orderNum;

    @TableField("last_login_dt")
    private LocalDateTime lastLoginDt;

    @ApiModelProperty(value = "总店id，无总店为0")
    @TableField("sup_id")
    private Integer supId;

    @ApiModelProperty(value = "是否是总店")
    @TableField("is_sup")
    private Boolean isSup;

    @ApiModelProperty(value = "到期时间")
    @TableField("expire_dt")
    private LocalDateTime expireDt;

    @ApiModelProperty(value = "1基础版 2标准版 3高级版")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value = "是否删除")
    @TableField("flag")
    private Integer flag;

    @ApiModelProperty(value = "营业执照")
    @TableField("business_license")
    private String businessLicense;

    @ApiModelProperty(value = "医疗机构执业许可证")
    @TableField("organization_license")
    private String organizationLicense;

    @ApiModelProperty(value = "是否审核通过")
    @TableField("license_status")
    private Boolean licenseStatus;

    @ApiModelProperty(value = "广告许可证")
    @TableField("ad_license")
    private String adLicense;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "激活时间")
    @TableField("active_dt")
    private LocalDateTime activeDt;

    @ApiModelProperty(value = "是否购买活动工具")
    @TableField("is_hd")
    private Boolean isHd;

    @ApiModelProperty(value = "活动工具过期时间")
    @TableField("hd_expire")
    private LocalDateTime hdExpire;

    @ApiModelProperty(value = "员工数默认值基础版10  标准版30 高级版100")
    @TableField("staff_limit")
    private Integer staffLimit;

    @TableField("qrcode")
    private String qrcode;

    @ApiModelProperty(value = "分享图")
    @TableField("share_pic")
    private String sharePic;

    @ApiModelProperty(value = "客服微信")
    @TableField("customer")
    private String customer;


}
