package com.ga.domain.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ga.common.JsonUtility;
import com.ga.exception.ErrorCodes;
import com.ga.exception.GAException;
import com.ga.persistance.entity.CommentHistory;
import com.ga.repository.ICommentsService;

/**
 * The Class CommentsController.
 *
 * @author Smit
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentsController.class);

    /** The comments service. */
    @Autowired
    ICommentsService commentsService;

    /**
     * Upload file.
     *
     * @param filePath the file path
     * @param comments the comments
     * @param userId the user id
     * @return the string
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String uploadFile(@RequestParam("filePath") String filePath, @RequestParam("comments") String comments,
            @RequestParam("userId") String userId) {

        LOGGER.info(String.format("Filepath = %s, comments : %s, userName : %s ", filePath, comments, userId));
        try {
            if (commentsService.uploadFile(filePath, comments, userId)) {
                LOGGER.info("File upload successfull");
                return JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK);
            } else {
                LOGGER.info("File upload error");
                throw new GAException(ErrorCodes.GA_FILE_UPLOAD);
            }
        } catch (GAException e) {
            e.printStackTrace();
            return JsonUtility.getJson("Error");
        }
    }

    /**
     * Gets the all comments.
     *
     * @param userId the user id
     * @return the all comments
     */
    @RequestMapping(value = "getallcomments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllComments(@RequestParam("userId") String userId) {
        LOGGER.info("UserId : " + userId);
        try {
            List<CommentHistory> commentsHistoryList = commentsService.getCommentsList(userId);
            return JsonUtility.getJson(commentsHistoryList);

        } catch (GAException e) {
            e.printStackTrace();
            return JsonUtility.getJson("Error");
        }
    }

    /**
     * Gets the comment by id.
     *
     * @param commentId the comment id
     * @return the comment by id
     */
    @RequestMapping(value = "getcommentbyid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCommentById(@RequestParam("commentId") String commentId) {
        LOGGER.info("CommentId : " + commentId);
        try {
            CommentHistory commentHistory = commentsService.getCommentByCommentID(commentId);
            return JsonUtility.getJson(commentHistory);

        } catch (GAException e) {
            e.printStackTrace();
            return JsonUtility.getJson("Error");
        }
    }
}
