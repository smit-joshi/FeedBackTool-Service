package com.ga.persistance.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "comment_history")
public class CommentHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    @Basic(optional = false)
    private Integer commentId;

    @Column(name = "FILEPATH")
    @Basic(optional = false)
    private String filePath;

    @Column(name = "COMMENTS")
    @Basic(optional = false)
    private String comments;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "COMMENT_DATE")
    private Date commentDate;

    @JoinColumn(name = "user_id", referencedColumnName = "UserDetail")
    @OneToMany
    private UserDetail userDetail;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    @Override
    public String toString() {
        return "CommentHistory [commentId=" + commentId + ", filePath=" + filePath + ", comments=" + comments
                + ", commentDate=" + commentDate + ", userDetail=" + userDetail + "]";
    }
}