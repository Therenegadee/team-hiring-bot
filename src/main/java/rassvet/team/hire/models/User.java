package rassvet.team.hire.models;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Component;

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
