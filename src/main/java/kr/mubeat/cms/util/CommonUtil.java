package kr.mubeat.cms.util;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by doohwan.yoo on 2017. 4. 18..
 */
public class CommonUtil {

    public static String encryptPassword(PasswordEncoder passwordEncoder, String originPassword) {
        return passwordEncoder.encode(originPassword);
    }

    public static boolean isPasswordMatched(PasswordEncoder passwordEncoder, String rawPwd, String encodedPwd) {
        return passwordEncoder.matches(rawPwd, encodedPwd);
    }

    public static Date addOneDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 24);
        return calendar.getTime();
    }
}
