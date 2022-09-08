package com.lechi.yxx.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.lechi.yxx.config.FtpConfig;
import com.lechi.yxx.model.StoreCase;
import com.lechi.yxx.model.StoreCaseCategory;
import com.lechi.yxx.service.IStoreCaseCategoryService;
import com.lechi.yxx.service.IStoreCaseService;
import com.lechi.yxx.util.FtpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zf
 * @since 2022-08-05
 */
@Api(tags = "案例信息接口")
@RestController
@RequestMapping("/storecase")
public class StoreCaseController {

    @Autowired
    private IStoreCaseService caseService;

    @Autowired
    private IStoreCaseCategoryService caseCategoryService;

    @GetMapping("/getlist")
    @ApiOperation("获取案例列表")
    public R getlist(
            @ApiParam("门诊id") @Param("storeId") Integer storeId,
            @ApiParam("类型") @Param("categoryId") Integer categoryId){
        if (categoryId!=null) {
            List<StoreCase> list = caseService.lambdaQuery()
                    .eq(StoreCase::getStoreId, storeId)
                    .eq(StoreCase::getCategoryId, categoryId)
                    .eq(StoreCase::getFlag,0)
                    .list();
            return R.ok(list);
        }
        return R.ok(caseService.lambdaQuery().eq(StoreCase::getStoreId, storeId).eq(StoreCase::getFlag,0).list());
    }

    @GetMapping("/getinfo")
    @ApiOperation("获取案例详情")
    public R getinfo(
            @ApiParam("案例id") @Param("id") Integer id){
        return R.ok(caseService.getById(id));
    }

    @GetMapping("/getcategory")
    @ApiOperation("获取案例类型")
    public R getcategory(
            @ApiParam("门诊id") @Param("storeId") Integer storeId,
            @ApiParam("类型id") @Param("categoryId") Integer categoryId){
        List<StoreCaseCategory> list = caseCategoryService.lambdaQuery()
                .eq(StoreCaseCategory::getStoreId, storeId)
                .eq(StoreCaseCategory::getId, categoryId)
                .list();
        return R.ok(list);
    }

    @PostMapping("/save")
    @ApiOperation("添加/编辑")
    public R save(@ApiParam("案例对象") @RequestBody StoreCase storeCase){
        if(storeCase.getId()!=null){
            return R.ok(caseService.updateById(storeCase));
        }
        return R.ok(caseService.save(storeCase));
    }

    @PostMapping("/delete")
    @ApiOperation("删除指定案例")
    public R delete(
            @ApiParam("id") @RequestBody StoreCase storeCase){
        StoreCase byId = caseService.getById(storeCase.getId());
        byId.setFlag(1);
        return R.ok(caseService.updateById(byId));
    }

    @ApiOperation("案例图上传")
    @PostMapping("/uploadimage")
    public R uploadPropertyStaffImage(@RequestParam("file") MultipartFile file) throws Exception {
        if(!file.isEmpty()){
            //此处拼接第三层路径：
            String baseDir= FtpConfig.getcase();
            String url= FtpUtils.upLoad(baseDir,file);
            String urlinfo = "https://imgs.ledianduo.com/tooth"+url;
            return R.ok(urlinfo);
        }
        return  R.failed("图片上传失败联系管理员");
    }

    /**
     * 列表
     */
    @ApiOperation("案例分类查询")
    @GetMapping("/flist")
    public R flist(@ApiParam("门诊id") @Param("storeId") Integer storeId){
        return R.ok(caseCategoryService.lambdaQuery().eq(StoreCaseCategory::getStoreId,storeId).list());
    }


    /**
     * 保存
     */
    @ApiOperation("案例分类新增/修改")
    @PostMapping("fsave")
    public R save(@RequestBody StoreCaseCategory storeCaseCategory){
        if (storeCaseCategory.getId()!=null){
            return R.ok(caseCategoryService.updateById(storeCaseCategory));
        }
        return R.ok(caseCategoryService.save(storeCaseCategory));
    }


    /**
     * 删除
     */
    @ApiOperation("案例分类删除")
    @PostMapping("fdelete")
    public R delete(@RequestBody StoreCaseCategory storeCaseCategory){
        return R.ok(caseCategoryService.removeById(storeCaseCategory.getId()));
    }


}
