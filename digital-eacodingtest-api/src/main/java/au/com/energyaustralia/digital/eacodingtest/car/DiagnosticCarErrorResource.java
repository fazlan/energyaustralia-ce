package au.com.energyaustralia.digital.eacodingtest.car;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an diagnostic error and it's information.
 */
class DiagnosticCarErrorResource {

    @JsonProperty("code")
    private final String code;

    @JsonProperty("message")
    private final String message;


    @JsonCreator
    DiagnosticCarErrorResource(@JsonProperty("code") String code,
                               @JsonProperty("message") String message) {
        this.code = code;
        this.message = message;
    }

    String getCode() {
        return code;
    }

    String getMessage() {
        return message;
    }
}
