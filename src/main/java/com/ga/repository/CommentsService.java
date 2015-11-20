package com.ga.repository;

import com.ga.exception.GAException;

public interface CommentsService {

    String uploadFile(String filePath, String comments, String userID) throws GAException;

    String getCommentsList(String userID) throws GAException;

    String getCommentByCommentID(String commentID, String userID) throws GAException;

}
