package com.lechi.yxx.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.lechi.yxx.config.FtpConfig;
import com.lechi.yxx.model.StoreDoctor;
import com.lechi.yxx.service.IStoreDoctorService;
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
 * 医生信息 前端控制器
 * </p>
 *
 * @author zf
 * @since 2022-08-04
 */
@Api(tags = "医生信息接口")
@RestController
@RequestMapping("/storedoctor")
public class StoreDoctorController {

    @Autowired
    private IStoreDoctorService storeDoctorService;

    @GetMapping("/getlist")
    @ApiOperation("获取医生列表")
    public R getlist(
            @ApiParam("门诊id") @Param("storeId") Integer storeId){
        return R.ok(storeDoctorService.lambdaQuery().eq(StoreDoctor::getStoreId,storeId).eq(StoreDoctor::getFlag,0).list());
    }

    @GetMapping("/getinfo")
    @ApiOperation("获取医生信息")
    public R getinfo(
            @ApiParam("医生id") @Param("id") Integer id){
        return R.ok(storeDoctorService.lambdaQuery().eq(StoreDoctor::getId,id).one()

        );
    }

    @PostMapping("/save")
    @ApiOperation("新增/修改医生信息")
    public R update(
            @ApiParam("医生对象") @RequestBody StoreDoctor storeDoctor){
        if (storeDoctor.getId()!=null) {
            return R.ok(storeDoctorService.updateById(storeDoctor));
        }
        return R.ok(storeDoctorService.save(storeDoctor));
    }

    @PostMapping("/delete")
    @ApiOperation("删除医生信息")
    public R delete(
            @ApiParam("医生对象")@RequestBody StoreDoctor storeDoctor){
        StoreDoctor byId = storeDoctorService.getById(storeDoctor);
        byId.setFlag(1);
        return R.ok(storeDoctorService.updateById(byId));
    }

    @ApiOperation("Banner图上传")
    @PostMapping("/uploadimage")
    public R uploadPropertyStaffImage(@RequestParam("file") MultipartFile file) throws Exception {
        if(!file.isEmpty()){
            //此处拼接第三层路径：
            String baseDir= FtpConfig.doctor();
            String url= FtpUtils.upLoad(baseDir,file);
            String urlinfo = "https://imgs.ledianduo.com/tooth"+url;
            return R.ok(urlinfo);
        }
        return  R.failed("图片上传失败联系管理员");
    }

}
