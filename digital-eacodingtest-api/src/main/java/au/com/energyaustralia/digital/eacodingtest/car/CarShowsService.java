package au.com.energyaustralia.digital.eacodingtest.car;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
class CarShowsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarShowsService.class);

    private final CarsRestClient client;

    private final CarShowsTransformer<List<CarMakeResource>> transformer;

    CarShowsService(CarsRestClient client, CarShowsTransformer<List<CarMakeResource>> transformer) {
        this.client = client;
        this.transformer = transformer;
    }

    List<CarMakeResource> list() {

        LOGGER.info("class={} method=list point=start", getClass().getSimpleName());

        List<FlattenedCarShow> flattenedCarShows = client.list()
                .stream()
                .flatMap(toFlattenedCarShows())
                .collect(Collectors.toList());

        List<CarMakeResource> response = transformer.transform(flattenedCarShows);

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

}
