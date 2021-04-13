package com.mu.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author mujiangkui
 * @description 监听session对象，实现在线人数的统计
 */

public class MyHttpSessionListener implements HttpSessionListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyHttpSessionListener.class);

    private Integer count = 0;

    /**
     * @param httpSessionEvent httpSessionEvent
     */
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        LOGGER.info("新用户上线了");
        count++;
        httpSessionEvent.getSession().getServletContext().setAttribute("count", count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        LOGGER.info("用户下线了");
        count--;
        httpSessionEvent.getSession().getServletContext().setAttribute("count", count);
    }
}
