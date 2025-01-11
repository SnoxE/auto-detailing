package allegro.agh.auto_detailing.database.user.sql.model;

public record UserSqlRow(
        int id,
        String firstName,
        String lastName,
        String email,
        String password,
        String phoneNumber,
        String role) {
    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String PASSWORD = "password";
    public static final String ROLE = "role";
}
