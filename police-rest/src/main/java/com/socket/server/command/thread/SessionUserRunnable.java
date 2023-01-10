package com.socket.server.command.thread;

import com.inch.rest.utils.SessionUtils;
import org.apache.log4j.Logger;

public class SessionUserRunnable implements Runnable {
    private static final Logger logger = Logger.getLogger(SessionUserRunnable.class);

    public SessionUserRunnable() {
    }

    public void run() {
        SessionUtils.REST_USER.clear();
    }
}
