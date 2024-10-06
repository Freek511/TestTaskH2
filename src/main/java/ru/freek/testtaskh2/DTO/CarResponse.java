package ru.freek.testtaskh2.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.freek.testtaskh2.Entity.Car;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarResponse {
    Car car;
    String message;
}
