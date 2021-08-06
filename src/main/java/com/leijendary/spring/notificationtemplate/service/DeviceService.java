package com.leijendary.spring.notificationtemplate.service;

import com.leijendary.spring.notificationtemplate.data.DeviceData;
import com.leijendary.spring.notificationtemplate.factory.DeviceFactory;
import com.leijendary.spring.notificationtemplate.model.Device;
import com.leijendary.spring.notificationtemplate.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceService extends AbstractService {

    private final DeviceRepository deviceRepository;

    public Device save(final DeviceData deviceData) {
        final var deviceId = deviceData.getDeviceId();
        final var device = deviceRepository.findFirstByDeviceId(deviceId)
                .orElse(new Device());

        DeviceFactory.map(deviceData, device);

        return deviceRepository.save(device);
    }

    public void deleteByDeviceId(final String deviceId) {
        deviceRepository.deleteByDeviceId(deviceId);
    }
}
