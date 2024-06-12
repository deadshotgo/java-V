package com.example.dom.dto.device;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceDto {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @NotNull(message = "roomId is mandatory")
    private Long roomId;
}
