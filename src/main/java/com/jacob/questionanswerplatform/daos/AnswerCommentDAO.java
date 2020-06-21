package com.jacob.questionanswerplatform.daos;

import com.jacob.questionanswerplatform.models.AnswerComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerCommentDAO extends JpaRepository<AnswerComment, Long> {
}
