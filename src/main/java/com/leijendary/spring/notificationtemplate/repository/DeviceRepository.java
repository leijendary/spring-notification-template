package com.leijendary.spring.notificationtemplate.repository;

import com.leijendary.spring.notificationtemplate.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findFirstByDeviceId(final String deviceId);

    @Modifying
    void deleteByDeviceId(final String deviceId);
}
