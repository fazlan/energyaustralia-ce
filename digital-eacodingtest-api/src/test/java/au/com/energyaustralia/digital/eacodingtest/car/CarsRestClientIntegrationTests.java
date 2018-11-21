package au.com.energyaustralia.digital.eacodingtest.car;


import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CarsRestClientConfig.class, CarsRestClient.class})
@RestClientTest
public class CarsRestClientIntegrationTests {

    @Value("${app.endpoints.rest.energy-australia.api}")
    private String carsApiUri;

    @Autowired
    private MockRestServiceServer mockedApiServer;

    @Autowired
    private CarsRestClient carsRestClient;

    @Test
    public void shouldListAllCarShowsReturnedByCarApi() {

        this.mockedApiServer
                .expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(carsApiUri + "/cars"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(
                        MockRestResponseCreators
                                .withStatus(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body("[{\"name\" : \"Show one\", \"cars\" : [{\"make\" : \"MakeONE\", \"model\" : \"ModelA\"}]}]")
                );

        List<CarShow> response = carsRestClient.list();

        Assert.assertThat(response, CoreMatchers.hasItem(new CarShow("Show one", Collections.singletonList(new Car("MakeONE", "ModelA")))));
    }

    @Test(expected = CarsApiError.class)
    public void shouldThrowServerApiErrorWhenApiReturn5xxStatus() {

        this.mockedApiServer
                .expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(carsApiUri + "/cars"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(
                        MockRestResponseCreators
                                .withStatus(HttpStatus.BAD_GATEWAY)
                );

        carsRestClient.list();
    }

    @Test(expected = CarsApiError.class)
    public void shouldThrowServerApiErrorWhenApiReturn4xxStatus() {

        this.mockedApiServer
                .expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(carsApiUri + "/cars"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(
                        MockRestResponseCreators
                                .withStatus(HttpStatus.BAD_REQUEST)
                );

        carsRestClient.list();
    }
}
