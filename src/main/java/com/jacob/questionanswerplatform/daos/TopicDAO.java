package com.jacob.questionanswerplatform.daos;

import com.jacob.questionanswerplatform.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicDAO extends JpaRepository<Topic, Long> {
}
