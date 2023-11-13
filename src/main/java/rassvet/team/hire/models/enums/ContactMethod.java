package rassvet.team.hire.models.enums;

import lombok.Getter;

@Getter
public enum ContactMethod {
    TELEGRAM("Telegram"),
    WHATSAPP("WhatsApp");

    private final String value;

    ContactMethod(String value) {
        this.value = value;
    }

    public static ContactMethod fromValue(String value) {
        for (ContactMethod contactMethod : ContactMethod.values()) {
            if (contactMethod.value.equals(value)) {
                return contactMethod;
            }
        }
        throw new IllegalArgumentException("Unsupported value: " + value);
    }
}
