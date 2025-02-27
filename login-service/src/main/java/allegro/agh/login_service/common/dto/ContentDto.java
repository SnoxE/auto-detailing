package allegro.agh.login_service.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ContentDto<T>(@JsonProperty("content") @Valid List<@NotNull T> content) {}
