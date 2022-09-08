package com.lechi.yxx.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zf
 * @since 2022-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="Banner对象", description="")
@Builder
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id" ,type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "门诊id")
    @TableField("store_id")
    private Integer storeId;

    @ApiModelProperty(value = "banner图路径")
    private String banner;

    @ApiModelProperty(value = "0启用 1禁用")
    @TableField("sfuse")
    private Integer sfuse;

    @ApiModelProperty(value = "是否删除 0不删除 1删除")
    private Integer flag;

    @ApiModelProperty(value = "是否默认 0不默认1默认")
    private Integer defaultam;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "商品id")
    @TableField("product_id")
    private String productId;

    @TableField(exist = false)
    private List<StoreProduct> productList;

}
