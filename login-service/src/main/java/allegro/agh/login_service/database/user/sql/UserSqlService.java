package allegro.agh.login_service.database.user.sql;

import static allegro.agh.login_service.common.resource.ResourceManager.readSqlQuery;

import allegro.agh.login_service.common.problem.AuthProblem;
import allegro.agh.login_service.common.problem.InternalServerErrorProblem;
import allegro.agh.login_service.database.user.dto.UserDto;
import allegro.agh.login_service.database.user.sql.model.UserLoginSqlRow;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserSqlService {

  private static final Logger log = LoggerFactory.getLogger(UserSqlService.class);

  private static final String INSERT_INTO_USERS = readSqlQuery("sql/user/insert_into_users.sql");
  private static final String SELECT_COUNT_BY_EMAIL =
      readSqlQuery("sql/user/select_user_count_by_email.sql");
  private static final String SELECT_USER_BY_EMAIL =
      readSqlQuery("sql/user/select_user_by_email.sql");

  private final NamedParameterJdbcOperations namedParameterJdbcOperations;
  private final PasswordEncoder passwordEncoder;

  public UserSqlService(
      NamedParameterJdbcOperations namedParameterJdbcOperations, PasswordEncoder passwordEncoder) {
    this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    this.passwordEncoder = passwordEncoder;
  }

  public UserDto createUser(
      String firstName, String lastName, String email, String password, String role)
      throws AuthProblem {
    try {
      MapSqlParameterSource parameterSource =
          insertUserParameterSource(
              new MapSqlParameterSource(), firstName, lastName, email, password, role);

      KeyHolder keyHolder = new GeneratedKeyHolder();

      namedParameterJdbcOperations.update(INSERT_INTO_USERS, parameterSource, keyHolder);
      return mapKeysToUserDto(Objects.requireNonNull(keyHolder.getKeys()));
    } catch (DataAccessException | NullPointerException e) {
      log.error("Unable to create user due to an unexpected error message={}", e.getMessage(), e);
      throw new InternalServerErrorProblem();
    }
  }

  public Integer getCountByEmail(String email) {
    try {
      MapSqlParameterSource parameterSource =
          mapEmailParameterSource(new MapSqlParameterSource(), email);
      return namedParameterJdbcOperations.queryForObject(
          SELECT_COUNT_BY_EMAIL, parameterSource, Integer.class);
    } catch (DataAccessException e) {
      log.error(
          "Unable to retrieve user count due to an unexpected error message={}", e.getMessage(), e);
      throw new InternalServerErrorProblem();
    }
  }

  public UserLoginSqlRow getUserByEmail(String email) {
    try {
      MapSqlParameterSource parameterSource =
          mapEmailParameterSource(new MapSqlParameterSource(), email);
      return namedParameterJdbcOperations.queryForObject(
          SELECT_USER_BY_EMAIL, parameterSource, new DataClassRowMapper<>(UserLoginSqlRow.class));
    } catch (DataAccessException e) {
      log.error(
          "Unable to retrieve user by email due to an unexpected error message={}",
          e.getMessage(),
          e);
      throw new InternalServerErrorProblem();
    }
  }

  private MapSqlParameterSource insertUserParameterSource(
      MapSqlParameterSource parameterSource,
      String firstName,
      String lastName,
      String email,
      String password,
      String role) {
    parameterSource.addValue("first_name", firstName);
    parameterSource.addValue("last_name", lastName);
    parameterSource.addValue("email", email);
    System.out.println();
    parameterSource.addValue("password", passwordEncoder.encode(password));
    parameterSource.addValue("role", role);
    return parameterSource;
  }

  private UserDto mapKeysToUserDto(Map<String, Object> keys) {
    int id = (int) keys.get("id");
    String firstName = keys.get("first_name").toString();
    String lastName = keys.get("last_name") != null ? keys.get("last_name").toString() : null;
    String email = keys.get("email").toString();
    String role = keys.get("role").toString();

    return new UserDto(id, firstName, lastName, email, role);
  }

  private MapSqlParameterSource mapEmailParameterSource(
      MapSqlParameterSource parameterSource, String email) {
    return parameterSource.addValue("email", email);
  }
}
