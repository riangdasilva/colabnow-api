package riangdasilva.colabnowapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import riangdasilva.colabnowapi.enums.UserRole;

public record RegisterDto(
        @NotNull @NotBlank String username,
        @NotNull @NotBlank String password,
        @NotNull UserRole role
) {
}
