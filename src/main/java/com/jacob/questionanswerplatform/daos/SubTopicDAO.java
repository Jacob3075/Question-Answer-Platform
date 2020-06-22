package com.jacob.questionanswerplatform.daos;

import com.jacob.questionanswerplatform.models.SubTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubTopicDAO extends JpaRepository<SubTopic, Long> {

	Optional<SubTopic> findSubTopicBySubTopicName(String name);

}
