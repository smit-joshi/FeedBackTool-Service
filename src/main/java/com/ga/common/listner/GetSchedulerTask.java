package com.ga.common.listner;

import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.ga.persistance.entity.CommentHistory;
import com.ga.persistance.mapper.ICommentsMapper;
import com.ga.persistance.mapper.impl.CommentsMapperImpl;

/**
 * The Class GetSchedulerTask.
 * 
 * @author Smit
 */
public class GetSchedulerTask extends TimerTask {

    private final static Logger LOGGER = Logger.getLogger("GetSchedulerTask");

    /*
     * (non-Javadoc)
     * 
     * @see java.util.TimerTask#run()
     */
    @Override
    public void run() {
        LOGGER.info("Scheduler called");

        ICommentsMapper mapper = new CommentsMapperImpl();

        try {
            List<CommentHistory> list = mapper.getCommentsList("1");
            System.out.println("Size :" + list.size());
            LOGGER.info("Scheduler job called successfully");
        } catch (Exception e) {
            LOGGER.error("Error while communicate with scheduler job");
            LOGGER.error(e);
        }
    }

}