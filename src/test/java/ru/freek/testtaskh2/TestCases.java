package ru.freek.testtaskh2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import ru.freek.testtaskh2.Entity.Car;
import ru.freek.testtaskh2.Repository.CarRepo;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
public class TestCases {

    @Mock
    private CarRepo carRepo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate100kRecords() {
        when(carRepo.save(any(Car.class))).thenAnswer(i -> i.getArgument(0));
        when(carRepo.count()).thenReturn(100000L);
        IntStream.range(0, 100000).forEach(i -> {
            var car = Car.builder()
                    .vin("Vin " + i)
                    .model("Model " + i)
                    .price(i)
                    .build();
            carRepo.save(car);
        });
        Assertions.assertEquals(carRepo.count(), 100000L);
        verify(carRepo, times(100000)).save(any(Car.class));
    }

    @Test
    public void test100PoolConnection() throws InterruptedException {
        Car mockCar = new Car();
        mockCar.setId(1L);
        mockCar.setModel("Model");
        mockCar.setVin("Vin");
        mockCar.setPrice(1);

        when(carRepo.findById(anyLong())).thenReturn(Optional.of(mockCar));

        ExecutorService executorService = Executors.newFixedThreadPool(100);

        long startTime = System.currentTimeMillis();

        IntStream.range(0, 1000000).forEach(i -> {
            executorService.submit(() -> {
                long carId = 1L;
                carRepo.findById(carId);
            });
        });

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Total time: " + totalTime + " ms");

        verify(carRepo, times(1000000)).findById(anyLong());
    }


}
