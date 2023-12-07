package rassvet.team.hire.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import rassvet.team.hire.bot.RassvetBot;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.exceptions.BadTextRequestException;
import rassvet.team.hire.bot.keyboards.StaffKeyboardMarkUp;
import rassvet.team.hire.bot.keyboards.UserBoardKeyboardMarkUp;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.dao.interfaces.RoleDao;
import rassvet.team.hire.dao.interfaces.UserDao;
import rassvet.team.hire.models.Role;
import rassvet.team.hire.models.User;

import static rassvet.team.hire.bot.utils.Consts.*;

@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {
    private final RassvetBot rassvetBot;

    @Override
    public void sendResponse(SendMessage responseMsg) {
        try {
            rassvetBot.execute(responseMsg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
