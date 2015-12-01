package com.ga.domain.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ga.common.JsonUtility;
import com.ga.domain.model.CommentDTO;
import com.ga.exception.ErrorCodes;
import com.ga.exception.GAException;
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
    @RequestMapping(value = "/addcomments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addComments(@RequestParam("filePath") String filePath, @RequestParam("comments") String comments,
            @RequestParam("userId") String userId) {

        LOGGER.info(String.format("comments : %s,comments : %s, userName : %s ", filePath, comments, userId));
        try {
            if (filePath.isEmpty() && comments.isEmpty()) {
                throw new GAException(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET);
            }

            if (!commentsService.addComments(filePath, comments, userId)) {
                LOGGER.info("File upload error");
                throw new GAException(ErrorCodes.GA_FILE_UPLOAD);
            }
            LOGGER.info("File upload successfull");
            return JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK, null);

        } catch (GAException e) {
            if (e.getCode() == ErrorCodes.GA_FILE_UPLOAD.getErrorCode()) {
                return JsonUtility.getJson(ErrorCodes.GA_FILE_UPLOAD, null);
            } else if (e.getCode() == ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET.getErrorCode()) {
                return JsonUtility.getJson(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET, null);
            } else {
                return JsonUtility.getJson(ErrorCodes.GA_INTERNAL, null);
            }
        }
    }

    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String uploadFile(@RequestParam CommonsMultipartFile file) {
        LOGGER.info("File size : " + file.getSize());
        CommentDTO commentDto = new CommentDTO();

        try {
            String resultFilePath = commentsService.uploadFile(file);
            if (resultFilePath.isEmpty()) {
                throw new GAException(ErrorCodes.GA_FILE_UPLOAD);
            }
            commentDto.setFilepath(resultFilePath);
            return JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK, commentDto);
        } catch (GAException e) {
            if (e.getCode() == ErrorCodes.GA_FILE_UPLOAD.getErrorCode()) {
                return JsonUtility.getJson(ErrorCodes.GA_FILE_UPLOAD, null);

            } else if (e.getCode() == ErrorCodes.GA_DATA_NOT_FOUND.getErrorCode()) {
                return JsonUtility.getJson(ErrorCodes.GA_DATA_NOT_FOUND, null);

            } else {
                return JsonUtility.getJson(ErrorCodes.GA_INTERNAL, null);
            }
        }
    }

    /**
     * Gets the all comments.
     *
     * @param userId the user id
     * @return the all comments
     */
    @RequestMapping(value = "getallcomments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getAllComments(@RequestParam("userId") String userId) {
        LOGGER.info("UserId : " + userId);
        try {
            if (userId.isEmpty()) {
                throw new GAException(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET);
            }

            List<CommentDTO> commentsDtoList = commentsService.getCommentsList(userId);
            return JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK, commentsDtoList);
        } catch (GAException e) {
            if (e.getCode() == ErrorCodes.GA_DATA_NOT_FOUND.getErrorCode()) {
                return JsonUtility.getJson(ErrorCodes.GA_DATA_NOT_FOUND, null);

            } else if(e.getCode() == ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET.getErrorCode()){
                return JsonUtility.getJson(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET, null);
                
            }else {
                return JsonUtility.getJson(ErrorCodes.GA_INTERNAL, null);
            }
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
            if (commentId.isEmpty()) {
                throw new GAException(ErrorCodes.GA_MANDATORY_PARAMETERS_NOT_SET);
            }
            CommentDTO commentsDto = commentsService.getCommentByCommentID(commentId);
            if (commentsDto == null) {
                throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
            }
            return JsonUtility.getJson(ErrorCodes.GA_TRANSACTION_OK, commentsDto);
        } catch (GAException e) {
            e.printStackTrace();
            return JsonUtility.getJson(ErrorCodes.GA_DATA_NOT_FOUND);
        }
    }
}
