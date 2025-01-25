package allegro.agh.login_service.database.user.sql.model;

public record UserLoginSqlRow(
    int id, String firstName, String lastName, String email, String password, String role) {}
