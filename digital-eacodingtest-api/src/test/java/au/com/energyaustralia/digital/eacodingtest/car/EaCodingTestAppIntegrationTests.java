package au.com.energyaustralia.digital.eacodingtest.car;

import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.WireMockRestServiceServer;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EaCodingTestAppIntegrationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CarsRestClient carsClient;

    @Value("${app.endpoints.rest.energy-australia.api}")
    private String apiUri;

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldReturnListOfCarModelsGroupByItsMakeDisplayedAtMelbourneMotorShowAndSortByMakeShowModel() throws Exception {

        MockRestServiceServer server = WireMockRestServiceServer.with(this.restTemplate)
                .baseUrl(apiUri)
                .stubs("classpath:/stubs/cars/valid/single-show.json")
                .build();

        mvc.perform(get("/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(4)))

                .andExpect(jsonPath("$[0].make", Is.is("George Motors")))
                .andExpect(jsonPath("$[0].shows[0].name", Is.is("Melbourne Motor Show")))
                .andExpect(jsonPath("$[0].shows[0].models", CoreMatchers.hasItems("George 15")))

                .andExpect(jsonPath("$[1].make", Is.is("Hondaka")))
                .andExpect(jsonPath("$[1].shows[0].name", Is.is("Melbourne Motor Show")))
                .andExpect(jsonPath("$[1].shows[0].models", CoreMatchers.hasItems("Elisa")))

                .andExpect(jsonPath("$[2].make", Is.is("Julio Mechannica")))
                .andExpect(jsonPath("$[2].shows[0].name", Is.is("Melbourne Motor Show")))
                .andExpect(jsonPath("$[2].shows[0].models", CoreMatchers.hasItems("Mark 4S")))

                .andExpect(jsonPath("$[3].make", Is.is("Moto Tourismo")))
                .andExpect(jsonPath("$[3].shows[0].name", Is.is("Melbourne Motor Show")))
                .andExpect(jsonPath("$[3].shows[0].models", IsIterableContainingInOrder.contains("Cyclissimo", "Delta 4")));

        server.verify();
    }

    @Test
    public void shouldReturnListOfCarModelsGroupByItsMakeDisplayedAtMultipleShowsAndSortByMakeShowModel() throws Exception {

        MockRestServiceServer server = WireMockRestServiceServer.with(this.restTemplate)
                .baseUrl(apiUri)
                .stubs("classpath:/stubs/cars/valid/multiple-shows.json")
                .build();

        mvc.perform(get("/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(4)))

                .andExpect(jsonPath("$[0].make", Is.is("George Motors")))
                .andExpect(jsonPath("$[0].shows[0].name", Is.is("Cartopia")))
                .andExpect(jsonPath("$[0].shows[0].models", CoreMatchers.hasItems("George 15")))
                .andExpect(jsonPath("$[0].shows[1].name", Is.is("Melbourne Motor Show")))
                .andExpect(jsonPath("$[0].shows[1].models", CoreMatchers.hasItems("George 15")))

                .andExpect(jsonPath("$[1].make", Is.is("Hondaka")))
                .andExpect(jsonPath("$[1].shows[0].name", Is.is("Cartopia")))
                .andExpect(jsonPath("$[1].shows[0].models", CoreMatchers.hasItems("Ellen")))
                .andExpect(jsonPath("$[1].shows[1].name", Is.is("Melbourne Motor Show")))
                .andExpect(jsonPath("$[1].shows[1].models", CoreMatchers.hasItems("Elisa")))

                .andExpect(jsonPath("$[2].make", Is.is("Julio Mechannica")))
                .andExpect(jsonPath("$[2].shows[0].name", Is.is("Cartopia")))
                .andExpect(jsonPath("$[2].shows[0].models", CoreMatchers.hasItems("Mark 2")))
                .andExpect(jsonPath("$[2].shows[1].name", Is.is("Melbourne Motor Show")))
                .andExpect(jsonPath("$[2].shows[1].models", CoreMatchers.hasItems("Mark 4S")))

                .andExpect(jsonPath("$[3].make", Is.is("Moto Tourismo")))
                .andExpect(jsonPath("$[3].shows[0].name", Is.is("Cartopia")))
                .andExpect(jsonPath("$[3].shows[0].models", Is.is(Arrays.asList("Cyclissimo", "Delta 16", "Delta 4"))))
                .andExpect(jsonPath("$[3].shows[1].name", Is.is("Melbourne Motor Show")))
                .andExpect(jsonPath("$[3].shows[1].models", IsIterableContainingInOrder.contains("Cyclissimo", "Delta 4")));

        server.verify();
    }

    @Test
    public void shouldReturnListOfCarModelsGroupByItsMakeDisplayedAtTheShowAndSortByMakeShowModelIgnoringDuplicateCars() throws Exception {

        MockRestServiceServer server = WireMockRestServiceServer.with(this.restTemplate)
                .baseUrl(apiUri)
                .stubs("classpath:/stubs/cars/valid/single-show-with-duplicate-cars.json")
                .build();

        mvc.perform(get("/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(2)))

                .andExpect(jsonPath("$[0].make", Is.is("Hondaka")))
                .andExpect(jsonPath("$[0].shows[0].name", Is.is("Carographic")))
                .andExpect(jsonPath("$[0].shows[0].models", CoreMatchers.hasItems("Elisa")))

                .andExpect(jsonPath("$[1].make", Is.is("Julio Mechannica")))
                .andExpect(jsonPath("$[1].shows[0].name", Is.is("Carographic")))
                .andExpect(jsonPath("$[1].shows[0].models", IsIterableContainingInOrder.contains("Mark 2", "Mark 4")));

        server.verify();
    }

    @Test
    public void shouldReturnListOfCarModelsGroupByItsMakeDisplayedAtTheShowAndSortByMakeShowModelIgnoringMissingCarModels() throws Exception {

        MockRestServiceServer server = WireMockRestServiceServer.with(this.restTemplate)
                .baseUrl(apiUri)
                .stubs("classpath:/stubs/cars/valid/single-show-with-missing-model.json")
                .build();

        mvc.perform(get("/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(2)))

                .andExpect(jsonPath("$[0].make", Is.is("Hondaka")))
                .andExpect(jsonPath("$[0].shows[0].name", Is.is("Carographic")))
                .andExpect(jsonPath("$[0].shows[0].models", CoreMatchers.hasItems("Elisa")))

                .andExpect(jsonPath("$[1].make", Is.is("Julio Mechannica")))
                .andExpect(jsonPath("$[1].shows[0].name", Is.is("Carographic")))
                .andExpect(jsonPath("$[1].shows[0].models", IsIterableContainingInOrder.contains("Mark 2", "Mark 4")));

        server.verify();
    }

    @Test
    public void shouldReturnListOfCarModelsGroupByItsMakeDisplayedAtTheShowAndSortByMakeShowModelIgnoringMissingCarMake() throws Exception {

        MockRestServiceServer server = WireMockRestServiceServer.with(this.restTemplate)
                .baseUrl(apiUri)
                .stubs("classpath:/stubs/cars/valid/single-show-with-missing-make.json")
                .build();

        mvc.perform(get("/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(3)))

                .andExpect(jsonPath("$[0].make", Is.is("George Motors")))
                .andExpect(jsonPath("$[0].shows[0].name", Is.is("New York Car Show")))
                .andExpect(jsonPath("$[0].shows[0].models", CoreMatchers.hasItems("George 15")))

                .andExpect(jsonPath("$[1].make", Is.is("Julio Mechannica")))
                .andExpect(jsonPath("$[1].shows[0].name", Is.is("New York Car Show")))
                .andExpect(jsonPath("$[1].shows[0].models", IsIterableContainingInOrder.contains("Mark 1")))

                .andExpect(jsonPath("$[2].make", Is.is("Moto Tourismo")))
                .andExpect(jsonPath("$[2].shows[0].name", Is.is("New York Car Show")))
                .andExpect(jsonPath("$[2].shows[0].models", IsIterableContainingInOrder.contains("Cyclissimo")));

        server.verify();
    }

    @Test
    public void shouldReturnEmptyListOfCarModelsWhenShowNameIsMissing() throws Exception {

        MockRestServiceServer server = WireMockRestServiceServer.with(this.restTemplate)
                .baseUrl(apiUri)
                .stubs("classpath:/stubs/cars/valid/single-show-with-missing-show.json")
                .build();

        mvc.perform(get("/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(0)));

        server.verify();
    }

    @Test
    public void shouldReturnEmptyListOfCarModelsWhenCarsApiReturnsNoContent() throws Exception {

        MockRestServiceServer server = WireMockRestServiceServer.with(this.restTemplate)
                .baseUrl(apiUri)
                .stubs("classpath:/stubs/cars/valid/no-content.json")
                .build();

        mvc.perform(get("/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(0)));

        server.verify();
    }

    @Test
    public void shouldReturnEmptyListOfCarModelsWhenCarsApiReturnsEmptyList() throws Exception {

        MockRestServiceServer server = WireMockRestServiceServer.with(this.restTemplate)
                .baseUrl(apiUri)
                .stubs("classpath:/stubs/cars/valid/empty-car-shows.json")
                .build();

        mvc.perform(get("/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(0)));

        server.verify();
    }

    @Test
    public void shouldReturnErrorDiagnosticsWhenCarsApiReturns502() throws Exception {

        MockRestServiceServer server = WireMockRestServiceServer.with(this.restTemplate)
                .baseUrl(apiUri)
                .stubs("classpath:/stubs/cars/invalid/unreachable-502.json")
                .build();

        mvc.perform(get("/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.code", Is.is("E502")))
                .andExpect(jsonPath("$.message", Is.is("A downstream system failed to process request. Try again later.")));

        server.verify();
    }

    @Test
    public void shouldReturnErrorDiagnosticsWhenCarsApiReturns400() throws Exception {

        MockRestServiceServer server = WireMockRestServiceServer.with(this.restTemplate)
                .baseUrl(apiUri)
                .stubs("classpath:/stubs/cars/invalid/bad-request-400.json")
                .build();

        mvc.perform(get("/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.code", Is.is("E400")))
                .andExpect(jsonPath("$.message", Is.is("A downstream system failed to understand request. Try again later.")));

        server.verify();
    }
}
