package au.com.energyaustralia.digital.eacodingtest.car;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        Map<String, Map<String, List<String>>> result = new GroupByMakeShowsCarShowsTransformer().transform(exhibitions);

        System.out.println(new ObjectMapper().writeValueAsString(result));
        ;

        //Then
        Assert.assertThat(result.get("MK1").get("Show name one"), CoreMatchers.hasItems("Model1", "Model2"));
        Assert.assertThat(result.get("MK1").get("Show name two"), CoreMatchers.hasItems("Model1"));
        Assert.assertThat(result.get("MK2").get("Show name one"), CoreMatchers.hasItems("Model1"));
        Assert.assertThat(result.get("MK2").get("Show name two"), CoreMatchers.hasItems("Model1"));
    }
}
