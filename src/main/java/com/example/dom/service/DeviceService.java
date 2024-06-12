package com.example.dom.service;

import com.example.dom.dto.device.DeviceDto;
import com.example.dom.models.Device;
import com.example.dom.models.Room;
import com.example.dom.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository repository;
    @Autowired
    private RoomService roomService;

    public List<Device> findAll() {
        List<Device> entities = repository.findAll();
        return entities.stream()
                .map(Device::toModel)
                .collect(Collectors.toList());
    }

    public Device findById(Long id) {
        Device entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        return Device.toModel(entity);
    }

    public Device create(DeviceDto device) {
        Room room = roomService.findById(device.getRoomId());
        Device entity = new Device(device.getName(), device.getType(), room);
        return Device.toModel(repository.save(entity));
    }

    public Device update(Long id, DeviceDto deviceDetails) {
        Device device = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        Room room = roomService.findById(deviceDetails.getRoomId());
        device.setName(deviceDetails.getName());
        device.setType(deviceDetails.getType());
        device.setRoom(room);
        Device updatedDevice = repository.save(device);
        return Device.toModel(updatedDevice);
    }

    public Long delete(Long id) {
        Device device = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        repository.delete(device);
        return id;
    }
}
