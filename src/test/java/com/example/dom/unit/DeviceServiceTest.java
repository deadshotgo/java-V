package com.example.dom.unit;

import com.example.dom.dto.device.DeviceDto;
import com.example.dom.models.Device;
import com.example.dom.models.Room;
import com.example.dom.models.User;
import com.example.dom.repository.DeviceRepository;
import com.example.dom.service.DeviceService;
import com.example.dom.service.RoomService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private DeviceService deviceService;

    @Test
    void testFindAll() {
        // Arrange
        Device device1 = createDeviceEntity(1L, "Device 1", "Type 1", createRoomEntity(1L, "Room 1", createUserEntity(1L, "User 1")));
        Device device2 = createDeviceEntity(2L, "Device 2", "Type 2", createRoomEntity(2L, "Room 2", createUserEntity(2L, "User 2")));
        when(deviceRepository.findAll()).thenReturn(Arrays.asList(device1, device2));

        // Act
        List<Device> result = deviceService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Device 1", result.get(0).getName());
        assertEquals("Device 2", result.get(1).getName());
    }

    @Test
    void testFindById_WhenIdExists() {
        // Arrange
        Long id = 1L;
        Device device = createDeviceEntity(id, "Device 1", "Type 1", createRoomEntity(1L, "Room 1", createUserEntity(1L, "User 1")));
        when(deviceRepository.findById(id)).thenReturn(Optional.of(device));

        // Act
        Device result = deviceService.findById(id);

        // Assert
        assertEquals("Device 1", result.getName());
    }

    @Test
    void testFindById_WhenIdNotExists() {
        // Arrange
        Long id = 1L;
        when(deviceRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> deviceService.findById(id));
    }

    @Test
    void testCreate() {
        // Arrange
        Long roomId = 1L;
        Room room = createRoomEntity(roomId, "Room 1", createUserEntity(1L, "User 1"));
        DeviceDto deviceDto = new DeviceDto("Device 1", "Type 1", roomId);
        Device device = createDeviceEntity(1L, "Device 1", "Type 1", room);
        when(roomService.findById(roomId)).thenReturn(room);
        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        // Act
        Device result = deviceService.create(deviceDto);

        // Assert
        assertEquals("Device 1", result.getName());
    }

    @Test
    void testUpdate_WhenIdExists() {
        // Arrange
        Long id = 1L;
        Long roomId = 1L;
        Device existingDevice = createDeviceEntity(id, "Device 1", "Type 1", createRoomEntity(roomId, "Room 1", createUserEntity(1L, "User 1")));
        DeviceDto deviceDto = new DeviceDto("Updated Device", "Updated Type", roomId);
        Room room = createRoomEntity(roomId, "Room 1", createUserEntity(1L, "User 1"));
        when(deviceRepository.findById(id)).thenReturn(Optional.of(existingDevice));
        when(roomService.findById(roomId)).thenReturn(room);
        when(deviceRepository.save(any(Device.class))).thenReturn(existingDevice);

        // Act
        Device result = deviceService.update(id, deviceDto);

        // Assert
        assertEquals("Updated Device", result.getName());
    }

    @Test
    void testUpdate_WhenIdNotExists() {
        // Arrange
        Long id = 1L;
        Long roomId = 1L;
        DeviceDto deviceDto = new DeviceDto("Updated Device", "Updated Type", roomId);
        when(deviceRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> deviceService.update(id, deviceDto));
    }

    @Test
    void testDelete_WhenIdExists() {
        // Arrange
        Long id = 1L;
        Device device = createDeviceEntity(id, "Device 1", "Type 1", createRoomEntity(1L, "Room 1", createUserEntity(1L, "User 1")));
        when(deviceRepository.findById(id)).thenReturn(Optional.of(device));

        // Act
        Long result = deviceService.delete(id);

        // Assert
        assertEquals(id, result);
        verify(deviceRepository, times(1)).delete(device);
    }

    @Test
    void testDelete_WhenIdNotExists() {
        // Arrange
        Long id = 1L;
        when(deviceRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> deviceService.delete(id));
    }

    private Device createDeviceEntity(Long id, String name, String type, Room room) {
        return new Device(id, name, type, room);
    }

    private Room createRoomEntity(Long id, String name, User user) {
        Room room = new Room();
        room.setId(id);
        room.setName(name);
        room.setUser(user);
        return room;
    }

    private User createUserEntity(Long id, String username) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        return user;
    }
}
