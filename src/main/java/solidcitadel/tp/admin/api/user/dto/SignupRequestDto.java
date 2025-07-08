package solidcitadel.tp.admin.api.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank
    @Size(min = 4, max = 50)
    @Pattern(regexp = "^[a-z0-9]+$")
    private String username;

    @NotBlank
    @Size(min = 8, max = 15)
    private String password;

    private String adminToken = "";
}
