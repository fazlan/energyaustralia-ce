package au.com.energyaustralia.digital.eacodingtest.car;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(JUnit4.class)
public class GroupByMakeCarShowsTransformerTest {

    @Test
    public void shouldTransformCarShowsWhenValid() throws JsonProcessingException {
        //Given
        List<FlattenedCarShow> exhibitions = Arrays.asList(
                new FlattenedCarShow("Show name one", new Car("MK1", "Model1")),
                new FlattenedCarShow("Show name one", new Car("MK1", "Model2")),
                new FlattenedCarShow("Show name one", new Car("MK2", "Model1")),
                new FlattenedCarShow("Show name two", new Car("MK1", "Model1")),
                new FlattenedCarShow("Show name two", new Car("MK2", "Model1"))
        );

        //When
        List<CarMakeResource> response = new GroupByMakeShowsCarShowsTransformer().transform(exhibitions);

        //Then
        Assert.assertThat(response.size(), Is.is(2));
        Assert.assertThat(getMakeName(response.get(0)), Is.is("MK1"));
        Assert.assertThat(
                getShowNameModels(response.get(0)),
                IsIterableContainingInOrder.contains(
                        "Show name one", "Model1", "Model2",
                        "Show name two", "Model1"
                ));

        Assert.assertThat(getMakeName(response.get(1)), Is.is("MK2"));
        Assert.assertThat(
                getShowNameModels(response.get(1)),
                IsIterableContainingInOrder.contains(
                        "Show name one", "Model1",
                        "Show name two", "Model1"
                ));
    }

    private static String getMakeName(CarMakeResource resource) {
        return resource.getMake();
    }

    private static List<String> getShowNameModels(CarMakeResource resource) {
        return resource.getShows()
                .stream()
                .flatMap(s -> Stream.concat(Stream.of(s.getName()), s.getModels().stream()))
                .collect(Collectors.toList());
    }
}
