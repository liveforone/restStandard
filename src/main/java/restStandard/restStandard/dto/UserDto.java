package restStandard.restStandard.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restStandard.restStandard.domain.Role;
import restStandard.restStandard.domain.Users;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String password;
    private Role auth;

    //==dto -> entity==//
    public Users toEntity() {
        return Users.builder()
                .id(id)
                .email(email)
                .password(password)
                .auth(auth)
                .build();
    }
}
