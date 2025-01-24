package allegro.agh.auto_detailing.database.reservations.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public record ReservationDto(
        @JsonProperty("res_id") int id,
        @JsonProperty("services_name") String servicesName,
        @JsonProperty("services_price") int servicesPrice,
        @JsonProperty("cars_make") String carsMake,
        @JsonProperty("cars_model") String carsModel,
        @JsonProperty("cars_year") String carsYear,
        @JsonProperty("cars_colour") String carsColour,
        @JsonProperty("res_start_at") Timestamp resStartAt,
        @JsonProperty("res_end_at") Timestamp resEndAt) {}

