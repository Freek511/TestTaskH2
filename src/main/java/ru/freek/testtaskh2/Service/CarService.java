package ru.freek.testtaskh2.Service;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import ru.freek.testtaskh2.DTO.CarRequest;
import ru.freek.testtaskh2.DTO.CarResponse;
import ru.freek.testtaskh2.Entity.Car;
import ru.freek.testtaskh2.Repository.CarRepo;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {
    private final CarRepo carRepo;

    public CarResponse deleteCarById(Long carId) {
        var response = CarResponse.builder()
                .car(carRepo.findById(carId).orElse(null))
                .message("Successfully deleted")
                .build();

        carRepo.deleteById(carId);
        return response;
    }

    public CarResponse saveCar(CarRequest carReq) {
        var car = Car.builder()
                .vin(carReq.getVin())
                .model(carReq.getModel())
                .price(carReq.getPrice())
                .build();
        carRepo.save(car);
        return CarResponse.builder()
                .car(car)
                .message("Successfully added")
                .build();
    }

    public List<Car> getAllCarList() {
        return carRepo.findAll();
    }

    public CarResponse updateCarById(CarRequest newCar, Long carId) {
        Optional <Car> oldCar = carRepo.findById(carId);

        if (oldCar.isEmpty()) {
            return CarResponse.builder()
                    .message("No such element")
                    .build();
        }
        oldCar.get().setVin(newCar.getVin());
        oldCar.get().setModel(newCar.getModel());
        oldCar.get().setPrice(newCar.getPrice());
        carRepo.save(oldCar.get());
        return CarResponse.builder()
                .message("Successfully updated")
                .car(oldCar.get())
                .build();
    }

    public CarResponse getCarById(Long carId) {
        return CarResponse.builder()
                .car(carRepo.findById(carId).orElse(null))
                .build();
    }

}
