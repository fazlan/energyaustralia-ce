package au.com.energyaustralia.digital.eacodingtest.car;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
class GroupByMakeShowsCarShowsTransformer implements CarShowsTransformer<List<CarMakeResource>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupByMakeShowsCarShowsTransformer.class);

    @Override
    public List<CarMakeResource> transform(List<FlattenedCarShow> cars) {

        LOGGER.info("class={} method=transform point=start number-of-flattened-cars={}", getClass().getSimpleName(), cars.size());

        Map<String, Map<String, List<String>>> groupedByCars = cars
                .stream()
                .filter(FlattenedCarShow.HAS_CAR_MAKE.and(FlattenedCarShow.HAS_CAR_MODEL).and(FlattenedCarShow.HAS_CAR_SHOW))
                .collect(
                        Collectors.groupingBy(FlattenedCarShow::getCarMake,
                                Collectors.groupingBy(FlattenedCarShow::getShow, Collectors.mapping(FlattenedCarShow::getCarModel, Collectors.toList()))
                        ));

        LOGGER.info("class={} method=transform point=completed number-of-grouped-by-make={}", getClass().getSimpleName(), groupedByCars.size());

        return groupedByCars.entrySet()
                .stream()
                .map(toCarMakeResource())
                .sorted(Comparator.comparing(CarMakeResource::getMake))
                .collect(Collectors.toList());
    }

    /**
     * Maps Map<Make, Map<Show, List<Model>> into a CarMakeResource. Sorts the shows by name in natural order.
     *
     * @return the transformed <code>CarMakeResource</code>
     */
    private Function<Map.Entry<String, Map<String, List<String>>>, CarMakeResource> toCarMakeResource() {
        return carsByMake -> {

            List<CarShowModelsResource> carShowModelsResources = carsByMake.getValue().entrySet()
                    .stream()
                    .map(toCarShowResource())
                    .sorted(Comparator.comparing(CarShowModelsResource::getName))
                    .collect(Collectors.toList());

            return new CarMakeResource(carsByMake.getKey(), carShowModelsResources);
        };
    }

    /**
     * Maps Map<Show, List<Model> into a CarShowModelsResource. Sorts the models in natural order.
     *
     * @return the transformed <code>CarShowModelsResource</code>
     */
    private Function<Map.Entry<String, List<String>>, CarShowModelsResource> toCarShowResource() {
        return carsByShow -> {
            List<String> models = carsByShow.getValue();
            Collections.sort(models);

            return new CarShowModelsResource(carsByShow.getKey(), models);
        };
    }
}
