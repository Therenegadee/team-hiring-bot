package rassvet.team.hire.bot.utils;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Validator {
    public static boolean isNameValid(String name) {
        String regex = "^[A-Za-zА-Яа-яЁё\\s-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean isAgeValid(String ageStr) {
        try {
            int age = Integer.parseInt(ageStr);
            if (age >= 0 && age <= 100) {
                return true;
            }
        } catch (NumberFormatException e) {}
        return false;
    }

    public static boolean isPhoneNumberValid(String phoneNumber){
        String cleanedNumber = phoneNumber.replaceAll("[^0-9]", "");
        if (cleanedNumber.length() != 11) {
            return false;
        }
        return true;
    }
}
