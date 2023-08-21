package riangdasilva.colabnowapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectDto(@NotNull @NotBlank String title, String description) {
}
