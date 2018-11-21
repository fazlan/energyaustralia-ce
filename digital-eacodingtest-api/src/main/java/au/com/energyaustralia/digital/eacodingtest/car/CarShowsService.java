package au.com.energyaustralia.digital.eacodingtest.car;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
class CarShowsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarShowsService.class);

    private final CarsRestClient client;

    private final CarShowsTransformer<Map<String, Map<String, List<String>>>> transformer;

    CarShowsService(CarsRestClient client, CarShowsTransformer<Map<String, Map<String, List<String>>>> transformer) {
        this.client = client;
        this.transformer = transformer;
    }

    List<CarMakeResource> list() {

        LOGGER.info("class={} method=list point=start", getClass().getSimpleName());

        List<FlattenedCarShow> flattenedCarShows = client.list()
                .stream()
                .flatMap(toFlattenedCarShows())
                .collect(Collectors.toList());

        LOGGER.info("class={} method=list point=middle number-of-flattened-cars={}", getClass().getSimpleName(), flattenedCarShows.size());

        List<CarMakeResource> response = transformer.transform(flattenedCarShows).entrySet()
                .stream()
                .map(toCarMakeResource())
                .sorted(Comparator.comparing(CarMakeResource::getMake))
                .collect(Collectors.toList());

        LOGGER.info("class={} method=list point=completed number-of-transformed-cars={}", getClass().getSimpleName(), response.size());

        return response;
    }

    /**
     * Flattens stream of <code>Car</code> into <code>FlattenedCarShow</code>
     *
     * @return mapper function to flatten the objects.
     */
    private Function<CarShow, Stream<FlattenedCarShow>> toFlattenedCarShows() {
        return cs -> cs.getCars().stream().distinct().map(c -> new FlattenedCarShow(cs.getName(), c));
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
