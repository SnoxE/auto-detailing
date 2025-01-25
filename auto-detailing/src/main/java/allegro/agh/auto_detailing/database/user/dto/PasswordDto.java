package allegro.agh.auto_detailing.database.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PasswordDto(
    @JsonProperty("old_password") String oldPassword,
    @JsonProperty("new_password") String newPassword) {}
