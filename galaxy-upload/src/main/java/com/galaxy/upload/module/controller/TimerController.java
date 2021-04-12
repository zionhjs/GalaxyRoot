package com.galaxy.upload.module.controller;

import com.galaxy.common.core.utils.Logger;
import com.galaxy.upload.module.model.Images;
import com.galaxy.upload.module.service.UploadImagesService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/***
 * 每晚12点进行同步苏州活动数据
 */
@Component
public class TimerController {

    @Resource
    private UploadImagesService uploadImagesService;

    @Scheduled(cron =  "0 0 0 * * ?") //每晚十二点执行
    //@Scheduled(cron =  "0 */1 * * * ?") //每隔1分钟执行一次
    //@Scheduled(cron =  "*/5 * * * * ?") //每五秒执行一次
    public void timer(){
        //获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //需要执行的方法
        try {

            List<Images> imagesList = uploadImagesService.imagesList(null);
            if (imagesList.size() > 0){
                //减去暂时积分的百分之十
                for (Images d:imagesList) {
                    if (null == d.getTmpRating()){
                        d.setTmpRating(0L);
                    }else {
                        BigDecimal result = BigDecimal.valueOf(d.getTmpRating()).multiply(BigDecimal.valueOf(0.1));
                        d.setTmpRating(BigDecimal.valueOf(d.getTmpRating()).subtract(result).longValue());
                    }
                    uploadImagesService.update(d);
                }
            }
        } catch (Exception e) {
            Logger.info(this,"" + e);
            e.printStackTrace();
        }

    }

}
