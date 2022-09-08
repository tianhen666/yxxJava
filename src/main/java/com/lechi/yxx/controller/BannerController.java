package com.lechi.yxx.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.lechi.yxx.config.FtpConfig;
import com.lechi.yxx.model.Banner;
import com.lechi.yxx.model.StoreProduct;
import com.lechi.yxx.service.IBannerService;
import com.lechi.yxx.service.IStoreProductService;
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
 *  前端控制器
 * </p>
 *
 * @author zf
 * @since 2022-07-26
 */
@Api(tags = "banner图接口")
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private IBannerService bannerService;

    @Autowired
    private IStoreProductService productService;

    @GetMapping("/list")
    @ApiOperation("获取门诊banner图")
    public R list(
            @ApiParam("门诊id") @RequestParam("storeId") Integer storeId,
            @ApiParam(value = "是否启用",required = false) @Param("sfuse") Integer sfuse){
        QueryWrapper<Banner> queryWrapper = new QueryWrapper<>();
        if (storeId!=null){
            queryWrapper
                    .eq("store_id",storeId);
        }
        if (sfuse!=null){
            queryWrapper
                    .eq("sfuse",sfuse);
        }

        queryWrapper.eq("flag",0).eq("defaultam",0).orderByAsc("sort");

        List<Banner> list = bannerService.list(queryWrapper);

        if (list!=null && !list.isEmpty()){
            return R.ok(list);
        }else {
            return R.ok(bannerService.lambdaQuery().eq(Banner::getDefaultam,1).list());
        }

    }

    @GetMapping("/info")
    @ApiOperation("获取指定的门诊banner图详情")
    public R info(@ApiParam(value = "id") @Param("id") Integer id){
            List<StoreProduct> list = new ArrayList<>();
            Banner byId = bannerService.getById(id);
            String productId = byId.getProductId();
            if (productId!=null && !productId.isEmpty()) {
                String[] result = productId.split(",");
                for (String r : result) {
                    StoreProduct byId1 = productService.getById(r);
                    list.add(byId1);
                }
                byId.setProductList(list);
            }else {
                byId.setProductList(null);
            }
            return R.ok(byId);
    }


    @PostMapping("/save")
    @ApiOperation("保存指定门诊的banner图")
    public R save(
            @ApiParam("banner对象") @RequestBody Banner banner){
        if (banner.getId()!=null){
            return R.ok(bannerService.updateById(banner));
        }
        return R.ok(bannerService.save(banner));
    }


    @PostMapping("/delete")
    @ApiOperation("删除指定的门诊banner图")
    public R delete(
            @ApiParam("id") @RequestBody Banner banner){
        Banner byId = bannerService.getById(banner.getId());
        byId.setFlag(1);
        return R.ok(bannerService.updateById(byId));
    }

    @PostMapping("/enable")
    @ApiOperation("启用")
    public R enable(
            @ApiParam("id") @RequestBody Banner banner){
        Banner byId = bannerService.getById(banner.getId());
        byId.setSfuse(0);
        return R.ok(bannerService.updateById(byId));
    }

    @PostMapping("/disable")
    @ApiOperation("禁用")
    public R disable(
            @ApiParam("id") @RequestBody Banner banner){
        Banner byId = bannerService.getById(banner.getId());
        byId.setSfuse(1);
        return R.ok(bannerService.updateById(byId));
    }


    @ApiOperation("Banner图上传")
    @PostMapping("/uploadimage")
    public R uploadPropertyStaffImage(@RequestParam("file") MultipartFile file) throws Exception {
        if(!file.isEmpty()){
            //此处拼接第三层路径：
            String baseDir= FtpConfig.store();
            String url= FtpUtils.upLoad(baseDir,file);
            String urlinfo = "https://imgs.ledianduo.com/tooth"+url;
            return R.ok(urlinfo);
        }
        return  R.failed("图片上传失败联系管理员");
    }



}
