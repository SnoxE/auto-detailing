package allegro.agh.auto_detailing.database.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Time;

public record ServiceDto(
    @JsonProperty("id") int id,
    @JsonProperty("name") String name,
    @JsonProperty("price") int price,
    @JsonProperty("length") Time length,
    @JsonProperty("car_size") String carSize) {}
