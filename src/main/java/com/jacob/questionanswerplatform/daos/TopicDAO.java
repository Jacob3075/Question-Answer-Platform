package com.jacob.questionanswerplatform.daos;

import com.jacob.questionanswerplatform.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicDAO extends JpaRepository<Topic, Long> {

	Optional<Topic> findTopByTopicName(String name);

}
