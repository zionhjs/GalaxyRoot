package com.galaxy.cms.module.service.impl;

import com.galaxy.cms.module.mapper.JmsShortConnResultMapper;
import com.galaxy.cms.module.model.ShortConnResult;
import com.galaxy.cms.module.service.JmsShortConnResultService;
import com.galaxy.common.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
* Created by CodeGenerator on 2021/01/04.
*/
@Service
@Transactional
public class JmsShortConnResultServiceImpl extends AbstractService<ShortConnResult> implements JmsShortConnResultService {

    @Resource
    private JmsShortConnResultMapper jmsShortConnResultMapper;

    @Override
    public ShortConnResult getByUrl(String url) {
        return jmsShortConnResultMapper.getByUrl(url);
    }
}
