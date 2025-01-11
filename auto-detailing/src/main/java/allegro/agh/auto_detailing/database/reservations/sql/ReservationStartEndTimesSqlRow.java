package allegro.agh.auto_detailing.database.reservations.sql;

import java.time.LocalDateTime;

public record ReservationStartEndTimesSqlRow(
        LocalDateTime startAt,
        LocalDateTime endAt) {
    public static final String START_AT = "start_at";
    public static final String END_AT = "end_at";
}
