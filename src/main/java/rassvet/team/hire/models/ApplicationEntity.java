package rassvet.team.hire.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rassvet.team.hire.models.enums.ContactMethod;
import rassvet.team.hire.models.enums.Position;

@Document("applications")
public class ApplicationEntity {
    @Id
    private String id;
    private String telegramId;
    private Position position;
    private String name;
    private int age;
    private String phoneNumber;
    private ContactMethod contactMethod;
    private String experience;
    private String desiredHoursPerWeek;
}
