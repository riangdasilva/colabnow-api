package riangdasilva.colabnowapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginDto(
        @NotNull @NotBlank String username,
        @NotNull @NotBlank String password) {
}
