package com.hypeboy.hypeBoard.repository;


import com.hypeboy.hypeBoard.entity.Comment;
import com.hypeboy.hypeBoard.entity.CommentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;


@Repository
public class CommentRepositoryImpl implements CommentRepository {
    public static class Columns {
        public final static String ID = "ID"; // int
        public final static String POST_ID = "POST_ID"; // int
        public final static String USER_ID = "USER_ID"; // String
        public final static String PARENT_ID = "PARENT_ID"; // int
        public final static String TEXT = "TEXT"; // String
        public final static String STATUS = "STATUS"; // STATUS ENUM
        public final static String CREATED_AT = "CREATED_AT"; // LocalDateTime
        public final static String UPDATED_AT = "UPDATED_AT"; // LocalDateTime
    }

    private final DataSource dataSource;

    @Autowired
    public CommentRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Comment> findById(int commentId) throws Exception {
        String sql = "select * from COMMENTS where ID = ?";
        Comment result = null;

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, commentId);
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        result = resultSetToComment(rs);
                    }
                }
            }
        }

        return Optional.ofNullable(result);
    }

    @Override
    public List<Comment> findByPostId(int postId, int lastId, int limit) throws Exception {
        String sql = "select * from COMMENTS where POST_ID = ? and ID > ? order by ID limit ?";
        List<Comment> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, postId);
                statement.setInt(2, lastId);
                statement.setInt(3, limit);
                try (ResultSet rs = statement.executeQuery()) {

                    while (rs.next()) {
                        result.add(resultSetToComment(rs));
                    }
                }
            }
        }

        return result;
    }

    @Override
    public boolean insert(Comment comment) throws SQLException {
        String sql = "insert into COMMENTS (POST_ID, USER_ID, TEXT, STATUS, PARENT_ID) values (?, ?, ?, ?, ?);";
        int result = 0;

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, comment.getPostId());
                statement.setString(2, comment.getUserId());
                statement.setString(3, comment.getText());
                statement.setString(4, comment.getStatus().getId());
                statement.setObject(5, comment.getParentId());

                result = statement.executeUpdate();
            }
        }

        return result > 0;
    }

    @Override
    public boolean update(Comment comment) throws SQLException {
        String sql = "update COMMENTS SET POST_ID = ?, USER_ID = ?, TEXT = ?, STATUS = ?, PARENT_ID =? where ID = ?";
        int result = 0;

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, comment.getPostId());
                statement.setString(2, comment.getUserId());
                statement.setString(3, comment.getText());
                statement.setString(4, comment.getStatus().getId());
                statement.setObject(5, comment.getParentId());
                statement.setInt(6, comment.getId());
                result = statement.executeUpdate();
            }
        }

        return result > 0;
    }

    // TODO - Delete when next pr merged
    @Override
    public boolean updateStatusDeleteById(int commentId) throws Exception {
        String selectChildrenQuery = "select ID from COMMENTS where PARENT_ID = ?";
        String updateStatusQuery = "update COMMENTS SET STATUS = 'deleted' where ID = ?";

        int result = 0;
        Queue<Integer> targetCommentIds = new LinkedList<>();
        targetCommentIds.add(commentId);

        try (Connection conn = dataSource.getConnection()) {
            try {
                conn.setAutoCommit(false);

                while (!targetCommentIds.isEmpty()) {
                    Integer targetId = targetCommentIds.poll();

                    try (PreparedStatement selectStatement = conn.prepareStatement(selectChildrenQuery)) {
                        selectStatement.setInt(1, targetId);
                        try (ResultSet rs = selectStatement.executeQuery()) {
                            while (rs.next()) {
                                targetCommentIds.add(rs.getInt(Columns.ID));
                            }
                        }
                    }

                    try (PreparedStatement statement = conn.prepareStatement(updateStatusQuery)) {
                        statement.setInt(1, targetId);
                        result = statement.executeUpdate();
                    }

                }

                conn.commit();
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            }
        }

        return result > 0;
    }

    // TODO - Delete when next pr merged
    @Override
    public boolean updateStatusDeleteByPostId(int postId) throws SQLException {
        String sql = "update COMMENTS SET STATUS = 'deleted' where POST_ID = ?";
        int result = 0;

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, postId);
                result = statement.executeUpdate();
            }
        }

        return result > 0;
    }

    @Override
    public boolean deletePermanently() throws Exception {
        String deleteQuery ="delete from COMMENTS where STATUS = 'deleted'";
        int result = 0;

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery)) {
                result = deleteStatement.executeUpdate();
            }
        }

        return result > 0;
    }


    @Override
    public boolean deletePermanentlyByPostId(int postId) throws Exception {
        String deleteQuery ="delete from COMMENTS where POST_ID = ?";
        int result = 0;

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery)) {
                deleteStatement.setInt(1, postId);
                result = deleteStatement.executeUpdate();
            }
        }

        return result > 0;
    }

    @Override
    public boolean markDelete(int commentId) throws SQLException {
        String sql = "update COMMENTS SET STATUS = 'deleted' where ID = ?";
        int result = 0;

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, commentId);
                result = statement.executeUpdate();
            }
        }

        return result > 0;
    }

    private Comment resultSetToComment(ResultSet rs) throws Exception {
        Integer id = rs.getInt(Columns.ID);
        Integer postId = rs.getInt(Columns.POST_ID);
        String userId = rs.getString(Columns.USER_ID);
        Integer parentId = rs.getInt(Columns.PARENT_ID);
        String content = rs.getString(Columns.TEXT);
        String statusString = rs.getString(Columns.STATUS);
        Timestamp createdAt = rs.getTimestamp(Columns.CREATED_AT);
        Timestamp updatedAt = rs.getTimestamp(Columns.UPDATED_AT);

        CommentStatus status = new CommentStatus(statusString);
        Comment comment = new Comment(id, postId, userId, content, parentId, status);
        comment.setCreatedAt(createdAt != null ? createdAt.toLocalDateTime() : null);
        comment.setUpdatedAt(updatedAt != null ? updatedAt.toLocalDateTime() : null);

        return comment;
    }


}
