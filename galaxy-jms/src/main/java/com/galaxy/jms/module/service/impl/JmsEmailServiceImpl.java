package com.galaxy.jms.module.service.impl;

import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.service.AbstractService;
import com.galaxy.common.core.utils.DigitUtil;
import com.galaxy.jms.module.mapper.JmsEmailMapper;
import com.galaxy.jms.module.model.Email;
import com.galaxy.jms.module.service.JmsEmailService;
import com.galaxy.jms.module.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.Date;


/**
* Created by CodeGenerator on 2021/01/02.
*/
@Service
@Transactional
public class JmsEmailServiceImpl extends AbstractService<Email> implements JmsEmailService {

    @Resource
    private JmsEmailMapper jmsEmailMapper;

    @Autowired
    private EmailUtil emailUtils;

    /**
     * 发送邮件
     * @param userEmail        接收者邮箱
     * @param messageInside   管理员接收内容
     * @param messageOutside  客户接收内容
     * @return
     */
    @Override
    public Result sendQuery(String userEmail,String messageInside,String messageOutside) {

        Email email = new Email();
        email.setCreatedAt(new Date());
        try {
            //给用户发送邮件
            Result result = emailUtils.sendMail(userEmail,messageOutside);
            if ("success".equalsIgnoreCase(result.getMessage())){
                email.setId(DigitUtil.generatorLongId());
                email.setUserEmail(userEmail);
                email.setMessageOutside(messageOutside);
                email.setResult("给该用户 " + userEmail + " 发送邮件成功");
                save(email);
            }else {
                email.setId(DigitUtil.generatorLongId());
                email.setUserEmail(userEmail);
                email.setMessageOutside(messageOutside);
                email.setResult("给该用户 " + userEmail + " 发送邮件失败");
                save(email);
            }
        } catch (Exception e) {
            e.printStackTrace();
            email.setId(DigitUtil.generatorLongId());
            email.setUserEmail(userEmail);
            email.setMessageOutside(messageOutside);
            email.setResult("给该用户 " + userEmail + " 发送邮件失败" + " 错误信息" + e.getMessage());
            save(email);
        }

        //给管理员发送邮件
        try {
            //给管理员发送邮件
            Result result = emailUtils.sendMail("sale@galaxycgi.com",messageInside);
            if ("success".equalsIgnoreCase(result.getMessage())){
                email.setId(DigitUtil.generatorLongId());
                email.setUserEmail("sale@galaxycgi.com");
                email.setMessageOutside(null);
                email.setMessageInside(messageInside);
                email.setResult("给该管理员 " + "sale@galaxycgi.com" + " 发送邮件成功");
                save(email);
            }else {
                email.setId(DigitUtil.generatorLongId());
                email.setUserEmail("sale@galaxycgi.com");
                email.setMessageOutside(null);
                email.setMessageInside(messageInside);
                email.setResult("给该管理员 " + "sale@galaxycgi.com" + " 发送邮件失败");
                save(email);
            }
        } catch (Exception e) {
            e.printStackTrace();
            email.setId(DigitUtil.generatorLongId());
            email.setUserEmail(userEmail);
            email.setMessageOutside(null);
            email.setMessageOutside(messageOutside);
            email.setResult("给该管理员 " + "sale@galaxycgi.com" + " 发送邮件失败" + " 错误信息" + e.getMessage());
            save(email);
        }

        //给Zion发送邮件
        try {
            //给Zion发送邮件
            Result result = emailUtils.sendMail("zion@galaxy.com",messageInside);
            if ("success".equalsIgnoreCase(result.getMessage())){
                email.setId(DigitUtil.generatorLongId());
                email.setUserEmail("zion@galaxy.com");
                email.setMessageOutside(null);
                email.setMessageInside(messageInside);
                email.setResult("给Zion " + "zion@galaxy.com" + " 发送邮件成功");
                save(email);
            }else {
                email.setId(DigitUtil.generatorLongId());
                email.setUserEmail("zion@galaxy.com");
                email.setMessageOutside(null);
                email.setMessageInside(messageInside);
                email.setResult("给Zion " + "zion@galaxy.com" + " 发送邮件失败");
                save(email);
            }
        } catch (Exception e) {
            e.printStackTrace();
            email.setId(DigitUtil.generatorLongId());
            email.setUserEmail(userEmail);
            email.setMessageOutside(null);
            email.setMessageOutside(messageOutside);
            email.setResult("给Zion " + "zion@galaxy.com" + " 发送邮件失败" + " 错误信息" + e.getMessage());
            save(email);
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 发送随机url邮件
     * @param userEmail 接收者邮箱
     * @return
     */
    @Override
    public Result sendGenerateUrl(String userEmail) {
        Email email = new Email();
        email.setCreatedAt(new Date());
        String message = new String();
        message = "Thanks for your interest, we provide different quality of rendering works and you can check the difference of prices in the link here: "+ "https://www.baidu.com/ " + " The above link will expire in 24 hour, please reach out to us anytime if you have any questions. Our number is +1 213-822-4642." + " Regards" + " GalaxyCGI Team" + new Date();
        try {
            //给用户发送邮件
            Result result = emailUtils.sendMail(userEmail,message);
            if ("success".equalsIgnoreCase(result.getMessage())){
                email.setUserEmail(userEmail);
                email.setMessageOutside(message);
                email.setGenerateUrl("https://www.baidu.com/");
                email.setResult("给该用户 " + userEmail + " 发送邮件成功");
                save(email);
            }else {
                email.setUserEmail(userEmail);
                email.setMessageOutside(message);
                email.setGenerateUrl("https://www.baidu.com/");
                email.setResult("给该用户 " + userEmail + " 发送邮件失败");
                save(email);
            }
        } catch (Exception e) {
            e.printStackTrace();
            email.setUserEmail(userEmail);
            email.setMessageOutside(message);
            email.setGenerateUrl("https://www.baidu.com/");
            email.setResult("给该用户 " + userEmail + " 发送邮件失败" + " 错误信息" + e.getMessage());
            save(email);
        }
        return ResultGenerator.genSuccessResult();
    }
}
