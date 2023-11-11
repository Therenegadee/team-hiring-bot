package rassvet.team.hire.bot.utils;

import org.springframework.stereotype.Service;

@Service
public class PhoneNumberFormatter {

    public static String formatPhoneNumber(String phoneNumber) {
        String cleanedNumber = phoneNumber.replaceAll("[^0-9]", "");
        String formattedNumber = "+7-(" +
                cleanedNumber.substring(1, 4) + ")-" +
                cleanedNumber.substring(4, 7) + "-" +
                cleanedNumber.substring(7, 9) + "-" +
                cleanedNumber.substring(9);
        return formattedNumber;
    }

}
