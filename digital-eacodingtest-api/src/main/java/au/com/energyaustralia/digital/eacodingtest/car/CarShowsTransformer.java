package au.com.energyaustralia.digital.eacodingtest.car;

import java.util.List;
import java.util.function.Function;

/**
 * Interface to abstract the transformation logic for a list of <code>ExhibitionCar</code>
 *
 * @param <R> the response type of the transformation
 */
interface CarShowsTransformer<R> extends Function<List<FlattenedCarShow>, R> {

    R transform(List<FlattenedCarShow> cars);

    default R apply(List<FlattenedCarShow> cars) {
        return transform(cars);
    }
}
