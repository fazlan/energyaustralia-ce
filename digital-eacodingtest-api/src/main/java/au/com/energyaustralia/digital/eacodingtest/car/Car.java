package au.com.energyaustralia.digital.eacodingtest.car;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * The Car entities in consumed Car API response gets deserialised into this model.
 */
class Car {

    private final String make;
    private final String model;

    @JsonCreator
    Car(@JsonProperty("make") String make, @JsonProperty("model") String model) {
        this.make = make;
        this.model = model;
    }

    String getMake() {
        return make;
    }

    String getModel() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Car)) {
            return false;
        }
        Car car = (Car) o;
        return Objects.equals(make, car.make) && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(make, model);
    }
}
