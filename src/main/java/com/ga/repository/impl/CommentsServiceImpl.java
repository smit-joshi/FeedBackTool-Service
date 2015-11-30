package com.ga.repository.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ga.domain.model.CommentDTO;
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
    public boolean addComments(String filePath, String comments, String userID) throws GAException {
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
    public List<CommentDTO> getCommentsList(String userID) throws GAException {
        LOGGER.info("Get commemts list called!!");
        List<CommentHistory> commentHistoryList = commentsMapper.getCommentsList(userID);
        List<CommentDTO> commentsDtoList = new ArrayList<CommentDTO>();

        if (commentHistoryList.isEmpty()) {
            throw new GAException(ErrorCodes.GA_FILE_UPLOAD);
        }

        for (CommentHistory commentHistory : commentHistoryList) {
            commentsDtoList.add(convertEntityToDTO(commentHistory));
        }

        if (commentHistoryList.isEmpty()) {
            throw new GAException(ErrorCodes.GA_FILE_UPLOAD);
        } else {
            return commentsDtoList;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ga.repository.ICommentsService#getCommentByCommentID(java.lang.String)
     */
    @Override
    public CommentDTO getCommentByCommentID(String commentID) throws GAException {
        LOGGER.info("Get commemt by comment id called!!");
        int commentId = Integer.parseInt(commentID);
        CommentHistory commentHistory = commentsMapper.getCommentByCommentID(commentId);

        if (commentHistory == null) {
            throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
        }

        return convertEntityToDTO(commentHistory);
    }

    /**
     * Convert entity to dto.
     *
     * @param commentHistory the comment history
     * @return the comment dto
     */
    private CommentDTO convertEntityToDTO(CommentHistory commentHistory) {
        CommentDTO commentDto = new CommentDTO();
        commentDto.setCommentDate(commentHistory.getCommentDate());
        commentDto.setCommentId(commentHistory.getCommentId());
        commentDto.setCommentsDetail(commentHistory.getCommentsDetail());
        commentDto.setFilepath(commentHistory.getFilepath());

        return commentDto;
    }

    @Override
    public String uploadFile(CommonsMultipartFile file) throws GAException {
        LOGGER.info("Upload file called!!");
        String fileName;
        try {
            fileName = checkIsFile(file);
            if (fileName.isEmpty()) {
                throw new GAException(ErrorCodes.GA_FILE_UPLOAD);
            }
            LOGGER.info(String.format("Upload file complete!! File path : %s", fileName));
            return fileName;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (SerialException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String checkIsFile(CommonsMultipartFile file) throws IllegalStateException, IOException, SerialException,
            SQLException {
        LOGGER.info("checkIsFile :" + file.getSize());
        LOGGER.info("checkIsFile :" + file);

        if (!file.isEmpty()) {
            LOGGER.info("checkIsFile return true:");
            byte[] bytes = file.getBytes();

            String fileName = file.getOriginalFilename();
            File newFile = new File("../webapps/FeedbackTool/comments/" + fileName);

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(newFile));
            LOGGER.info("newFile :" + newFile);

            bufferedOutputStream.write(bytes);
            bufferedOutputStream.close();
            return newFile.getPath();
        } else {
            LOGGER.info("checkIsFile return false:");
            return null;
        }
    }

}