package com.credit.facility.loan.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;

import com.credit.facility.loan.util.enums.ErrorMessage;
import com.credit.facility.loan.util.exception.BusinessException;

public class StringUtil {

    public static Long getRandomNumber(int charCount){

        String randomNumeric;
        do {
            randomNumeric = getRandomNumberAsString(charCount);
        } while (randomNumeric.startsWith("0"));

        Long randomLong = null;
        if (StringUtils.hasText(randomNumeric)){
            randomLong = Long.parseLong(randomNumeric);
        }

        return randomLong;
    }

    public static String getRandomNumberAsString(int charCount){

        validateCharCount(charCount);

        String randomNumeric = RandomStringUtils.randomNumeric(charCount);

        return randomNumeric;
    }

    public static String getRandomString(int charCount){

        validateCharCount(charCount);

        String randomAlphabetic = RandomStringUtils.randomAlphabetic(charCount);

        return randomAlphabetic;
    }

    private static void validateCharCount(int charCount) {
        if (charCount < 0){
            throw new BusinessException(ErrorMessage.VALUE_CANNOT_BE_NEGATIVE);
        }
    }
}
