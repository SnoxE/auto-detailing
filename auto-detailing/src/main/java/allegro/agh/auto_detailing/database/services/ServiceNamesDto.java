package allegro.agh.auto_detailing.database.services;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ServiceNamesDto(@JsonProperty("name") String name) {
    public String getName() {
        return this.name;
    }
}
