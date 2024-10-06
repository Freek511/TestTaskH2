package ru.freek.testtaskh2.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.freek.testtaskh2.DTO.CarRequest;
import ru.freek.testtaskh2.DTO.CarResponse;
import ru.freek.testtaskh2.Entity.Car;
import ru.freek.testtaskh2.Service.CarService;

import java.util.List;

@RestController
@RequestMapping("/cars")
@AllArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping
    public ResponseEntity<CarResponse> saveCar (@RequestBody CarRequest car) {
        var carResp = carService.saveCar(car);
        return new ResponseEntity<>(carResp, HttpStatusCode.valueOf(201));
    }

    @GetMapping
    public List<Car> getAllCarList() {
        return carService.getAllCarList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable("id") Long carId) {
        var carResp = carService.getCarById(carId);

        if (carResp.getCar() == null) {
           carResp.setMessage("No such element with id: " + carId);
           return new ResponseEntity<>(carResp, HttpStatusCode.valueOf(400));
        }
        return new ResponseEntity<>(carResp,HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarResponse> deleteCarById (@PathVariable("id") Long carId) {
        var carResp = carService.deleteCarById(carId);

        if (carResp.getCar() == null) {
            carResp.setMessage("No such element");
            return new ResponseEntity<>(carResp, HttpStatusCode.valueOf(400));
        }

        return new ResponseEntity<>(carResp, HttpStatusCode.valueOf(200));

    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> updateCarById(@PathVariable("id") Long carId, @RequestBody CarRequest newCar) {
        var carResp = carService.updateCarById(newCar, carId);
        if (carResp.getCar() == null) {
            return new ResponseEntity<>(carResp, HttpStatusCode.valueOf(400));
        }
        return new ResponseEntity<>(carResp, HttpStatusCode.valueOf(200));
    }
}
