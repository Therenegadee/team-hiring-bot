package rassvet.team.hire.models.enums;

import lombok.Getter;

@Getter
public enum ApplicationStatus {
    IN_CONSIDERATION("IN_CONSIDERATION"),
    REFUSED("REFUSED"),
    ARCHIVE("ARCHIVE");

    private String value;

    ApplicationStatus(String value) {
        this.value = value;
    }

    public static ApplicationStatus fromValue(String value) {
        for (ApplicationStatus applicationStatus : ApplicationStatus.values()) {
            if (applicationStatus.value.equals(value)) {
                return applicationStatus;
            }
        }
        throw new IllegalArgumentException("Unsupported value: " + value);
    }
}
