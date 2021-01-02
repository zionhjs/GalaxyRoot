package com.galaxy.jms.module.controller;

import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.utils.Logger;
import com.galaxy.jms.module.model.Email;
import com.galaxy.jms.module.service.JmsEmailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* Created by CodeGenerator on 2021/01/02.
*/
@RestController
@RequestMapping("/email")
@Api(tags = {"/email"}, description = "邮件管理模块")
public class JmsEmailController {
    @Resource
    private JmsEmailService jmsEmailService;

    @ApiOperation(value = "发送随机url邮件", notes = "发送随机url邮件")
    @RequestMapping(value = "/sendGenerateUrl", method = {RequestMethod.POST, RequestMethod.GET})
    public Result sendGenerateUrl(@RequestParam(value = "userEmail") String userEmail) {
        Logger.info(this, "/email/sendGenerateUrl 发送随机url邮件接口入参--->" + userEmail);
        return jmsEmailService.sendGenerateUrl(userEmail);
    }

    @ApiOperation(value = "给用户，管理员，Zion发送邮件", notes = "给用户，管理员，Zion发送邮件")
    @RequestMapping(value = "/sendQuery", method = {RequestMethod.POST, RequestMethod.GET})
    public Result sendEmail(@RequestParam(value = "userEmail") String userEmail,
                            @RequestParam(value = "message_inside") String messageInside,
                            @RequestParam(value = "message_outside") String messageOutside) {
        Logger.info(this, "/email/sendQuery 给用户，管理员，Zion发送邮件接口入参--->" + userEmail + messageInside + messageOutside);
        return jmsEmailService.sendQuery(userEmail,messageInside,messageOutside);
    }

    @ApiOperation(value = "删除邮件", notes = "删除邮件")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delete(@RequestParam Long id) {
        Email email=new Email();
        email.setId(id);
        email.setIsDelete(true);
        jmsEmailService.update(email);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "修改邮件", notes = "修改邮件")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public Result update(@RequestBody Email email) {
        email.setUpdatedAt(new Date());
        jmsEmailService.update(email);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(email);
        return result;
    }

    @ApiOperation(value = "获取邮件单个详情", notes = "获取邮件单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST, RequestMethod.GET})
    public Result detail(@RequestParam Long id) {
        Email email = jmsEmailService.findById(id);
        return ResultGenerator.genSuccessResult(email);
    }

    @ApiOperation(value = "分页查询邮件", notes = "分页查询邮件")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST, RequestMethod.GET})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page, @RequestParam(defaultValue="20",required=false) Integer size, @RequestBody(required =false) Email email) {
        PageHelper.startPage(page, size);
        email.setIsDelete(false);
        List<Email> list = jmsEmailService.findByModel(email);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
