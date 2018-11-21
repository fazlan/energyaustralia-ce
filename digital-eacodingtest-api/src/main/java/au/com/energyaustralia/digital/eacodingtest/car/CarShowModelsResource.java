package au.com.energyaustralia.digital.eacodingtest.car;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class CarShowModelsResource {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("models")
    private final List<String> models;

    CarShowModelsResource(@JsonProperty("name") String name,
                          @JsonProperty("models") List<String> models) {
        this.name = name;
        this.models = models;
    }

    String getName() {
        return name;
    }

    List<String> getModels() {
        return models;
    }
}
