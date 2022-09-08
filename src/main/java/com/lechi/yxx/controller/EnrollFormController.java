package com.lechi.yxx.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.lechi.yxx.config.FtpConfig;
import com.lechi.yxx.model.EnrollForm;
import com.lechi.yxx.service.IEnrollFormService;
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
 * 报名表单 前端控制器
 * </p>
 *
 * @author zf
 * @since 2022-07-27
 */
@Api(tags = "活动接口")
@RestController
@RequestMapping("/enrollform")
public class EnrollFormController {

    @Autowired
    IEnrollFormService enrollFormService;

    @GetMapping("/getlist")
    @ApiOperation("获取活动")
    public R getlist(
            @ApiParam("门诊id") @Param("storeId") Integer storeId,
            @ApiParam("状态") @Param("status") Integer status){
        if (status!=null){
            QueryWrapper<EnrollForm> queryWrapper = new QueryWrapper();
            queryWrapper.eq("store_id",storeId).eq("status",status).eq("flag",0).orderByAsc("sort");
            return R.ok(enrollFormService.list(queryWrapper));
        }
        return R.ok(enrollFormService.lambdaQuery().eq(EnrollForm::getStoreId,storeId).eq(EnrollForm::getFlag,0).orderByAsc(EnrollForm::getSort).list());
    }

    @GetMapping("/getinfo")
    @ApiOperation("获取活动详情")
    public R getinfo(
            @ApiParam("活动id") @Param("id") Integer id){
        return R.ok(enrollFormService.lambdaQuery().eq(EnrollForm::getId,id).one());
    }

    @PostMapping("/save")
    @ApiOperation("新增/修改")
    public R save(@RequestBody EnrollForm enrollForm){
        if (enrollForm.getId()!=null){
            return R.ok(enrollFormService.updateById(enrollForm));
        }else {
            return R.ok(enrollFormService.save(enrollForm));
        }
    }

    @ApiOperation("活动图上传")
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

    @ApiOperation("删除活动")
    @PostMapping("/delete")
    public R delete(@ApiParam("id") @RequestBody EnrollForm enrollForm){
        EnrollForm byId = enrollFormService.getById(enrollForm);
        byId.setFlag(1);
        return R.ok(enrollFormService.updateById(byId));
    }

    @ApiOperation("上线活动")
    @PostMapping("/enable")
    public R enable(@ApiParam("id") @RequestBody EnrollForm enrollForm){
        EnrollForm byId = enrollFormService.getById(enrollForm);
        byId.setStatus(0);
        return R.ok(enrollFormService.updateById(byId));
    }

    @ApiOperation("下架活动")
    @PostMapping("/disable")
    public R disable(@ApiParam("id") @RequestBody EnrollForm enrollForm){
        EnrollForm byId = enrollFormService.getById(enrollForm);
        byId.setStatus(1);
        return R.ok(enrollFormService.updateById(byId));
    }


}
