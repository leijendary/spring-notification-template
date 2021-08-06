package com.leijendary.spring.notificationtemplate.factory.mapper;

import com.leijendary.spring.notificationtemplate.data.SampleData;
import com.leijendary.spring.notificationtemplate.data.request.v1.SampleRequestV1;
import org.modelmapper.PropertyMap;

public class SampleRequestV1ToSampleDataMap extends PropertyMap<SampleRequestV1, SampleData> {

    @Override
    protected void configure() {
        map().setColumn1(source.getField1());
        map().setColumn2(source.getField2());
    }
}
