package com.ga.common.listner;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * The listener interface for receiving appServletContext events. The class that is interested in processing a
 * appServletContext event implements this interface, and the object created with that class is registered with a
 * component using the component's <code>addAppServletContextListener<code> method. When
 * the appServletContext event occurs, that object's appropriate
 * method is invoked.
 * 
 * @author Smit
 */
public class AppServletContextListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger("AppServletContextListener");

    private static final long DELAY = 4 * 60 * 60000;

    private static final long INITIAL_DELAY = 60 * 60000;

    private GetSchedulerTask schedulerTask = new GetSchedulerTask();

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("ServletContextListener started");

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(schedulerTask, INITIAL_DELAY, DELAY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("ServletContextListener destroyed");
    }

}
