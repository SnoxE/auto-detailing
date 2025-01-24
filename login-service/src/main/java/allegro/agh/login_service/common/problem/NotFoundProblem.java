package allegro.agh.login_service.common.problem;

import java.util.Map;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

public class NotFoundProblem extends AbstractThrowableProblem {
  public NotFoundProblem(String detail) {
    super(Problem.DEFAULT_TYPE, Status.NOT_FOUND.getReasonPhrase(), Status.NOT_FOUND, detail);
  }

  public NotFoundProblem(String detail, Map<String, Object> parameters) {
    super(
        Problem.DEFAULT_TYPE,
        Status.NOT_FOUND.getReasonPhrase(),
        Status.NOT_FOUND,
        detail,
        null,
        null,
        parameters);
  }
}
