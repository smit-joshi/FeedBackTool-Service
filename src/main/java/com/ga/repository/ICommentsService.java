package com.ga.repository;

import java.util.List;

import com.ga.exception.GAException;
import com.ga.persistance.entity.CommentHistory;

/**
 * The Interface ICommentsService.
 *
 * @author Smit
 */
public interface ICommentsService {

    /**
     * Upload file.
     *
     * @param filePath the file path
     * @param comments the comments
     * @param userID the user id
     * @return true, if successful
     * @throws GAException the GA exception
     */
    boolean uploadFile(String filePath, String comments, String userID) throws GAException;

    /**
     * Gets the comments list.
     *
     * @param userID the user id
     * @return the comments list
     * @throws GAException the GA exception
     */
    List<CommentHistory> getCommentsList(String userID) throws GAException;

    /**
     * Gets the comment by comment id.
     *
     * @param commentID the comment id
     * @return the comment by comment id
     * @throws GAException the GA exception
     */
    CommentHistory getCommentByCommentID(String commentID) throws GAException;

}
