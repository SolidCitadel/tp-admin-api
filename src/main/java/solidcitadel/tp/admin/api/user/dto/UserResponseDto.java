package solidcitadel.tp.admin.api.user.dto;

import lombok.Getter;
import solidcitadel.tp.admin.api.user.UserRoleEnum;

@Getter
public class UserResponseDto {
    private final Long id;
    private final String username;
    private final UserRoleEnum role;

    public UserResponseDto(Long id, String username, UserRoleEnum role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
}
