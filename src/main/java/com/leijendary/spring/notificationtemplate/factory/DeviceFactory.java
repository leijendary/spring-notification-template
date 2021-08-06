package com.leijendary.spring.notificationtemplate.factory;

import com.leijendary.spring.notificationtemplate.data.DeviceData;
import com.leijendary.spring.notificationtemplate.model.Device;

public class DeviceFactory extends AbstractFactory {

    public static void map(final DeviceData deviceData, final Device device) {
        MAPPER.map(deviceData, device);
    }
}
