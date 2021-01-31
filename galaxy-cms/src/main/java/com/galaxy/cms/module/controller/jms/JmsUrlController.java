package com.galaxy.cms.module.controller.jms;

import com.galaxy.cms.module.model.ShortConnResult;
import com.galaxy.cms.module.service.JmsShortConnResultService;
import com.galaxy.common.core.utils.Logger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("/url")
@Api(tags = {"/url"}, description = "重定向管理模块")
public class JmsUrlController {

    @Autowired
    private JmsShortConnResultService jmsShortConnResultService;

    @ApiOperation(value = "重定向跳转界面", notes = "重定向跳转界面")
    @RequestMapping(value = "/redirect", method = {RequestMethod.POST, RequestMethod.GET})
    public String redirect(@RequestParam(value = "url") String url) {
        Logger.info(this, "/url/redirect 重定向跳转界面接口入参--->" + url);

        if (null == url){
            return "重定向唯一标识不能为空";
        }

        ShortConnResult shortConnResult = jmsShortConnResultService.getByUrl(url);
        if (null == shortConnResult){
            return "重定向唯一标识不存在";
        }

        if (new Date().getTime() > shortConnResult.getCreateAt().getTime() + 86400000){
            return "该链接已失效，请重新申请";
        }

        return "redirect:http://www.galaxycgi.com/api/v1/quotation";
    }

}
