package com.leijendary.spring.notificationtemplate.factory;

import org.modelmapper.ModelMapper;

import static com.leijendary.spring.notificationtemplate.util.SpringContext.getBean;

public abstract class AbstractFactory {

    protected static final ModelMapper MAPPER = getBean(ModelMapper.class);
}
