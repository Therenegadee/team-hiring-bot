package rassvet.team.hire.bot.exceptions;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public class UserNotFoundException extends RuntimeException{
    private final Update update;
    public UserNotFoundException(Update update) {
        super();
        this.update = update;
    }
}
