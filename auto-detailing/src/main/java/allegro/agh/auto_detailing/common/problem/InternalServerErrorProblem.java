package allegro.agh.auto_detailing.common.problem;

import java.util.Map;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

public class InternalServerErrorProblem extends AbstractThrowableProblem {

  public InternalServerErrorProblem() {
    super(
        Problem.DEFAULT_TYPE,
        Status.INTERNAL_SERVER_ERROR.getReasonPhrase(),
        Status.INTERNAL_SERVER_ERROR);
  }

  public InternalServerErrorProblem(String detail) {
    super(
        Problem.DEFAULT_TYPE,
        Status.INTERNAL_SERVER_ERROR.getReasonPhrase(),
        Status.INTERNAL_SERVER_ERROR,
        detail);
  }

  public InternalServerErrorProblem(String detail, Map<String, Object> parameters) {
    super(
        Problem.DEFAULT_TYPE,
        Status.INTERNAL_SERVER_ERROR.getReasonPhrase(),
        Status.INTERNAL_SERVER_ERROR,
        detail,
        null,
        null,
        parameters);
  }
}
