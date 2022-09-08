package com.lechi.yxx.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.lechi.yxx.config.FtpConfig;
import com.lechi.yxx.model.StoreProduct;
import com.lechi.yxx.model.StoreServe;
import com.lechi.yxx.service.IStoreProductService;
import com.lechi.yxx.service.IStoreServeService;
import com.lechi.yxx.util.FtpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 店铺服务表 前端控制器
 * </p>
 *
 * @author zf
 * @since 2022-08-20
 */
@Api(tags = "服务接口")
@RestController
@RequestMapping("/serve")
public class StoreServeController {

    @Autowired
    private IStoreServeService storeServeService;

    @Autowired
    private IStoreProductService productService;

    @GetMapping("/getlist")
    @ApiOperation("获取服务")
    public R getlist(
            @ApiParam("门诊id") @Param("storeId") Integer storeId){
            QueryWrapper<StoreServe> queryWrapper = new QueryWrapper();
            queryWrapper.eq("store_id",storeId).eq("flag",0).orderByAsc("weight");
        List<StoreServe> list = storeServeService.list(queryWrapper);
        return R.ok(list);
    }

    @GetMapping("/getinfo")
    @ApiOperation("获取服务详情")
    public R getinfo(
            @ApiParam("服务id") @Param("id") Integer id){
        List<StoreProduct> list = new ArrayList<>();
        StoreServe one = storeServeService.lambdaQuery().eq(StoreServe::getId,id).one();
        String productId = one.getProductId();
        if (productId!=null && !productId.isEmpty()) {
            String[] result = productId.split(",");
            for (String r : result) {
                StoreProduct byId = productService.getById(r);
                list.add(byId);
            }
            one.setProductList(list);
        }else {
            one.setProductList(null);
        }
        return R.ok(one);
    }

    @PostMapping("/save")
    @ApiOperation("新增/修改")
    public R save(@RequestBody StoreServe storeServe){
        if (storeServe.getId()!=null){
            return R.ok(storeServeService.updateById(storeServe));
        }else {
            return R.ok(storeServeService.save(storeServe));
        }
    }

    @ApiOperation("服务图上传")
    @PostMapping("/uploadimage")
    public R uploadPropertyStaffImage(@RequestParam("file") MultipartFile file) throws Exception {
        if(!file.isEmpty()){
            //此处拼接第三层路径：
            String baseDir= FtpConfig.activity();
            String url= FtpUtils.upLoad(baseDir,file);
            String urlinfo = "https://imgs.ledianduo.com/tooth"+url;
            return R.ok(urlinfo);
        }
        return  R.failed("图片上传失败联系管理员");
    }

    @ApiOperation("删除服务")
    @PostMapping("/delete")
    public

    R delete(@ApiParam("id") @RequestBody StoreServe storeServe){
        StoreServe byId = storeServeService.getById(storeServe);
        byId.setFlag(1);
        return R.ok(storeServeService.updateById(byId));
    }



}
