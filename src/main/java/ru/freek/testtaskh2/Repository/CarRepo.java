package ru.freek.testtaskh2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.freek.testtaskh2.Entity.Car;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {

}
