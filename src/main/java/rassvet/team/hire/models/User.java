package rassvet.team.hire.models;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Component;
import rassvet.team.hire.models.enums.Role;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@Table("users")
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
