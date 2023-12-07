package rassvet.team.hire.bot.boards;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.boards.interfaces.BoardManager;
import rassvet.team.hire.bot.keyboards.VacanciesKeyboardMarkUp;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.bot.handler.callbackQuery.CallbackQueryHandler;
import rassvet.team.hire.bot.service.interfaces.VacancyService;
import rassvet.team.hire.models.Role;
import rassvet.team.hire.models.User;


@Component
@NoArgsConstructor
public class VacancyBoardManager implements BoardManager, CallbackQueryHandler {
    @Autowired
    private BotCache botCache;
    @Autowired
    private BotService botService;
    @Autowired
    private VacancyService vacancyService;

    @Override
    public void handleCallbackQuery(Update update, String callbackData) {
        String[] callbackDataArr = callbackData.split(" ");
        String secondPrefixOfCallbackData = callbackDataArr[1];
        String thirdPrefixOfCallbackData = callbackDataArr[2];
        String fourthPrefixOfCallbackData = callbackDataArr[3];
        switch (secondPrefixOfCallbackData) {
            case "BOARD" -> showBoardPanel(update);
            case "CREATE" -> createVacancy(update);
            case "SHOW" -> {
                switch (thirdPrefixOfCallbackData) {
                    case "ALL" -> showVacancies(update);
                    case "ID" -> showVacancy(update, Long.parseLong(thirdPrefixOfCallbackData));
                }
            }
            case "APPLY" -> applyForVacancy(update, Long.parseLong(thirdPrefixOfCallbackData));
            case "EDIT" -> {
                switch (thirdPrefixOfCallbackData) {
                    case "ID" -> editVacancy(update, Long.parseLong(fourthPrefixOfCallbackData));
                    case "POSITION" -> editPosition(update, Long.parseLong(fourthPrefixOfCallbackData));
                    case "DESCRIPTION" -> editDescription(update, Long.parseLong(fourthPrefixOfCallbackData));
                    case "QUESTIONNAIRE" -> editQuestionnaire(update, Long.parseLong(fourthPrefixOfCallbackData));
                }
            }
            case "DELETE" -> deleteVacancy(update, Long.parseLong(thirdPrefixOfCallbackData));
            case "RESTORE" -> restoreVacancy(update);
        }
    }

    @Override
    public void showBoardPanel(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        User user = botCache.getUserCache(telegramId);
        Role role = user.getRole();
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(VacanciesKeyboardMarkUp.vacancyBoardKeyboard(role))
                .build());
    }


    private void showVacancies(Update update) {
        vacancyService.showVacancies(update);
    }

    private void showVacancy(Update update, Long vacancyId) {
        vacancyService.showVacancy(update, vacancyId);
    }

    private void applyForVacancy(Update update, Long vacancyId) {
        vacancyService.applyForVacancy(update, vacancyId);
    }

    private void createVacancy(Update update) {
        vacancyService.createVacancy(update);
    }

    private void editVacancy(Update update, Long vacancyId) {
        vacancyService.editVacancy(update, vacancyId);
    }

    private void editDescription(Update update, Long vacancyId) {
        vacancyService.editDescription(update, vacancyId);
    }

    private void editPosition(Update update, Long vacancyId) {
        vacancyService.editPosition(update, vacancyId);
    }

    private void editQuestionnaire(Update update, Long vacancyId) {
        vacancyService.editQuestionnaire(update, vacancyId);
    }

    private void deleteVacancy(Update update, Long vacancyId) {
        vacancyService.deleteVacancy(update, vacancyId);
    }

    private void restoreVacancy(Update update) {
        vacancyService.restoreVacancy(update);
    }

}
