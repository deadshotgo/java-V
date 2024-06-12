package com.example.dom.controller;


import com.example.dom.dto.room.RoomDto;
import com.example.dom.models.Room;
import com.example.dom.models.User;
import com.example.dom.service.RoomService;
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
@RequestMapping("/api/v1/rooms")
@Tag(name = "Room", description = "Endpoints for room management")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GenerateApiDoc(
            summary = "Get all rooms",
            description = "Retrieve a list of all registered rooms",
            responseDescription = "List of rooms retrieved successfully",
            responseClass = Room.class
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findAll());
    }

    @GenerateApiDoc(
            summary = "Get room by ID",
            description = "Retrieve a room by its unique identifier",
            responseDescription = "Room retrieved successfully by ID",
            responseClass = Room.class
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findById(id));
    }

    @GenerateApiDoc(
            summary = "Create a new room",
            description = "Create a new room with the provided data",
            responseDescription = "Room created successfully",
            responseClass = Room.class
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Room> createRoom(@Valid @RequestBody RoomDto room) {
        Room entity = roomService.create(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GenerateApiDoc(
            summary = "Update room by ID",
            description = "Update a room by its unique identifier",
            responseDescription = "Room updated successfully",
            responseClass = Room.class
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomDto roomDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.update(id, roomDetails));
    }

    @GenerateApiDoc(
            summary = "Delete room by ID",
            description = "Delete a room by its unique identifier",
            responseDescription = "Room deleted successfully",
            responseClass = Long.class
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteRoom(@PathVariable Long id) {
      return ResponseEntity.status(HttpStatus.OK).body(roomService.delete(id));
    }
}