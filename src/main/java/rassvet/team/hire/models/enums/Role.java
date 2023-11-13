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

    public static Role fromValue(String value) {
        for (Role role : Role.values()) {
            if (role.value.equals(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unsupported value: " + value);
    }
}
