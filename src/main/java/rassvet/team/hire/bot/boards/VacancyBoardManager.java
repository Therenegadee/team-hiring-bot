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
import rassvet.team.hire.bot.handler.interfaces.CallbackQueryHandler;
import rassvet.team.hire.dao.interfaces.UserDao;
import rassvet.team.hire.dao.interfaces.VacancyDao;
import rassvet.team.hire.models.Role;
import rassvet.team.hire.models.User;
import rassvet.team.hire.models.Vacancy;

import java.util.Set;


@Component
@NoArgsConstructor
public class VacancyBoardManager implements BoardManager, CallbackQueryHandler {
    @Autowired
    private BotCache botCache;
    @Autowired
    private BotService botService;
    @Autowired
    private VacancyDao vacancyDao;
    @Autowired
    private UserDao userDao;

    @Override
    public void handleCallbackQuery(Update update, String callbackData) {
        String[] callbackDataArr = callbackData.split(" ");
        String secondPrefixOfCallbackData = callbackDataArr[1];
        String thirdPrefixOfCallbackData = callbackDataArr[2];
        switch (secondPrefixOfCallbackData) {
            case "BOARD" -> showBoardPanel(update);
            case "SHOW" -> {
                switch (thirdPrefixOfCallbackData) {
                    case "ALL" -> showVacancies(update);
                    case "ID" -> showVacancy(update, Long.parseLong(callbackDataArr[3]));
                }
            }
            case "APPLY" -> ;
            case "EDIT" -> ;
            case "DELETE" -> ;
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
        Set<Vacancy> vacancies = vacancyDao.findAll();
        sendVacancyBriefInfo(update, vacancies);
    }

    private void sendVacancyBriefInfo(Update update, Set<Vacancy> vacancies) {
        String chatId = update.getMessage().getChatId().toString();
        String vacancyInfo = "Вакансия: %s";
        for (Vacancy vacancy : vacancies) {
            botService.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(String.format(
                            vacancyInfo,
                            vacancy.getPositionName()

                    ))
                    .replyMarkup(VacanciesKeyboardMarkUp.showVacancyKeyboard(vacancy.getId()))
                    .build()
            );
        }
    }

    private void showVacancy(Update update, Long vacancyId) {
        //todo: добавить обработку исключения
        Vacancy vacancy = vacancyDao.findById(vacancyId)
                .orElseThrow(RuntimeException::new);
        sendVacancyWholeInfo(update, vacancy);
    }

    private void sendVacancyWholeInfo(Update update, Vacancy vacancy) {
        String chatId = update.getMessage().getChatId().toString();
        Long telegramId = update.getMessage().getFrom().getId();
        User user = botCache.getUserCache(telegramId);
        Role role = user.getRole();

        String vacancyInfo = """
                Должность: %s
                Описание вакансии:
                %s
                """;
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(String.format(
                        vacancyInfo,
                        vacancy.getPositionName(),
                        vacancy.getDescription()
                ))
                .replyMarkup(VacanciesKeyboardMarkUp.actionsTowardVacancyKeyboard(vacancy.getId(), role))
                .build());
    }

}
