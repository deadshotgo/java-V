package com.example.dom.controller;
import com.example.dom.dto.device.DeviceDto;
import com.example.dom.models.Device;
import com.example.dom.service.DeviceService;
import com.example.dom.swagger.GenerateApiDoc;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
@Tag(name = "Device", description = "Endpoints for device management")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GenerateApiDoc(
            summary = "Get all devices",
            description = "Retrieve a list of all registered devices",
            responseDescription = "List of devices retrieved successfully",
            responseClass = Device.class
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        return ResponseEntity.status(HttpStatus.OK).body(deviceService.findAll());
    }

    @GenerateApiDoc(
            summary = "Get device by ID",
            description = "Retrieve a device by its unique identifier",
            responseDescription = "Device retrieved successfully by ID",
            responseClass = Device.class
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(deviceService.findById(id));
    }

    @GenerateApiDoc(
            summary = "Create a new device",
            description = "Create a new device with the provided data",
            responseDescription = "Device created successfully",
            responseClass = Device.class
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Device> createDevice(@Valid @RequestBody DeviceDto device) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.create(device));
    }

    @GenerateApiDoc(
            summary = "Update device by ID",
            description = "Update a device by its unique identifier",
            responseDescription = "Device updated successfully",
            responseClass = Device.class
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable Long id, @Valid @RequestBody DeviceDto deviceDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(deviceService.update(id, deviceDetails));
    }

    @GenerateApiDoc(
            summary = "Delete device by ID",
            description = "Delete a device by its unique identifier",
            responseDescription = "Device deleted successfully",
            responseClass = Long.class
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteDevice(@PathVariable Long id) {
       return ResponseEntity.status(HttpStatus.OK).body(deviceService.delete(id));
    }
}
