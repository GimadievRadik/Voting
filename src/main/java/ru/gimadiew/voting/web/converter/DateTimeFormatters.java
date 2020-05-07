package ru.gimadiew.voting.web.converter;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static ru.gimadiew.voting.util.DateTimeUtil.parseLocalDate;

public class DateTimeFormatters {
    public static class LocalDateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(String text, Locale locale) {
            return parseLocalDate(text);
        }

        @Override
        public String print(LocalDate ld, Locale locale) {
            return ld.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }
}
