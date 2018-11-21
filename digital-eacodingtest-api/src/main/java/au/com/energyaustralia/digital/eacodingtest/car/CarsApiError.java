package au.com.energyaustralia.digital.eacodingtest.car;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;


class CarsApiError extends HttpStatusCodeException {

    CarsApiError(HttpStatus statusCode) {
        super(statusCode);
    }
}
