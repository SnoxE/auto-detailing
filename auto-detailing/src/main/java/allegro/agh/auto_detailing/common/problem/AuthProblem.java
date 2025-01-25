package allegro.agh.auto_detailing.common.problem;

import java.util.Map;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

public class AuthProblem extends AbstractThrowableProblem {

  public AuthProblem(String detail) {
    super(Problem.DEFAULT_TYPE, Status.UNAUTHORIZED.getReasonPhrase(), Status.UNAUTHORIZED, detail);
  }

  public AuthProblem(String detail, Map<String, Object> parameters) {
    super(
        Problem.DEFAULT_TYPE,
        Status.UNAUTHORIZED.getReasonPhrase(),
        Status.UNAUTHORIZED,
        detail,
        null,
        null,
        parameters);
  }
}
