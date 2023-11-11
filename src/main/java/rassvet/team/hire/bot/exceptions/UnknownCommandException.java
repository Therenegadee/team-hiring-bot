package rassvet.team.hire.bot.exceptions;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public class UnknownCommandException extends RuntimeException {
    private final Update update;
    public UnknownCommandException(Update update) {
        super();
        this.update = update;
    }
}
