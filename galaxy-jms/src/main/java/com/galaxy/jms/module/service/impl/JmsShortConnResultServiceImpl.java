package com.galaxy.jms.module.service.impl;

import com.galaxy.common.core.service.AbstractService;
import com.galaxy.jms.module.mapper.JmsShortConnResultMapper;
import com.galaxy.jms.module.model.ShortConnResult;
import com.galaxy.jms.module.service.JmsShortConnResultService;
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
