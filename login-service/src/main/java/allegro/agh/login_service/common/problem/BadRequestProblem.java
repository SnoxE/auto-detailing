package allegro.agh.login_service.common.problem;

import java.util.Map;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

public class BadRequestProblem extends AbstractThrowableProblem {
  public BadRequestProblem(String detail) {
    super(Problem.DEFAULT_TYPE, Status.BAD_REQUEST.getReasonPhrase(), Status.BAD_REQUEST, detail);
  }

  public BadRequestProblem(String detail, Map<String, Object> parameters) {
    super(
        Problem.DEFAULT_TYPE,
        Status.BAD_REQUEST.getReasonPhrase(),
        Status.BAD_REQUEST,
        detail,
        null,
        null,
        parameters);
  }
}
