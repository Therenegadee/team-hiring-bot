package rassvet.team.hire.bot.utils;


public class Consts {
    public static final String HELLO_WINDOW = """
            Добро пожаловать в Rassvet Team бот 🏋️‍♂️
                        
            Вы ищете работу в нашей фитнес-студии? Мы ищем вас!
            Здесь вы можете оставить свою заявку на вакансию тренера или администратора.
            Мы готовы рассмотреть ваши навыки и опыт.
            Для начала подачи заявки введите команду: /apply
            Мы будем рады видеть вас в нашей команде! 💪
            """;
    public static final String HELP_WINDOW = """
            /apply - Подать заявку на работу
            /info - Узнать больше о нашем боте
            /help - Список доступных команд
            /cancel - Отменить текущую операцию или заявку
            """;
    public static final String INFO_WINDOW = """
            Данный бот предназначен для упрощения процесса подачи заявок на вакансии тренера или администратора в нашей фитнес-студии.
            Мы ищем талантливых и преданных профессионалов, которые хотят присоединиться к нашей команде и помогать нашим клиентам достичь своих фитнес-целей.
                  
            Чтобы присоединиться к нашей команде, вам нужно выполнить следующие шаги:
            1. Отправьте команду /apply, чтобы начать процесс подачи заявки.
            2. Заполните необходимую информацию о себе и своем опыте.
            3. Мы рассмотрим вашу заявку и свяжемся с вами для дополнительной информации или уточнений.
                        
            Если у вас возникли вопросы или вам нужна помощь, не стесняйтесь обратиться к боту с командой /help, чтобы получить список доступных команд
            Мы ценим ваш интерес к работе в нашей фитнес-студии и с нетерпением ждем вашей заявки.
            Спасибо за ваше внимание! 💪
            """;
    public static final String INFO_WINDOW_FOR_TRAINER = """
            Данный бот предназначен для упрощения процесса обработки заявок на вакансии тренера.
            С его помощью можно:
            1) Просматривать активные заявки;
            2) Редактировать их статус (Отказано/В рассмотрении/Приглашение на собседование);
            3) В каждой анкете содержится контактная информация о человеке, поэтому вы сможете легко связаться с кандидатом;
            4) Создавать новую анкету и добавлять в нее дополнительные вопросы.
            ВАЖНО! В каждой анкете изначально содержатся вопросы:
            - об имени и фамилии кандидата;
            - о возрасте;
            - о номере телефона;
            - о предпочтительном формате связи с кандидатом (Telegram/WhatsApp);
            - об опыте работы;
            Вам нужно будет добавить оставшиеся интересующие вопросы (чтобы создать анкету введите /applications -> Создать)
            Чтобы ознакомиться со списком команд введите: /help
            """;
    public static final String HELLO_WINDOW_FOR_STAFF = """
            Добро пожаловать в Rassvet Team бот 🏋️‍♂️
                        
            Данный бот предназначен для упрощения процесса обработки заявок на вакансии
            Для ознакомления с основным функционалом бота введи /info 💪
            """;
    public static final String HELP_WINDOW_FOR_STAFF = """
            /application - Вся работа с анкетами (просмотр/создание/редактирование/удаление)
            /info - Узнать о функционале бота
            /help - Список доступных команд
            /cancel - Отменить текущую операцию и вернуться к главной панели
            """;
    public static final String INFO_WINDOW_FOR_STAFF = """
            Данный бот предназначен для упрощения процесса обработки заявок на вакансии %s.
            С его помощью можно:
            1) Просматривать активные заявки;
            2) Редактировать их статус (Отказано/В рассмотрении/Приглашение на собседование);
            3) В каждой анкете содержится контактная информация о человеке, поэтому вы сможете легко связаться с кандидатом;
            4) Создавать новую анкету и добавлять в нее дополнительные вопросы.
            ВАЖНО! В каждой анкете изначально содержатся вопросы:
            - об имени и фамилии кандидата;
            - о возрасте;
            - о номере телефона;
            - о предпочтительном формате связи с кандидатом (Telegram/WhatsApp);
            - об опыте работы;
            Вам нужно будет добавить оставшиеся интересующие вопросы (чтобы создать анкету введите /applications -> Создать)
            Чтобы ознакомиться со списком команд введите: /help
            """;
    // ERRORS
    public static final String INTERNAL_EXCEPTION = """
            У нас произошла ошибка на данном этапе выполнения!
            Простите нас за это \uD83D\uDE22\s
            Мы уже знаем об ошибке и скоро ее исправим!""";

