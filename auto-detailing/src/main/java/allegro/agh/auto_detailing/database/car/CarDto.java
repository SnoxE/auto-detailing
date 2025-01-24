package allegro.agh.auto_detailing.database.car;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CarDto(
        @JsonProperty("id") String id,
        @JsonProperty("make") String make,
        @JsonProperty("model") String model,
        @JsonProperty("production_year") String productionYear,
        @JsonProperty("size") String size,
        @JsonProperty("colour") String colour) {}
