package com.laundry.main.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public final class ReferenceNumberGenerator {

  private static final AtomicInteger COUNTER = new AtomicInteger(1);

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

  private ReferenceNumberGenerator() {}

  public static String generate(String prefix) {

    return prefix
        + "-"
        + LocalDateTime.now().format(FORMATTER)
        + "-"
        + String.format("%03d", COUNTER.getAndIncrement());
  }
}