    public static final String CANT_UNDERSTAND = "Извините, но я пока что понимаю только команды :(\n" +
            "Чтобы узнать список доступных комманд введите /help";
    public static final String INCORRECT_INPUT_FOR_KEYBOARDS = "Вы ввели недопустимое значение! Выберите один из предложенных ответов!";
    public static final String INCORRECT_NAME_INPUT = "Вы ввели некорректное имя (оно не может содержать цифры, а также любые символы, кроме дефисов).\n" +
            "Введите имя снова.";
    public static final String INCORRECT_AGE_INPUT = "Вы ввели недопустимое значение для возраста!\n" +
            "Введите ваш возраст снова в числовом формате (например, 25).";
    public static final String INCORRECT_PHONE_NUMBER_INPUT = """
            Вы ввели некорректный номер телефона!
            Коректные форматы ввода:
            1. 7xxxXXXxxXX
            2. 8xxxXXXxxXX
            3. +7xxxXXXxxXX
            4. +7(XXX)-XXX-XX-XX""";

    // MESSAGES FOR APPLYING FOR VACANCY
    public static final String INPUT_AGE = "Введите свой возраст числом (например, 25)";
    public static final String INPUT_NAME = "Введите своё имя и фамилию";
    public static final String INPUT_PHONE_NUMBER = "Введите свой номер телефона";
    public static final String INPUT_EXPERIENCE = """
            Напишите о своем опыте работы.
            Отправьте данный рассказ одним сообщением в ответ на это.
            Расскажите о своем опыте как можно подробнее (как давно ведете, какие направления вели, и т.п.)
            Если есть спортивные звания или разряды, обязательно их укажи!""";

    // MESSAGES FOR ACTIONS TOWARD VACANCY
    public static final String INPUT_VACANCY_POSITION = "Введите название должности:";
    public static final String INPUT_VACANCY_DESCRIPTION = "Введите описание вакансии:";
    public static final String INPUT_VACANCY_QUESTIONNAIRE = "Введите вопрос к вакансии:";
    public static final String VACANCY_SUCCESSFULLY_DELETED = """
            Вакансия успешно удалена!
            Если вы это сделали случайно, воспользуйтесь кнопкой <<Восстановить>>
            """;

    public static final String VACANCY_SUCCESSFULLY_RESTORED = "Вакансия успешно восстановлена!";

    // MESSAGES FOR STAFF LOGIN COMMAND
    public static final String INPUT_YOUR_SECRET_CODE = "Введите ваш код сотрудника";

    // HELP MESSAGE TEXTS FOR BUTTONS
    public static final String WHAT_TO_EDIT = "Выберите, что хотите изменить:";

    // KEYBOARD BUTTONS
    public static final String APPLICATIONS_BUTTON = "Заявления на работу";
    public static final String VACANCIES_BUTTON = "Вакансии";
    public static final String STAFF_BUTTON = "Сотрудники";
    public static final String MY_APPLICATIONS_BUTTON = "Мои отклики";

    public static final String SHOW_ACTIVE_APPLICATIONS_BUTTON = "Посмотреть активные заявки";
    public static final String SHOW_APPROVED_APPLICATIONS_BUTTON = "Посмотреть принятые заявки";
    public static final String SHOW_ALL_APPLICATIONS_BUTTON = "Посмотреть все заявки (актив/принято/отказ/архивные)";
    public static final String SHOW_REFUSED_APPLICATIONS_BUTTON = "Посмотреть заявки со статусом ОТКАЗАНО";

    public static final String SHOW_CURRENT_STAFF = "Посмотреть работников с доступом к боту";
    public static final String EDIT_STAFF_MEMBER_BUTTON = "Редактировать данные";
    public static final String DELETE_STAFF_MEMBER_BUTTON = "Удалить сотрудника";

    public static final String SHOW_OPEN_VACANCIES_BUTTON = "Посмотреть открытые вакансии";
    public static final String SHOW_VACANCY_BUTTON = "Посмотреть вакансию";
    public static final String CREATE_VACANCY_BUTTON = "Создать новую вакансию";
    public static final String APPLY_FOR_VACANCY_BUTTON = "Откликнуться на вакансию";

    public static final String EDIT_VACANCY_BUTTON = "Редактировать вакансию";
    public static final String EDIT_VACANCY_DESCRIPTION_BUTTON = "Редактировать описание вакансии";
    public static final String EDIT_VACANCY_POSITION_NAME_BUTTON = "Редактировать название должности";
    public static final String EDIT_VACANCY_QUESTIONS_BUTTON = "Редактировать вопросы к вакансии";

    public static final String RESTORE_VACANCY_BUTTON = "Восстановить вакансию";

    public static final String DELETE_VACANCY_BUTTON = "Удалить вакансию";
}
