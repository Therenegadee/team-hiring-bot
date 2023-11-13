package rassvet.team.hire.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Component;
import rassvet.team.hire.models.enums.ApplicationStatus;
import rassvet.team.hire.models.enums.ContactMethod;

import java.util.Map;

@Getter
@Setter
@Builder
@Table("application")
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Application {
    @Id
    private Long id;
    private ApplicationStatus applicationStatus;
    private String telegramId;
    private String fullName;
    private int age;
    private String phoneNumber;
    private ContactMethod contactMethod;
    private String experience;
    private Vacancy vacancy;
    private Map<String, String> answers;
}
