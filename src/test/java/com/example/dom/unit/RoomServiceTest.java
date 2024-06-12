package com.example.dom.unit;

import com.example.dom.dto.room.RoomDto;
import com.example.dom.models.Room;
import com.example.dom.models.User;
import com.example.dom.repository.RoomRepository;
import com.example.dom.service.RoomService;
import com.example.dom.service.UserService;
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
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private RoomService roomService;

    @Test
    void testFindAll() {
        // Arrange
        Room room1 = createRoomEntity(1L, "Room 1", new User());
        Room room2 = createRoomEntity(2L, "Room 2", new User());
        when(roomRepository.findAll()).thenReturn(Arrays.asList(room1, room2));

        // Act
        List<Room> result = roomService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Room 1", result.get(0).getName());
        assertEquals("Room 2", result.get(1).getName());
    }

    @Test
    void testFindById_WhenIdExists() {
        // Arrange
        Long id = 1L;
        Room room = createRoomEntity(id, "Room 1", new User());
        when(roomRepository.findById(id)).thenReturn(Optional.of(room));

        // Act
        Room response = roomService.findById(id);

        // Assert
        assertEquals("Room 1", response.getName());
    }

    @Test
    void testFindById_WhenIdNotExists() {
        // Arrange
        Long id = 1L;
        when(roomRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> roomService.findById(id));
    }

    @Test
    void testCreate() {
        // Arrange
        Long userId = 1L;
        String roomName = "Room 1";
        RoomDto roomDto = new RoomDto(roomName, userId);
        User user = createUserEntity(userId, "User 1", "user1@example.com");
        Room room = createRoomEntity(1L, roomName, user);
        when(userService.getUser(userId)).thenReturn(user);
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        // Act
        Room result = roomService.create(roomDto);

        // Assert
        assertEquals(roomName, result.getName());
        assertEquals(user, result.getUser());
    }

    @Test
    void testUpdate() {
        // Arrange
        Long roomId = 1L;
        Long userId = 1L;
        String newRoomName = "Updated Room";
        RoomDto roomDto = new RoomDto(newRoomName, userId);
        User user = createUserEntity(userId, "User 1", "user1@example.com");
        Room existingRoom = createRoomEntity(roomId, "Old Room", user);
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(existingRoom));
        when(userService.getUser(userId)).thenReturn(user);
        when(roomRepository.save(any(Room.class))).thenReturn(existingRoom);

        // Act
        Room result = roomService.update(roomId, roomDto);

        // Assert
        assertEquals(newRoomName, result.getName());
        assertEquals(user, result.getUser());
    }

    @Test
    void testDelete_WhenIdExists() {
        // Arrange
        Long id = 1L;
        Room room = createRoomEntity(id, "Room 1", new User());
        when(roomRepository.findById(id)).thenReturn(Optional.of(room));
        doNothing().when(roomRepository).delete(room);

        // Act
        Long result = roomService.delete(id);

        // Assert
        assertEquals(id, result);
        verify(roomRepository, times(1)).delete(room);
    }

    @Test
    void testDelete_WhenIdNotExists() {
        // Arrange
        Long id = 1L;
        when(roomRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> roomService.delete(id));
    }

    private Room createRoomEntity(Long id, String name, User user) {
        Room room = new Room();
        room.setId(id);
        room.setName(name);
        room.setUser(user);
        return room;
    }

    private User createUserEntity(Long id, String username, String email) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }
}
