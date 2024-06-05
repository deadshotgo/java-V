package com.example.dom.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table( name="device" )
@Data
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;
}
