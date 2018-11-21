package au.com.energyaustralia.digital.eacodingtest.car;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

class CarShow {

    private final String name;
    private final List<Car> cars;

    @JsonCreator
    public CarShow(@JsonProperty("name") String name, @JsonProperty("cars") List<Car> cars) {
        this.name = name;
        this.cars = cars;
    }

    String getName() {
        return name;
    }

    List<Car> getCars() {
        return cars;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CarShow)) {
            return false;
        }
        CarShow carShow = (CarShow) o;
        return Objects.equals(name, carShow.name) && Objects.equals(cars, carShow.cars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cars);
    }
}
