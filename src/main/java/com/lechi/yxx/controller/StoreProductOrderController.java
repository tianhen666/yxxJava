package com.lechi.yxx.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.lechi.yxx.model.StoreProductOrder;
import com.lechi.yxx.service.IStoreProductOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author zf
 * @since 2022-08-08
 */
@RestController
@RequestMapping("/order")
@Api(tags = "商品订单接口")
public class StoreProductOrderController {

    @Autowired
    private IStoreProductOrderService orderService;


    @GetMapping("/unpaid")
    @ApiOperation("代付款")
    public R getone(
            @ApiParam("门诊id") @Param("storeId") Integer storeId,
            @ApiParam("用户id") @Param("userId") Integer userId){
        List<StoreProductOrder> list = orderService.lambdaQuery()
                .eq(StoreProductOrder::getStoreId, storeId)
                .eq(StoreProductOrder::getUserId, userId)
                .eq(StoreProductOrder::getStatus, 0)
                .list();
        return R.ok(list);
    }

    @GetMapping("/notused")
    @ApiOperation("代使用")
    public R notused(
            @ApiParam("门诊id") @Param("storeId") Integer storeId,
            @ApiParam("用户id") @Param("userId") Integer userId){
        List<StoreProductOrder> list = orderService.lambdaQuery()
                .eq(StoreProductOrder::getStoreId, storeId)
                .eq(StoreProductOrder::getUserId, userId)
                .eq(StoreProductOrder::getStatus, 1)
                .list();
        return R.ok(list);
    }

    @GetMapping("/complete")
    @ApiOperation("已完成")
    public R complete(
            @ApiParam("门诊id") @Param("storeId") Integer storeId,
            @ApiParam("用户id") @Param("userId") Integer userId){
        List<StoreProductOrder> list = orderService.lambdaQuery()
                .eq(StoreProductOrder::getStoreId, storeId)
                .eq(StoreProductOrder::getUserId, userId)
                .eq(StoreProductOrder::getStatus, 2)
                .list();
        return R.ok(list);
    }

    @GetMapping("/getinfo")
    @ApiOperation("订单详情")
    public R getinfo(
            @ApiParam("订单号") @Param("orderNo") String orderNo){
        return R.ok(orderService.lambdaQuery().eq(StoreProductOrder::getOrderNo,orderNo));
    }

    @PostMapping("/uploadFile")
    @ApiOperation("商品图片上传")
    public R uploadFile(@RequestParam(value = "file",required = true) MultipartFile uploadFile,
                           HttpServletRequest request) {
        return R.ok(orderService.uploadFile(uploadFile,request));
    }



}
