package dinn.pub_lib.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串格式工具
 * @author Steffen.Dinn
 */
public class StringUtil {
    private StringUtil() {
    }

    /**
     * 保留小数位数
     * @param num 小数
     * @param places 位数
     * @return
     */
    public static String getFormat1(float num, byte places) {
        if (places <= 0) return String.valueOf((int)num);
        else if (places == 2){
            return (new DecimalFormat("#0.00")).format(num);
        } else {
            String strPlaces = "#0.";
            for (byte i=0; i<places; i++) strPlaces +=  "0";
            return (new DecimalFormat(strPlaces)).format(num);
        }
    }

    /**
     * 是否是手机号码
     *
     * @param paramString
     * @return
     */
    public static boolean isValidMobiNumber(String paramString) {
        Pattern p = Pattern.compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");
        Matcher m = p.matcher(paramString);
        return m.matches();
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return boolean
     */
    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
        return pattern.matcher(email).matches();
    }


}
