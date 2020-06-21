package com.jacob.questionanswerplatform.daos;

import com.jacob.questionanswerplatform.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerDAO extends JpaRepository<Answer , Long> {
}
