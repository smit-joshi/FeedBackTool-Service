package com.ga.repository.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentsServiceImpl.class);

    /** The comments mapper. */
    @Autowired
    ICommentsMapper commentsMapper;

    /*
     * (non-Javadoc)
     * 
     * @see com.ga.repository.ICommentsService#addComments(java.lang.String, java.lang.String, java.lang.String)
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
            throw new GAException(ErrorCodes.GA_INTERNAL);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ga.repository.ICommentsService#getCommentsList(java.lang.String)
     */
    @Override
    public List<CommentDTO> getCommentsList(String userID, Integer userTime) throws GAException {
        LOGGER.info("Get commemts list called!!");
        List<CommentHistory> commentHistoryList = commentsMapper.getCommentsList(userID);
        List<CommentDTO> commentsDtoList = new ArrayList<CommentDTO>();

        // get data from database and store with list object.
        if (commentHistoryList.isEmpty()) {
            throw new GAException(ErrorCodes.GA_INTERNAL);
        }

        for (CommentHistory commentHistory : commentHistoryList) {
            commentsDtoList.add(convertEntityToDTO(commentHistory, userTime));
        }
        // convert into dto and return to controller
        if (commentsDtoList.isEmpty()) {
            throw new GAException(ErrorCodes.GA_INTERNAL);
        } else {
            LOGGER.info("CommentsDtoList : " + commentsDtoList.toString());
            return commentsDtoList;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ga.repository.ICommentsService#getCommentByCommentID(java.lang.String)
     */
    @Override
    public CommentDTO getCommentByCommentID(String commentID, Integer userTime) throws GAException {
        LOGGER.info("Get commemt by comment id called!!");
        int commentId = Integer.parseInt(commentID);
        CommentHistory commentHistory = commentsMapper.getCommentByCommentID(commentId);

        if (commentHistory == null) {
            throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
        }

        CommentDTO commentDTO = convertEntityToDTO(commentHistory, userTime);
        if (commentDTO == null) {
            throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
        }

        return commentDTO;
    }

    /**
     * Convert entity to dto.
     *
     * @param commentHistory the comment history
     * @return the comment dto
     */
    private CommentDTO convertEntityToDTO(CommentHistory commentHistory, Integer userTime) {
        CommentDTO commentDto = new CommentDTO();
        Date d = commentHistory.getCommentDate();

        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        simpleDateFormat.setTimeZone(timeZone);
        calendar.setTime(d);
        calendar.add(Calendar.MINUTE, userTime);

        commentDto.setCommentDate(calendar.getTime());
        commentDto.setCommentId(commentHistory.getCommentId());
        commentDto.setCommentsDetail(commentHistory.getCommentsDetail());
        commentDto.setFilepath(commentHistory.getFilepath());
        return commentDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ga.repository.ICommentsService#uploadFile(org.springframework.web.multipart.commons.CommonsMultipartFile)
     */
    @Override
    public String uploadFile(CommonsMultipartFile file) throws GAException {
        LOGGER.info("Upload file called!!");
        String fileName;
        try {
            fileName = checkIsFile(file);
            if (fileName.isEmpty()) {
                LOGGER.info("File is empty!!");
                throw new GAException(ErrorCodes.GA_FILE_UPLOAD);
            }
            LOGGER.info(String.format("Upload file complete!! File path : %s", fileName));
            return fileName;
        } catch (IllegalStateException e) {
            LOGGER.info("Exception : " + e.getMessage());
            LOGGER.info("stack trace :  " + e);
            throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
        }
    }

    /**
     * Check is file.
     *
     * @param file the file
     * @return the string
     * @throws GAException the GA exception
     */
    private String checkIsFile(CommonsMultipartFile file) throws GAException {
        LOGGER.info("Checkfile is called!!");
        try {
            if (!file.isEmpty()) {
                LOGGER.info("checkIsFile return true:");
                byte[] bytes = file.getBytes();
                Long f = new Date().getTime();

                File newFile = new File("/var/lib/tomcat7/webapps/ROOT/comments/" + f + ".jpg");

                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(newFile));
                LOGGER.info("newFile :" + newFile);

                bufferedOutputStream.write(bytes);
                bufferedOutputStream.close();
                LOGGER.info("return file :" + newFile.getName());
                return newFile.getName();

            } else {
                LOGGER.info("checkIsFile return false:");
                throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
        }
    }
}