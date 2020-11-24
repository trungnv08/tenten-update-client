package com.trung.util;

import java.util.logging.ConsoleHandler;

/**
 * @author trung
 * @version 1.0
 * @since 11/24/2020
 */
public class LoggerHandler extends ConsoleHandler {
    public LoggerHandler() {
        this.setOutputStream(System.out);
    }

    public static LoggerHandler getInstance() {
        return LoggerHandlerHover.HANDLER;
    }

    private static class LoggerHandlerHover {
        private static final LoggerHandler HANDLER = new LoggerHandler();
    }
}
