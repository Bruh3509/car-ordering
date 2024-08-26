package com.demo.cars.mapper;

import com.demo.cars.entity.Car;
import com.demo.cars.entity.Place;
import com.demo.cars.model.car.CarRequest;

public class CarBuilder {
    public static Car buildEntity(Place place, CarRequest request) {
        Car car = new Car();

        car.setPlace(place);
        car.setCarClass(request.carClass());
        car.setBrand(request.brand());
        car.setModel(request.model());
        car.setNumberOfSeats(request.numberOfSeats());
        car.setYearOfProduction(request.yearOfProduction());
        car.setPlateNumber(request.plateNumber());
        car.setIsAvailable(request.isAvailable());
        car.setDailyFee(request.dailyFee());

        return car;
    }

    private CarBuilder() {
    }
}
