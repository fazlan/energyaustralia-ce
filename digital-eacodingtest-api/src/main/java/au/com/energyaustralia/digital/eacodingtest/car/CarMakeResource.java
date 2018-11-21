package au.com.energyaustralia.digital.eacodingtest.car;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class CarMakeResource {
    @JsonProperty("make")
    private final String make;
    @JsonProperty("shows")
    private final List<CarShowModelsResource> shows;

    @JsonCreator
    CarMakeResource(@JsonProperty("make") String make, @JsonProperty("shows") List<CarShowModelsResource> shows) {
        this.make = make;
        this.shows = shows;
    }

    String getMake() {
        return make;
    }

    List<CarShowModelsResource> getShows() {
        return shows;
    }
}
