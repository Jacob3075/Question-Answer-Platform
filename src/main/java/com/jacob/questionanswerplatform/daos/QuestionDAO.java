package com.jacob.questionanswerplatform.daos;

import com.jacob.questionanswerplatform.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionDAO extends JpaRepository<Question, Long> {
}
