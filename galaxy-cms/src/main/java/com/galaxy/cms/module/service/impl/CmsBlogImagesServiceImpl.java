package com.galaxy.cms.module.service.impl;

import com.galaxy.cms.module.mapper.CmsBlogImagesMapper;
import com.galaxy.cms.module.model.BlogImages;
import com.galaxy.cms.module.service.CmsBlogImagesService;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
* Created by CodeGenerator on 2020/12/27.
*/
@Service
@Transactional
public class CmsBlogImagesServiceImpl extends AbstractService<BlogImages> implements CmsBlogImagesService {
    @Resource
    private CmsBlogImagesMapper cmsBlogImagesMapper;

    @Override
    public Result batch(List<BlogImages> blogImagesList) {
        if (blogImagesList.size() > 0){
            for (BlogImages d:blogImagesList) {
                d.setCreatedAt(new Date());
                d.setIsDelete(false);
            }
        }
        saveList(blogImagesList);
        Result result= ResultGenerator.genSuccessResult();
        result.setData(blogImagesList);
        return result;
    }

    @Override
    public Result deleteByUrl(String url) {
        cmsBlogImagesMapper.deleteByUrl(url);
        return ResultGenerator.genSuccessResult();
    }
}
