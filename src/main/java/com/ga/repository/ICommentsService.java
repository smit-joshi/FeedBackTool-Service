package com.ga.repository;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ga.domain.model.CommentDTO;
import com.ga.exception.GAException;

/**
 * The Interface ICommentsService.
 *
 * @author Smit
 */
public interface ICommentsService {

    /**
     * Adds the comments.
     *
     * @param filePath the file path
     * @param comments the comments
     * @param userID the user id
     * @return true, if successful
     * @throws GAException the GA exception
     */
    boolean addComments(String filePath, String comments, String userID) throws GAException;

    /**
     * Gets the comments list.
     *
     * @param userID the user id
     * @return the comments list
     * @throws GAException the GA exception
     */
    List<CommentDTO> getCommentsList(String userID,Integer time) throws GAException;

    /**
     * Gets the comment by comment id.
     *
     * @param commentID the comment id
     * @return the comment by comment id
     * @throws GAException the GA exception
     */
    CommentDTO getCommentByCommentID(String commentID,Integer time) throws GAException;

    /**
     * Upload file.
     *
     * @param file the file
     * @return the string
     * @throws GAException the GA exception
     */
    String uploadFile(CommonsMultipartFile file) throws GAException;
}
