package allegro.agh.auto_detailing.common.problem;

import java.util.Map;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

public class ConflictProblem extends AbstractThrowableProblem {

  public ConflictProblem(String detail) {
    super(Problem.DEFAULT_TYPE, Status.CONFLICT.getReasonPhrase(), Status.CONFLICT, detail);
  }

  public ConflictProblem(String detail, Map<String, Object> parameters) {
    super(
        Problem.DEFAULT_TYPE,
        Status.CONFLICT.getReasonPhrase(),
        Status.CONFLICT,
        detail,
        null,
        null,
        parameters);
  }
}
