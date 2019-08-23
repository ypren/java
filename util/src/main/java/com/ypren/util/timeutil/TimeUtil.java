package com.ypren.util.timeutil;

import java.time.LocalDate;
import java.time.LocalTime;

public final class TimeUtil {

    public static String getLocalTime() {
        return LocalTime.now().toString();
    }

    public static String getLocalDate() {
        return LocalDate.now().toString();
    }
}
