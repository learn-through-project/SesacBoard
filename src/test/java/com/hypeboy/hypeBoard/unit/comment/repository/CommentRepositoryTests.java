package com.hypeboy.hypeBoard.unit.comment.repository;

import com.hypeboy.hypeBoard.entity.Comment;
import com.hypeboy.hypeBoard.entity.CommentStatus;
import com.hypeboy.hypeBoard.repository.CommentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentRepositoryTests {

    @Autowired
    private CommentRepository commentRepository;

    private Comment validComment;
    private Comment invalidComment;

    private void createDummyComment() throws Exception {
        int existCommentId = 8;
        int existPostId = 1;
        String existUserId = "test1";
        String sampleText = "updated sample text";
        Integer existParentId = null;
        CommentStatus status = new CommentStatus("blocked");

        validComment = new Comment(existCommentId, existPostId, existUserId, sampleText, existParentId, status);

        invalidComment = new Comment(
                existCommentId + 10000,
                existPostId + 10000,
                existUserId + "invalid",
                sampleText,
                existParentId,
                status);
    }

    @BeforeEach
    public void setUp() throws Exception {
        createDummyComment();
    }

    @Test
    public void test() {
        Assertions.assertThat(true).isTrue();
    }


    @Test
    public void markDelete_Return_Fail() throws Exception {
        int notMatchedId = validComment.getId() + 10000;
        boolean isSuccess = commentRepository.markDelete(notMatchedId);
        Assertions.assertThat(isSuccess).isFalse();
    }

    @Test
    public void markDelete_Return_Success() throws Exception {
        int validCommentId = validComment.getId();
        boolean isSuccess = commentRepository.markDelete(validCommentId);
        Assertions.assertThat(isSuccess).isTrue();
    }

    @Test
    public void deletePermanentlyByPostId_Return_Success() throws Exception {
        int validPostId = validComment.getPostId();
        boolean isSuccess = commentRepository.deletePermanentlyByPostId(validPostId);
        Assertions.assertThat(isSuccess).isTrue();
    }



    @Test
    public void deletePermanent_Return_Success() throws Exception {
        boolean isSuccess = commentRepository.deletePermanently();
        Assertions.assertThat(isSuccess).isTrue();
    }

    @Test
    public void updateStatusReadyToDeleteByPostId_Return_Fail() throws Exception {
        int invalidCommentId = invalidComment.getPostId();
        boolean isSuccess = commentRepository.updateStatusDeleteByPostId(invalidCommentId);
        Assertions.assertThat(isSuccess).isFalse();
    }

    @Test
    public void updateStatusReadyToDeleteByPostId_Return_Success() throws Exception {
        int validCommentId = validComment.getPostId();
        boolean isSuccess = commentRepository.updateStatusDeleteByPostId(validCommentId);
        Assertions.assertThat(isSuccess).isTrue();
    }

    @Test
    public void updateStatusReadyToDeleteById_Return_Fail() throws Exception {
        int invalidCommentId = invalidComment.getId();
        boolean isSuccess = commentRepository.updateStatusDeleteById(invalidCommentId);
        Assertions.assertThat(isSuccess).isFalse();
    }

    @Test
    public void updateStatusReadyToDeleteById_Return_Success() throws Exception {
        int validCommentId = validComment.getId();
        boolean isSuccess = commentRepository.updateStatusDeleteById(validCommentId);
        Assertions.assertThat(isSuccess).isTrue();
    }

    @Test
    public void update_Throw_Exception() throws SQLException  {
        boolean isSuccess = commentRepository.update(invalidComment);
        Assertions.assertThat(isSuccess).isFalse();
    }

    @Test
    public void update_Return_Success() throws SQLException {
        boolean isSuccess = commentRepository.update(validComment);
        Assertions.assertThat(isSuccess).isTrue();
    }


    @Test
    public void insert_Throw_Exception() {
        assertThatThrownBy(() -> commentRepository.insert(invalidComment))
                .isInstanceOf(SQLException.class);
    }

    @Test
    public void insert_Return_Success() throws SQLException {
        boolean isSuccess = commentRepository.insert(validComment);
        Assertions.assertThat(isSuccess).isTrue();
    }


    @Test
    public void findByPostId_Return_Empty_Optional() {
        int postId = validComment.getPostId();
        int lastId = 12;
        int limit = -1;

        assertThatThrownBy(() -> commentRepository.findByPostId(postId, lastId, limit))
                .isInstanceOf(Exception.class);
    }

    @Test
    public void findByPostId_Return_Comment() throws Exception {
        int postId = validComment.getPostId();
        int lastId = 12;
        int limit = 10;
        List<Comment> comment = commentRepository.findByPostId(postId, lastId, limit);

        Assertions.assertThat(comment.size()).isLessThanOrEqualTo(limit);

        if (comment.size() > 0) {
            Assertions.assertThat(comment.get(0).getPostId()).isEqualTo(postId);
            Assertions.assertThat(comment.get(0).getId()).isEqualTo(lastId + 1);
        }
    }


    @Test
    public void findById_Return_Empty_Optional() throws Exception {
        int invalidCommentId = invalidComment.getId();
        Optional<Comment> comment = commentRepository.findById(invalidCommentId);

        Assertions.assertThat(comment).isNotNull();
        Assertions.assertThat(comment.isEmpty()).isTrue();
    }

    @Test
    public void findById_Return_Comment() throws Exception {
        int commentId = validComment.getId();
        Optional<Comment> comment = commentRepository.findById(commentId);

        Assertions.assertThat(comment).isNotNull();
        Assertions.assertThat(comment.isPresent()).isTrue();
        Assertions.assertThat(comment.get().getId()).isEqualTo(commentId);
    }

}