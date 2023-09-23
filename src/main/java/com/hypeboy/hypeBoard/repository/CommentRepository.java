package com.hypeboy.hypeBoard.repository;

import com.hypeboy.hypeBoard.entity.Comment;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Optional<Comment> findById(int commentId) throws Exception;
    List<Comment> findByPostId(int postId, int lastId, int limit) throws Exception;
    boolean insert(Comment comment) throws SQLException;

    boolean update(Comment comment) throws SQLException;

    boolean updateStatusDeleteById(int commentId) throws Exception;

    boolean updateStatusDeleteByPostId(int commentId) throws Exception;

    boolean deletePermanently() throws Exception;
}
