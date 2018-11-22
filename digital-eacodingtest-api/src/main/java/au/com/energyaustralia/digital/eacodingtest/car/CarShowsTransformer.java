package au.com.energyaustralia.digital.eacodingtest.car;

import java.util.List;
import java.util.function.Function;

/**
 * Interface to abstract the transformation logic for a list of <code>ExhibitionCar</code>
 *
 * @param <R> the resource type of the mapped transformation
 */
interface CarShowsTransformer<R> {

    R transform(List<FlattenedCarShow> cars);

}
