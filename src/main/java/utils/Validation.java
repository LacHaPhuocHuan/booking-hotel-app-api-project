package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static Boolean validaEmail(String email){
        Pattern pattern = Pattern.compile(ConstantUtils.EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Boolean validaPhone(String phone){
        Pattern pattern=Pattern.compile(ConstantUtils.PHONE_REGEX);
        return pattern.matcher(phone).matches();
    }
}
