package rassvet.team.hire.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import rassvet.team.hire.models.enums.ContactMethod;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Component
public class Application {
    @Id
    private String id;
    private String telegramId;
    private String fullName;
    private int age;
    private String phoneNumber;
    private ContactMethod contactMethod;
    private String experience;
    private Vacancy vacancy;
    private Map<String, String> answers;
}
