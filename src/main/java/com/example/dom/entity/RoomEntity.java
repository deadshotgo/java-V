package com.example.dom.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table( name = "room")
@Data
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
