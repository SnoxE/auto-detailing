package allegro.agh.auto_detailing.database.reservations.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddReservationDto(
        @JsonProperty("id") int id,
        @JsonProperty("service_id") int serviceId,
        @JsonProperty("car_id") int carId,
        @JsonProperty("start_at_date") String startAtDate,
        @JsonProperty("start_at_time") String startAtTime,
        @JsonProperty("length") LengthDto length) {
    public record LengthDto(
            @JsonProperty("hours") int hours,
            @JsonProperty("minutes") int minutes
    ) {}
}