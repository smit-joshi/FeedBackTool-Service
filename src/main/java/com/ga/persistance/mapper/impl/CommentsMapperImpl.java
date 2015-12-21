package com.ga.persistance.mapper.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ga.exception.ErrorCodes;
import com.ga.exception.GAException;
import com.ga.persistance.entity.CommentHistory;
import com.ga.persistance.entity.UserDetail;
import com.ga.persistance.mapper.ICommentsMapper;

/**
 * The Class CommentsMapperImpl.
 *
 * @author Smit
 */
@Repository
public class CommentsMapperImpl implements ICommentsMapper {
    /** The session factory. */
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public boolean uploadFile(String filePath, String comments, String userID) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CommentHistory commentsHistory = new CommentHistory();
        commentsHistory.setCommentsDetail(comments);
        commentsHistory.setFilepath(filePath);
        commentsHistory.setUserId(new UserDetail(userID));

        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        simpleDateFormat.setTimeZone(timeZone);

        commentsHistory.setCommentDate(calendar.getTime());

        session.save(commentsHistory);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ga.persistance.mapper.ICommentsMapper#getCommentsList(java.lang.String)
     */
    @Override
    public List<CommentHistory> getCommentsList(String userID) throws GAException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        UserDetail userDetail = new UserDetail(userID);

        String hql = "FROM CommentHistory where userId =" + userDetail.getUserId() + "ORDER BY commentDate DESC";
        Query query = session.createQuery(hql);
        List<CommentHistory> communityResuleList = query.list();

        if (communityResuleList.isEmpty()) {
            throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
        }

        session.getTransaction().commit();
        session.close();
        return communityResuleList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ga.persistance.mapper.ICommentsMapper#getCommentByCommentID(int)
     */
    @Override
    public CommentHistory getCommentByCommentID(int commentID) throws GAException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        CommentHistory commentHistory = (CommentHistory) session.get(CommentHistory.class, commentID);

        if (commentHistory == null) {
            throw new GAException(ErrorCodes.GA_DATA_NOT_FOUND);
        }

        session.getTransaction().commit();
        session.close();
        return commentHistory;
    }

}
