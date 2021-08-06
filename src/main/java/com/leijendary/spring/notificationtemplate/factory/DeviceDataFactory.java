package com.leijendary.spring.notificationtemplate.factory;

import com.leijendary.schema.AuthSchema;
import com.leijendary.spring.notificationtemplate.data.DeviceData;

public class DeviceDataFactory extends AbstractFactory {

    public static DeviceData of(final AuthSchema authSchema) {
        return MAPPER.map(authSchema, DeviceData.class);
    }
}
