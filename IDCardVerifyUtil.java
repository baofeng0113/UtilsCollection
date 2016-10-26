import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Strings;

public final class IDCardVerifyUtil {

    private static final List<String> DISTRICTS = Arrays.asList(
            "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36",
            "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62",
            "63", "64", "65", "71", "81", "82", "91");

    private static final int CARD_NUMBER_LENGTH = 18;

    private static final int FACTORS[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    private static boolean isDistrictPartAvail(String number) {
        return DISTRICTS.contains(number.substring(0, 2));
    }

    private static boolean isBirthdayPartAvail(String number) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        simpleDateFormat.setLenient(false);
        Date date = null;
        try {
            date = simpleDateFormat.parse(number.substring(6, 14));
        } catch (ParseException e) {
            return false;
        }
        return date.before(new Date());
    }

    private static int[] convertCharArrayToIntArray(char[] chars) {
        int[] nums = new int[chars.length];
        try {
            for (int i = 0; i < chars.length; i++) {
                nums[i] = Integer.parseInt(String.valueOf(chars[i]));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return nums;
    }

    private static int sumEachFigureFactor(int[] numbers) {
        int sum = 0;
        if (FACTORS.length == numbers.length) {
            for (int i = 0; i < numbers.length; i++) {
                for (int j = 0; j < FACTORS.length; j++) {
                    if (i == j) {
                        sum = sum + numbers[i] * FACTORS[j];
                    }
                }
            }
        }
        return sum;
    }

    private static String getLastFigureValue(int factor) {
        String value = "";
        switch (factor % 11) {
            case 10:
                value = "2";
                break;
            case 9:
                value = "3";
                break;
            case 8:
                value = "4";
                break;
            case 7:
                value = "5";
                break;
            case 6:
                value = "6";
                break;
            case 5:
                value = "7";
                break;
            case 4:
                value = "8";
                break;
            case 3:
                value = "9";
                break;
            case 2:
                value = "x";
                break;
            case 1:
                value = "0";
                break;
            case 0:
                value = "1";
                break;
        }
        return value;
    }

    public static boolean isCardNumberAvail(final String number) {
        if (Strings.isNullOrEmpty(number) || number.length() != CARD_NUMBER_LENGTH) {
            return false;
        }

        Matcher matcher = Pattern.compile("[0-9]{17}").matcher(number.substring(0, 17));
        if (!matcher.find()) {
            return false;
        }

        if (!isDistrictPartAvail(number) || !isBirthdayPartAvail(number)) {
            return false;
        } else {
            String code17 = number.substring( 0, 17);
            String code18 = number.substring(17, 18);
            String value = getLastFigureValue(sumEachFigureFactor(
                     convertCharArrayToIntArray(code17.toCharArray())));
            return value.length() > 0 && value.equalsIgnoreCase(code18);
        }
    }
}
