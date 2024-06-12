package com.example.dom.models;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "room")
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Room(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public static Room toModel(Room entity) {
        Room model = new Room();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setUser(User.toModel(entity.getUser()));
        return model;
    }
}
