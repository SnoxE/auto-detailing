package allegro.agh.auto_detailing.database.car.sql;

import static allegro.agh.auto_detailing.common.resource.ResourceManager.readSqlQuery;

import allegro.agh.auto_detailing.common.exceptions.DgAuthException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CarSqlService {

  private static final Logger log = LoggerFactory.getLogger(CarSqlService.class);

  private static final String INSERT_INTO_CARS = readSqlQuery("sql/insert/insert_into_cars.sql");
  private static final String SELECT_CARS_BY_USER_ID =
      readSqlQuery("sql/select/cars/select_cars_by_user_id.sql");
  private static final String DELETE_FROM_CARS_BY_USER_ID_AND_CAR_ID =
      readSqlQuery("sql/delete/delete_car_by_user_id_and_car_id.sql");

  private final JdbcOperations jdbcOperations;

  public CarSqlService(JdbcOperations jdbcOperations) {
    this.jdbcOperations = jdbcOperations;
  }

  private PreparedStatement preparedInsertIntoCarsQuery(
      Connection connection,
      int userId,
      String make,
      String model,
      String productionYear,
      String size,
      String colour)
      throws SQLException {
    PreparedStatement statement = connection.prepareStatement(INSERT_INTO_CARS);

    int parameterIndex = 0;

    statement.setInt(++parameterIndex, userId);
    statement.setString(++parameterIndex, make);
    statement.setString(++parameterIndex, model);
    statement.setString(++parameterIndex, productionYear);
    statement.setString(++parameterIndex, size);
    statement.setString(++parameterIndex, colour);

    return statement;
  }

  private PreparedStatement preparedGetCarsStatement(Connection connection, String userId)
      throws SQLException {
    PreparedStatement statement = connection.prepareStatement(SELECT_CARS_BY_USER_ID);

    int parameterIndex = 0;
    statement.setInt(++parameterIndex, Integer.parseInt(userId));

    return statement;
  }

  private PreparedStatement preparedDeleteCarStatement(Connection connection, int userId, int carId)
      throws SQLException {
    PreparedStatement statement =
        connection.prepareStatement(DELETE_FROM_CARS_BY_USER_ID_AND_CAR_ID);

    int parameterIndex = 0;
    statement.setInt(++parameterIndex, userId);
    statement.setInt(++parameterIndex, carId);

    return statement;
  }

  public Integer createCar(
      int userId, String make, String model, String productionYear, String size, String colour)
      throws DgAuthException {
    try {
      return jdbcOperations.update(
          con ->
              preparedInsertIntoCarsQuery(con, userId, make, model, productionYear, size, colour));
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  public void deleteCar(int userId, int carId) throws DgAuthException {
    try {
      jdbcOperations.update(con -> preparedDeleteCarStatement(con, userId, carId));
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  public List<CarSqlRow> getCars(String userId) {
    return jdbcOperations.query(
        con -> preparedGetCarsStatement(con, userId),
        (rs, rowNum) -> {
          try {
            return extractCarRow(rs);
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

  private CarSqlRow extractCarRow(ResultSet resultSet)
      throws SQLException, JsonProcessingException {
    return new CarSqlRow(
        resultSet.getInt(CarSqlRow.ID),
        resultSet.getString(CarSqlRow.MAKE),
        resultSet.getString(CarSqlRow.MODEL),
        resultSet.getString(CarSqlRow.PRODUCTION_YEAR),
        resultSet.getString(CarSqlRow.SIZE),
        resultSet.getString(CarSqlRow.COLOUR));
  }
}
