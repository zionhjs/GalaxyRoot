package com.galaxy.upload.module.controller;

import com.galaxy.common.core.controller.BaseController;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.upload.module.module.Images;
import com.galaxy.upload.module.service.UploadImagesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/galaxy/uoload")
@Api(tags = {"/galaxy/uoload"}, description = "图片管理管理")
public class UploadImagesController extends BaseController {

    @Autowired
    private UploadImagesService uploadImagesService;

    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/add", method = {RequestMethod.POST,RequestMethod.GET})
    public Result add(@RequestBody Images images) {
        images.setCreatedAt(new Date());
        images.setIsDelete(false);
        uploadImagesService.save(images);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(images);
        return result;
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET})
    public Result delete(@RequestParam Long id) {
        Images images=new Images();
        images.setId(id);
        images.setIsDelete(true);
        uploadImagesService.update(images);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "修改", notes = "修改")
    @RequestMapping(value = "/update", method = {RequestMethod.POST,RequestMethod.GET})
    public Result update(@RequestBody Images images) {
        images.setUpdatedAt(new Date());
        uploadImagesService.update(images);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(images);
        return result;
    }

    @ApiOperation(value = "获取单个详情", notes = "获取单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST,RequestMethod.GET})
    public Result detail(@RequestParam Long id) {
        Images images = uploadImagesService.findById(id);
        return ResultGenerator.genSuccessResult(images);
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST,RequestMethod.GET})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page,@RequestParam(defaultValue="20",required=false) Integer size, @RequestBody(required =false) Images images) {
        PageHelper.startPage(page, size);
        images.setIsDelete(false);
        List<Images> list = uploadImagesService.findByModel(images);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }


}
