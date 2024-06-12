package com.example.dom.models;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name="device" )
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Device(String name, String type, Room room) {
        this.name = name;
        this.type = type;
        this.room = room;
    }

    public static Device toModel(Device entity) {
        Device model = new Device();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setType(entity.getType());
        model.setRoom(Room.toModel(entity.getRoom()));
        return model;
    }
}
