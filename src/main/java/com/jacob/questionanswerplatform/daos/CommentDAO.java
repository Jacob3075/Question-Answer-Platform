package com.jacob.questionanswerplatform.daos;

import com.jacob.questionanswerplatform.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDAO extends JpaRepository<Comment, Long> {
}
