package com.leijendary.spring.notificationtemplate.factory;

import com.leijendary.spring.notificationtemplate.data.SampleData;
import com.leijendary.spring.notificationtemplate.data.request.v1.SampleRequestV1;

public class SampleDataFactory extends AbstractFactory {

    public static SampleData of(final SampleRequestV1 sampleRequestV1) {
        return MAPPER.map(sampleRequestV1, SampleData.class);
    }
}
