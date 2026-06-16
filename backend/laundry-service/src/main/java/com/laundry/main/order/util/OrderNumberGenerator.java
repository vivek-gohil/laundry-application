package com.laundry.main.order.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class OrderNumberGenerator {

    private OrderNumberGenerator() {
    }

    public static String generate() {
        return "ORD-" +LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
