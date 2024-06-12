package com.example.dom.service;

import com.example.dom.dto.room.RoomDto;
import com.example.dom.models.Room;
import com.example.dom.models.User;
import com.example.dom.repository.RoomRepository;
import com.example.dom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository repository;
    @Autowired
    private UserService userService;

    public List<Room> findAll() {
        List<Room> entities = repository.findAll();
        return entities.stream()
                .map(Room::toModel)
                .collect(Collectors.toList());
    }
    public Room findById(Long id) {
        Room entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return Room.toModel(entity);
    }

    public Room create(RoomDto room) {
        User user = userService.getUser(room.getUserId());
        Room entity = new Room(room.getName(), user);
        return Room.toModel(repository.save(entity));
    }

    public Room update(Long id, RoomDto roomDetails) {
        Room room = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        User user = userService.getUser(roomDetails.getUserId());
        room.setName(roomDetails.getName());
        room.setUser(user);
        Room updatedRoom = repository.save(room);
        return Room.toModel(updatedRoom);
    }

    public Long delete(Long id) {
        Room room = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        repository.delete(room);

        return id;
    }
}
