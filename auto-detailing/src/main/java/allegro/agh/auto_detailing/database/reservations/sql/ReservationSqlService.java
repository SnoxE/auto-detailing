package allegro.agh.auto_detailing.database.reservations.sql;

import static allegro.agh.auto_detailing.common.resource.ResourceManager.readSqlQuery;

import allegro.agh.auto_detailing.common.exceptions.DgAuthException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReservationSqlService {

  private static final Logger log = LoggerFactory.getLogger(ReservationSqlService.class);
  private static final String SELECT_RESERVATIONS_BY_USER_ID =
      readSqlQuery("sql/select/reservations/select_reservations_by_user_id.sql");
  private static final String SELECT_RESERVATIONS =
      readSqlQuery("sql/select/reservations/select_reservations.sql");
  private static final String SELECT_CALENDAR_RESERVATIONS =
      readSqlQuery("sql/select/reservations/select_calendar_reservations.sql");
  private static final String DELETE_RESERVATION_BY_ID_AND_USER_ID =
      readSqlQuery("sql/delete/delete_reservation_by_id_and_user_id.sql");

  JdbcOperations jdbcOperations;

  public ReservationSqlService(JdbcOperations jdbcOperations) {
    this.jdbcOperations = jdbcOperations;
  }

  public List<ReservationSqlRow> getReservations(String userId) {
    return jdbcOperations.query(
        con -> preparedSelectReservationByUserIdQuery(con, userId),
        (rs, rowNum) -> {
          try {
            return extractReservationRow(rs);
          } catch (JsonProcessingException e) {
            log.error(
                "Unable to retrieve cars due to unexpected exception (userId={}, message={})",
                userId,
                e.getMessage(),
                e);
            throw new RuntimeException(e);
          }
        });
  }

  public List<ReservationSqlRow> getCalendarReservations(LocalDate after, LocalDate before) {
    return jdbcOperations.query(
        con -> preparedCalendarReservationsStatement(con, after, before),
        (rs, rowNum) -> {
          try {
            return extractReservationRow(rs);
          } catch (JsonProcessingException e) {
            log.error(
                "Unable to retrieve cars due to unexpected exception (message={})",
                e.getMessage(),
                e);
            throw new RuntimeException(e);
          }
        });
  }

  public List<ReservationStartEndTimesSqlRow> getReservationStartEndTimes() {
    return jdbcOperations.query(
        con -> con.prepareStatement(SELECT_RESERVATIONS),
        (rs, rowNum) -> {
          try {
            return extractReservationStartEndTimesRow(rs);
          } catch (JsonProcessingException e) {
            log.error(
                "Unable to retrieve cars due to unexpected exception (message={})",
                e.getMessage(),
                e);
            throw new RuntimeException(e);
          }
        });
  }

  public void deleteReservation(int userId, int reservationId) throws DgAuthException {
    try {
      jdbcOperations.update(
          con -> preparedDeleteReservationByIaAndUserIdStatement(con, userId, reservationId));
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  private PreparedStatement preparedSelectReservationByUserIdQuery(
      Connection connection, String userId) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(SELECT_RESERVATIONS_BY_USER_ID);

    int parameterIndex = 0;
    statement.setInt(++parameterIndex, Integer.parseInt(userId));

    return statement;
  }

  private PreparedStatement preparedCalendarReservationsStatement(
      Connection connection, LocalDate after, LocalDate before) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(SELECT_CALENDAR_RESERVATIONS);

    int parameterIndex = 0;
    statement.setObject(++parameterIndex, after);
    statement.setObject(++parameterIndex, before);

    return statement;
  }

  private PreparedStatement preparedDeleteReservationByIaAndUserIdStatement(
      Connection connection, int userId, int reservationId) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(DELETE_RESERVATION_BY_ID_AND_USER_ID);

    int parameterIndex = 0;
    statement.setInt(++parameterIndex, userId);
    statement.setInt(++parameterIndex, reservationId);

    return statement;
  }

  private ReservationSqlRow extractReservationRow(ResultSet resultSet)
      throws SQLException, JsonProcessingException {
    return new ReservationSqlRow(
        resultSet.getInt(ReservationSqlRow.ID),
        resultSet.getString(ReservationSqlRow.SERVICES_NAME),
        resultSet.getInt(ReservationSqlRow.SERVICES_PRICE),
        resultSet.getString(ReservationSqlRow.CARS_MAKE),
        resultSet.getString(ReservationSqlRow.CARS_MODEL),
        resultSet.getString(ReservationSqlRow.CARS_YEAR),
        resultSet.getString(ReservationSqlRow.CARS_COLOUR),
        resultSet.getTimestamp(ReservationSqlRow.RES_START_AT),
        resultSet.getTimestamp(ReservationSqlRow.RES_END_AT));
  }

  private ReservationStartEndTimesSqlRow extractReservationStartEndTimesRow(ResultSet resultSet)
      throws SQLException, JsonProcessingException {
    return new ReservationStartEndTimesSqlRow(
        resultSet.getTimestamp(ReservationStartEndTimesSqlRow.START_AT).toLocalDateTime(),
        resultSet.getTimestamp(ReservationStartEndTimesSqlRow.END_AT).toLocalDateTime());
  }
}
