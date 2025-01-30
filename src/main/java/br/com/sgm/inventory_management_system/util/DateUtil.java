package br.com.sgm.inventory_management_system.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {

    public static String LocalDateToString(LocalDate date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        return format.format(date);
    }

    public static LocalDate StringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }
    }
