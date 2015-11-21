package com.ga.repository.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ga.exception.ErrorCodes;
import com.ga.exception.GAException;
import com.ga.persistance.entity.CommentHistory;
import com.ga.persistance.mapper.ICommentsMapper;
import com.ga.repository.ICommentsService;

/**
 * The Class CommentsServiceImpl.
 *
 * @author Smit
 */
@Service
@Transactional
public class CommentsServiceImpl implements ICommentsService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    /** The comments mapper. */
    @Autowired
    ICommentsMapper commentsMapper;

    /*
     * (non-Javadoc)
     * 
     * @see com.ga.repository.ICommentsService#uploadFile(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean uploadFile(String filePath, String comments, String userID) throws GAException {
        LOGGER.info("Upload file called!!");
        boolean result = commentsMapper.uploadFile(filePath, comments, userID);

        if (result) {
            LOGGER.info("File uploaded successfully");
            return true;
        } else {
            LOGGER.info("File upload error");
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ga.repository.ICommentsService#getCommentsList(java.lang.String)
     */
    @Override
    public List<CommentHistory> getCommentsList(String userID) throws GAException {
        LOGGER.info("Get commemts list called!!");
        List<CommentHistory> commentsHistory = commentsMapper.getCommentsList(userID);

        if (commentsHistory.isEmpty()) {
            throw new GAException(ErrorCodes.GA_FILE_UPLOAD);
        } else {
            return commentsHistory;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ga.repository.ICommentsService#getCommentByCommentID(java.lang.String)
     */
    @Override
    public CommentHistory getCommentByCommentID(String commentID) throws GAException {
        LOGGER.info("Get commemt by comment id called!!");
        int commentId = Integer.parseInt(commentID);
        CommentHistory commentHistory = commentsMapper.getCommentByCommentID(commentId);

        if (commentHistory == null) {
            throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
        } else {
            return commentHistory;
        }
    }
}