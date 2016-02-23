package com.pilotfish.eip.modules.pdfviewer.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Created by craigmiller on 2/19/16.
 */
public class LogFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        String formattedDate = "[" + formatDate(record.getMillis()) + "]";
        String className = record.getSourceClassName();
        String methodName = record.getSourceMethodName();
        String level = record.getLevel().getLocalizedName();
        String message = record.getMessage();

        Throwable t = record.getThrown();

        StringBuilder output = new StringBuilder();
        output.append(formattedDate + " ");
        output.append(level + ": ");

        output.append(message + System.lineSeparator());
        output.append("               " + className + " ");
        output.append(methodName);

        if(t != null){
            output.append(System.lineSeparator() + "    " + t);
            StackTraceElement[] steArr = t.getStackTrace();
            for(StackTraceElement ste : steArr){
                output.append(System.lineSeparator() + "      " + ste.toString());
            }
        }
        output.append(System.lineSeparator());

        return output.toString();
    }

    /**
     * Formats the raw millisecond amount of the date into a style more
     * fitting for the log entry.
     *
     * @param millisecs the date in milliseconds since 1970.
     * @return the formatted date.
     */
    private String formatDate(long millisecs){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
        Date resultDate = new Date(millisecs);
        return dateFormat.format(resultDate);
    }
}
