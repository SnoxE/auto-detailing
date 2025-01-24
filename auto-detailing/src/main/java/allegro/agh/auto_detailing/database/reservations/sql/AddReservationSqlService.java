package allegro.agh.auto_detailing.database.reservations.sql;

import allegro.agh.auto_detailing.common.exceptions.DgAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import static allegro.agh.auto_detailing.common.resource.ResourceManager.readSqlQuery;

@Service
public class AddReservationSqlService {

    public static final String INSERT_INTO_RESERVATIONS =
            readSqlQuery("sql/insert/insert_into_reservations.sql");

    JdbcOperations jdbcOperations;

    public AddReservationSqlService(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    private PreparedStatement preparedInsertIntoReservationsQuery(
            Connection connection,
            int userId,
            int serviceId,
            int carId,
            Timestamp startAt,
            Timestamp endAt) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT_INTO_RESERVATIONS);

        int parameterIndex = 0;

        statement.setInt(++parameterIndex, userId);
        statement.setInt(++parameterIndex, serviceId);
        statement.setInt(++parameterIndex, carId);
        statement.setTimestamp(++parameterIndex, startAt);
        statement.setTimestamp(++parameterIndex, endAt);

        return statement;
    }

    public Integer createReservation(
            int userId,
            int serviceId,
            int carId,
            String startAt,
            String endAt) throws DgAuthException {
        try {
            return jdbcOperations.update(con -> preparedInsertIntoReservationsQuery(
                    con,
                    userId,
                    serviceId,
                    carId,
                    Timestamp.valueOf(startAt),
                    Timestamp.valueOf(endAt)));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
