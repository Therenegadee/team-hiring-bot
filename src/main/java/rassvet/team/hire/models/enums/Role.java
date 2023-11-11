package rassvet.team.hire.models.enums;

import lombok.Getter;

@Getter
public enum Role {
    CREATOR("Основатель"),
    SENIOR_TRAINER("Старший тренер"),
    SENIOR_ADMIN("Старший администратор");

    private String value;
    Role(String value) {
        this.value = value;
    }
}
