package com.example.springAprendiz.Dtos;

import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRecordDto(@NotBlank String name, @NotBlank String category, String description,
                               @PositiveOrZero Double price, @PositiveOrZero Integer amount,
                               Integer productStatus) {
}
