package rassvet.team.hire.bot.service.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface AuthService {

    void inputSecretCode(Update update);

    void verifySecretCode(Update update);
}
