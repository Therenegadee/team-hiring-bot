package rassvet.team.hire.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import rassvet.team.hire.models.enums.Role;

@Getter
@Setter
@NoArgsConstructor
@Component
public class User {
    private Long id;
    private Role role;
    private String telegramId;
    private String username;
    private String phoneNumber;
    private String fullName;
    private String secretKey;
}
