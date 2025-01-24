package allegro.agh.login_service.common.problem;


import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.Map;

public class AuthProblem extends AbstractThrowableProblem {

    public AuthProblem(String detail){
        super(Problem.DEFAULT_TYPE, Status.UNAUTHORIZED.getReasonPhrase(), Status.UNAUTHORIZED, detail);
    }

    public AuthProblem(String detail, Map<String, Object> parameters){
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
