package alexey.grizly.com.users.utils;

import java.security.SecureRandom;
import java.util.*;


public class ApprovedTokenUtils {
    private ApprovedTokenUtils(){}
    public static String generateApprovedToken(Integer targetStringLength){
        int leftLimit = 48; /* numeral '0'*/
        int rightLimit = 122; /* litter 'z' */
        Random random = new SecureRandom();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
