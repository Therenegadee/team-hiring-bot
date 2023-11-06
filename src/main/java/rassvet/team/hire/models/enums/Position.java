package rassvet.team.hire.models.enums;

import lombok.Getter;

@Getter
public enum Position {
    TRAINER("Тренер"),
    ADMINISTRATOR("Администратор");

    private final String value;

    Position(String value) {
        this.value = value;
    }
}
