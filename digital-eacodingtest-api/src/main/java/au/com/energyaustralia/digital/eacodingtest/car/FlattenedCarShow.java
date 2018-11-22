package au.com.energyaustralia.digital.eacodingtest.car;

import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.function.Predicate;

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

    /**
     * Predicate to test if car make is not empty or null.
     */
    static final Predicate<FlattenedCarShow> HAS_CAR_MAKE = cs -> !StringUtils.isEmpty(cs.getCarMake());

    /**
     * Predicate to test if car model is not empty or null.
     **/
    static final Predicate<FlattenedCarShow> HAS_CAR_MODEL = cs -> !StringUtils.isEmpty(cs.getCarModel());

    /**
     * Predicate to test if show name is not empty or null.
     **/
    static final Predicate<FlattenedCarShow> HAS_CAR_SHOW = cs -> !StringUtils.isEmpty(cs.getShow());
}
