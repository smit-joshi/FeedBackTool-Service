package com.ga.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ga.exception.GAException;
import com.ga.repository.CommentsService;

public class CommentsServiceImpl implements CommentsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public String uploadFile(String filePath, String comments, String userID) throws GAException {
        LOGGER.info("Upload file called!!");
        return null;
    }

    @Override
    public String getCommentsList(String userID) throws GAException {
        LOGGER.info("Get commemts list called!!");
        return null;
    }

    @Override
    public String getCommentByCommentID(String commentID, String userID) throws GAException {
        LOGGER.info("Get commemt by comment id called!!");
        return null;
    }
}