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
}
