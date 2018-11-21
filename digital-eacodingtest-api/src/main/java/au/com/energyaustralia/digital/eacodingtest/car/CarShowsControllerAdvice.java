package au.com.energyaustralia.digital.eacodingtest.car;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
class CarShowsControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarsRestClient.class);

    @ExceptionHandler(CarsApiError.class)
    public ResponseEntity<DiagnosticCarErrorResource> carsApiError(CarsApiError e) {
        return toResponse(e, e.getStatusCode(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DiagnosticCarErrorResource> carsApiError(Exception e) {
        return toResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<DiagnosticCarErrorResource> toResponse(Exception e, HttpStatus actualStatus, HttpStatus clientStatus) {
        final DiagnosticCarErrorResource diagnostic = DiagnosticCarErrorResourceFactory.toError(actualStatus);

        LOGGER.info(
                "diagnostic-code={} diagnostic-reason={} actual-status={} client-status={} message={}",
                diagnostic.getCode(),
                diagnostic.getMessage(),
                actualStatus.value(),
                clientStatus.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(diagnostic, clientStatus);
    }

    private static class DiagnosticCarErrorResourceFactory {

        private static final Map<HttpStatus, DiagnosticCarErrorResource> ERROR_DIAGNOSTIC_MAP = new EnumMap<>(HttpStatus.class);

        static {
            ERROR_DIAGNOSTIC_MAP.put(HttpStatus.BAD_GATEWAY, new DiagnosticCarErrorResource("E502", "A downstream system failed to process request. Try again later."));
            ERROR_DIAGNOSTIC_MAP.put(HttpStatus.BAD_REQUEST, new DiagnosticCarErrorResource("E400", "A downstream system failed to understand request. Try again later."));
            ERROR_DIAGNOSTIC_MAP.put(HttpStatus.INTERNAL_SERVER_ERROR, new DiagnosticCarErrorResource("E000", "An unexpected error occurred. Try again later."));
        }

        private DiagnosticCarErrorResourceFactory() {
        }

        static DiagnosticCarErrorResource toError(HttpStatus status) {
            return Optional
                    .ofNullable(ERROR_DIAGNOSTIC_MAP.get(status))
                    .orElse(ERROR_DIAGNOSTIC_MAP.get(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}
