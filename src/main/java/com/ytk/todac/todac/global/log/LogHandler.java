package com.ytk.todac.todac.global.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;

@Slf4j
public class LogHandler {
    public static void printLog(Level level, String message) {
        if(level == Level.ERROR) log.error(message);
        else if(level == Level.WARN) log.warn(message);
        else if(level == Level.INFO) log.info(message);
        else if(level == Level.DEBUG) log.debug(message);
        else if(level == Level.TRACE) log.trace(message);
    }
}
