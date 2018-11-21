package au.com.energyaustralia.digital.eacodingtest.car;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
class GroupByMakeShowsCarShowsTransformer implements CarShowsTransformer<Map<String, Map<String, List<String>>>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupByMakeShowsCarShowsTransformer.class);

    @Override
    public Map<String, Map<String, List<String>>> transform(List<FlattenedCarShow> cars) {

        LOGGER.info("class={} method=transform point=start cars={}", getClass().getSimpleName(), cars.size());

        Map<String, Map<String, List<String>>> response = cars
                .stream()
                .filter(hasShowName().and(hasCarModel()).and(hasCarMake()))
                .collect(
                        Collectors.groupingBy(FlattenedCarShow::getCarMake,
                                Collectors.groupingBy(FlattenedCarShow::getShow, Collectors.mapping(FlattenedCarShow::getCarModel, Collectors.toList()))
                        ));

        LOGGER.info("class={} method=transform point=completed number-of-grouped-by-make={}", getClass().getSimpleName(), response.size());

        return response;

    }

    /**
     * Predicate to test if car make is not empty or null.
     *
     * @return the predicate.
     */
    private Predicate<FlattenedCarShow> hasCarMake() {
        return cs -> !StringUtils.isEmpty(cs.getCarMake());
    }

    /**
     * Predicate to test if car model is not empty or null.
     *
     * @return the predicate.
     */
    private Predicate<FlattenedCarShow> hasCarModel() {
        return cs -> !StringUtils.isEmpty(cs.getCarModel());
    }

    /**
     * Predicate to test if show name is not empty or null.
     *
     * @return the predicate.
     */
    private Predicate<FlattenedCarShow> hasShowName() {
        return cs -> !StringUtils.isEmpty(cs.getShow());
    }
}
