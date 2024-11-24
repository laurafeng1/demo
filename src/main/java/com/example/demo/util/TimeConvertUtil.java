package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConvertUtil {
    private final static Logger logger = LoggerFactory.getLogger(TimeConvertUtil.class);

    public static Date parse(String dateStr) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error("date str parse error", e);
        }
        return date;
    }

    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        return sdf.format(date);
    }
}
