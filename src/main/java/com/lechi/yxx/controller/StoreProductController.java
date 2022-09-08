package com.lechi.yxx.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lechi.yxx.config.FtpConfig;
import com.lechi.yxx.model.StoreOrderReview;
import com.lechi.yxx.model.StoreProduct;
import com.lechi.yxx.service.IStoreOrderReviewService;
import com.lechi.yxx.service.IStoreProductService;
import com.lechi.yxx.util.FtpUtils;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 店铺爆品 前端控制器
 * </p>
 *
 * @author zf
 * @since 2022-07-29
 */
@Api(tags = "商品接口")
@RestController
@RequestMapping("/storeproduct")
public class StoreProductController {

    @Autowired
    private IStoreProductService storeProductService;

    @Autowired
    private IStoreOrderReviewService orderReviewService;

    @GetMapping("/getlist")
    @ApiOperation("获取商品列表")
    public R getlist(@ApiParam("门诊id") @Param("storeId") Integer storeId,
                     @ApiParam("当前页") @Param("pageIndex") Integer pageIndex,
                     @ApiParam("状态") @Param("status") Integer status){
        if (pageIndex!=null){
            Page<StoreProduct> page = new Page<>(pageIndex,4);
            QueryWrapper<StoreProduct> queryWrapper = new QueryWrapper();
            queryWrapper.eq("store_id",storeId).eq("status",0);
            Page<StoreProduct> storeProductPage = storeProductService.page(page, queryWrapper);
            return R.ok(storeProductPage);
        }
        QueryWrapper<StoreProduct> queryWrapper = new QueryWrapper();
        queryWrapper.eq("store_id",storeId).eq("status",status).orderByAsc("order_num");
        return R.ok(storeProductService.list(queryWrapper));
    }

    @GetMapping("/getinfo")
    @ApiOperation("获取商品详情")
    public R getinfo(@ApiParam("商品id") @Param("id") Integer id){
        StoreProduct one = storeProductService.lambdaQuery().eq(StoreProduct::getId, id).one();
        return R.ok(one);
    }

    @GetMapping("/getreview")
    @ApiOperation("获取商品评论")
    public R getreview(@ApiParam("门诊id") @Param("storeId") Integer storeId,
                       @ApiParam("商品id") @Param("targetId") Integer targetId){
        List<StoreOrderReview> list = orderReviewService.lambdaQuery()
                .eq(StoreOrderReview::getStoreId, storeId)
                .eq(StoreOrderReview::getTargetId, targetId).list();
        return R.ok(list);
    }

    @ApiOperation("商品图上传")
    @PostMapping("/uploadimage")
    public R uploadPropertyStaffImage(@RequestParam("file") MultipartFile file) throws Exception {
        if(!file.isEmpty()){
            //此处拼接第三层路径：
            String baseDir= FtpConfig.product();
            String url= FtpUtils.upLoad(baseDir,file);
            String urlinfo = "https://imgs.ledianduo.com/tooth"+url;
            return R.ok(urlinfo);
        }
        return  R.failed("图片上传失败联系管理员");
    }

    @ApiOperation("删除商品")
    @PostMapping("/delete")
    public R delete(@ApiParam("id") @RequestBody StoreProduct storeProduct){
        return  R.ok(storeProductService.removeById(storeProduct.getId()));
    }

    @ApiOperation("发布评论")
    @PostMapping("/comments")
    public R comments(@ApiParam("发布评论对象") @RequestBody StoreOrderReview storeOrderReview){
        return  R.ok(orderReviewService.save(storeOrderReview));
    }

    @ApiOperation("保存/编辑商品")
    @PostMapping("/save")
    public R save(@ApiParam("商品对象") @RequestBody StoreProduct storeProduct){
        if (storeProduct.getId()!=null){
            return  R.ok(storeProductService.updateById(storeProduct));
        }else {
            return  R.ok(storeProductService.save(storeProduct));
        }
    }

    @ApiOperation("上架")
    @PostMapping("/enable")
    public R enable(@ApiParam("id") @RequestBody StoreProduct storeProduct){
        StoreProduct byId = storeProductService.getById(storeProduct.getId());
        byId.setStatus(0);
        return R.ok(storeProductService.updateById(byId));
    }

    @ApiOperation("下架")
    @PostMapping("/disable")
    public R disable(@ApiParam("商品对象") @RequestBody StoreProduct storeProduct){
        StoreProduct byId = storeProductService.getById(storeProduct.getId());
        byId.setStatus(1);
        return R.ok(storeProductService.updateById(byId));
    }



}
