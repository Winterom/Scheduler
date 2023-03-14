package alexey.grizly.com.commons.errors;


import org.springframework.http.HttpStatusCode;


public record AppResponseErrorDto(HttpStatusCode statusCode, String message) {
    @Override
    public HttpStatusCode statusCode() {
        return statusCode;
    }

    @Override
    public String message() {
        return message;
    }
}
