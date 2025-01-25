package allegro.agh.login_service.config;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.zalando.problem.AbstractThrowableProblem;

@RestControllerAdvice
public class ProblemExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(AbstractThrowableProblem.class)
  public ResponseEntity<Object> handleProblemExceptions(
      AbstractThrowableProblem ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    putIfNotNull(body, "title", ex.getTitle());
    putIfNotNull(body, "status", ex.getStatus() != null ? ex.getStatus().getStatusCode() : null);
    putIfNotNull(body, "detail", ex.getDetail());
    putIfNotNull(body, "instance", ex.getInstance());
    putIfNotNull(body, "parameters", ex.getParameters());

    HttpStatusCode status =
        ex.getStatus() == null
            ? HttpStatus.INTERNAL_SERVER_ERROR
            : HttpStatus.valueOf(ex.getStatus().getStatusCode());

    return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
  }

  private static void putIfNotNull(Map<String, Object> map, String key, Object value) {
    if (value != null) {
      map.put(key, value);
    }
  }
}
