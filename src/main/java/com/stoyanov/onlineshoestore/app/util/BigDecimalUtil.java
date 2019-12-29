package com.stoyanov.onlineshoestore.app.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

    public static boolean isNumberHigher(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) > 0;
    }

    public static boolean isNumberHigherOrEqual(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) >= 0;
    }

    public static boolean isNumberLower(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) < 0;
    }

    public static boolean isNumberLowerOrEqual(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) <= 0;
    }
}
