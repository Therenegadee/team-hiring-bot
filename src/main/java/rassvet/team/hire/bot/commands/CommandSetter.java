package rassvet.team.hire.bot.commands;

import org.springframework.stereotype.Service;
import rassvet.team.hire.bot.cache.BotState;

@Service
public class CommandSetter {

    public Command setCommand(BotState botState){
        switch (botState) {
            case INPUT_AGE_STATE, INPUT_DESIRED_HOURS_PER_WEEK_STATE, INPUT_EXPERIENCE_STATE, INPUT_NAME_STATE,
                    INPUT_PHONE_NUMBER_STATE, CHOOSE_POSITION_STATE, CHOOSE_CONTACT_METHOD_STATE -> {
                return new ApplyCommand();
            }
            case BASIC_STATE -> {
                return new InfoCommand();
            }
            default -> {
                return null;
            }
        }
    }
}
