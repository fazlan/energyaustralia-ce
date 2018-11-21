package au.com.energyaustralia.digital.eacodingtest.car;

import java.util.Optional;

class FlattenedCarShow {
    private final String show;
    private final Car car;

    FlattenedCarShow(String show, Car car) {
        this.show = show;
        this.car = car;
    }

    String getShow() {
        return show;
    }

    String getCarMake() {
        return Optional.ofNullable(car).map(Car::getMake).orElse(null);
    }

    String getCarModel() {
        return Optional.ofNullable(car).map(Car::getModel).orElse(null);
    }
}
