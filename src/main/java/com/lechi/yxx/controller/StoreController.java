package com.lechi.yxx.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.lechi.yxx.config.FtpConfig;
import com.lechi.yxx.model.Store;
import com.lechi.yxx.service.IStoreService;
import com.lechi.yxx.util.FtpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 店铺表 前端控制器
 * </p>
 *
 * @author zf
 * @since 2022-07-26
 */
@Api(tags = "门诊信息接口")
@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private IStoreService storeService;

    @GetMapping("/getone")
    @ApiOperation("获取首页门诊信息")
    public R getone(
            @ApiParam("门诊id") @Param("storeId") Integer storeId){
        return R.ok(storeService.getone(storeId));
    }

    @GetMapping("/getinfo")
    @ApiOperation("获取门诊详情信息")
    public R getinfo(
            @ApiParam("门诊id") @Param("storeId") Integer storeId){
        return R.ok(storeService.lambdaQuery().eq(Store::getStoreId,storeId).eq(Store::getFlag,0).one());
    }

    @PostMapping("/save")
    @ApiOperation("添加/修改门诊信息")
    public R save(@ApiParam("门诊对象") @RequestBody Store store){
        if(store.getStoreId()!=null){
            return R.ok(storeService.updateById(store));
        }
        return R.ok(storeService.save(store));
    }

    @PostMapping("/delete")
    @ApiOperation("删除门诊信息")
    public R delete(@ApiParam("门诊对象") @RequestBody Store store){
        Store byId = storeService.getById(store.getStoreId());
        byId.setFlag(1);
        return R.ok(storeService.updateById(byId));
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
