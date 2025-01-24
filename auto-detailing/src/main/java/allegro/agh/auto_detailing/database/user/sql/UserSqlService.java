package allegro.agh.auto_detailing.database.user.sql;

import static allegro.agh.auto_detailing.common.resource.ResourceManager.readSqlQuery;

import allegro.agh.auto_detailing.common.exceptions.DgAuthException;
import allegro.agh.auto_detailing.database.user.dto.UserDto;
import allegro.agh.auto_detailing.database.user.sql.model.UserSqlRow;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserSqlService {

  private static final Logger log = LoggerFactory.getLogger(UserSqlService.class);

  private static final String INSERT_INTO_USERS = readSqlQuery("sql/insert/insert_into_users.sql");
  private static final String SELECT_COUNT_BY_EMAIL =
      readSqlQuery("sql/select/user/select_user_count_by_email.sql");
  private static final String SELECT_USER_BY_ID =
      readSqlQuery("sql/select/user/select_user_by_id.sql");
  private static final String SELECT_USER_BY_EMAIL =
      readSqlQuery("sql/select/user/select_user_by_email.sql");
  private static final String SELECT_USER_BY_EMAIL_AND_PASSWORD =
      readSqlQuery("sql/select/user/select_user_by_email_and_password.sql");
  private static final String SELECT_PASSWORD_BY_ID =
      readSqlQuery("sql/select/user/select_user_password_by_id.sql");
  private static final String UPDATE_PASSWORD_BY_ID =
      readSqlQuery("sql/update/update_user_password.sql");

  private final JdbcOperations jdbcOperations;
  private final PasswordEncoder passwordEncoder;

  public UserSqlService(JdbcOperations jdbcOperations, PasswordEncoder passwordEncoder) {
    this.jdbcOperations = jdbcOperations;
    this.passwordEncoder = passwordEncoder;
  }

  private PreparedStatement preparedInsertIntoUsersQuery(
      Connection connection,
      String firstName,
      String lastName,
      String email,
      String phoneNumber,
      String password,
      String role)
      throws SQLException {
    PreparedStatement statement = connection.prepareStatement(INSERT_INTO_USERS);

    int parameterIndex = 0;

    statement.setString(++parameterIndex, firstName);
    if (lastName != null) statement.setString(++parameterIndex, lastName);
    else statement.setNull(++parameterIndex, JDBCType.VARCHAR.getVendorTypeNumber());
    statement.setString(++parameterIndex, email);
    statement.setString(++parameterIndex, phoneNumber);
    //        statement.setString(++parameterIndex, passwordEncoder.encode(password));
    if (role != null) statement.setString(++parameterIndex, role.toUpperCase());
    else statement.setString(++parameterIndex, "USER");

    return statement;
  }

  private PreparedStatement updatePasswordPreparedStatement(
      Connection connection, int userId, String password) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(UPDATE_PASSWORD_BY_ID);

    int parameterIndex = 0;

    statement.setString(++parameterIndex, password);
    statement.setInt(++parameterIndex, userId);

    return statement;
  }

  public Integer createUser(
      String firstName,
      String lastName,
      String email,
      String phoneNumber,
      String password,
      String role)
      throws DgAuthException {
    try {
      return jdbcOperations.update(
          con ->
              preparedInsertIntoUsersQuery(
                  con, firstName, lastName, email, phoneNumber, password, role));
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  public UserDto getUserByEmailAndPassword(String email, String password) throws DgAuthException {
    return jdbcOperations.queryForObject(
        SELECT_USER_BY_EMAIL_AND_PASSWORD, userRowMapper, UserDto.class, email, password);
  }

  public Integer getCountByEmail(String email) {
    return jdbcOperations.queryForObject(SELECT_COUNT_BY_EMAIL, Integer.class, email);
  }

  public void changePassword(int userId, String password) throws ResponseStatusException {
    try {
      jdbcOperations.update(con -> updatePasswordPreparedStatement(con, userId, password));
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  public UserDto getUserById(int id) {
    return jdbcOperations.queryForObject(SELECT_USER_BY_ID, userRowMapper, id);
  }

  public UserDto getUserByEmail(String email) {
    return jdbcOperations.queryForObject(SELECT_USER_BY_EMAIL, userRowMapper, email);
  }

  public String getPasswordByUserId(int userId) {
    return jdbcOperations.queryForObject(SELECT_PASSWORD_BY_ID, String.class, userId);
  }

  private final RowMapper<UserDto> userRowMapper =
      ((resultSet, rowNum) -> {
        return new UserDto(
            resultSet.getInt(UserSqlRow.ID),
            resultSet.getString(UserSqlRow.FIRST_NAME),
            resultSet.getString(UserSqlRow.LAST_NAME),
            resultSet.getString(UserSqlRow.EMAIL),
            resultSet.getString(UserSqlRow.PHONE_NUMBER),
            resultSet.getString(UserSqlRow.PASSWORD),
            resultSet.getString(UserSqlRow.ROLE));
      });
}
