package com.example.dom.dto.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "userId is mandatory")
    private Long userId;
}
