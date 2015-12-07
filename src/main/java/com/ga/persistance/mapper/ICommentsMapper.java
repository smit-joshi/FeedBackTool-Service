package com.ga.persistance.mapper;

import java.util.List;

import com.ga.exception.GAException;
import com.ga.persistance.entity.CommentHistory;

/**
 * The Interface ICommentsMapper.
 *
 * @author Smit
 */
public interface ICommentsMapper {

    /**
     * Upload file.
     *
     * @param filePath the file path
     * @param comments the comments
     * @param userID the user id
     * @return true, if successful
     */
    boolean uploadFile(String filePath, String comments, String userID);

    /**
     * Gets the comments list.
     *
     * @param userID the user id
     * @return the comments list
     * @throws GAException
     */
    List<CommentHistory> getCommentsList(String userID) throws GAException;

    /**
     * Gets the comment by comment id.
     *
     * @param commentID the comment id
     * @return the comment by comment id
     * @throws GAException
     */
    CommentHistory getCommentByCommentID(int commentID) throws GAException;

}
