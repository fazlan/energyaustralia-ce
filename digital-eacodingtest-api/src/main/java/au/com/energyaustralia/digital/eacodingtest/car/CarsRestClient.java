package au.com.energyaustralia.digital.eacodingtest.car;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
class CarsRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarsRestClient.class);

    private final String apiUri;
    private final RestTemplate template;

    CarsRestClient(@Value("${app.endpoints.rest.energy-australia.api}") String apiUri, RestTemplate template) {
        this.apiUri = apiUri;
        this.template = template;
    }

    List<CarShow> list() {

        LOGGER.info("class={} method=list point=started", CarsRestClient.class.getSimpleName());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        List<CarShow> cars = template
                .exchange(apiUri + "/cars", HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<CarShow>>() {
                })
                .getBody();

        cars = Objects.nonNull(cars) ? cars : Collections.emptyList();

        LOGGER.info("class={} method=list point=completed number-of-cars={}", getClass().getSimpleName(), cars.size());

        return cars;
    }
}
