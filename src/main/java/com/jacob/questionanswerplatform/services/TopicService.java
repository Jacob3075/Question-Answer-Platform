package com.jacob.questionanswerplatform.services;

import com.jacob.questionanswerplatform.daos.SubTopicDAO;
import com.jacob.questionanswerplatform.daos.TopicDAO;
import com.jacob.questionanswerplatform.models.SubTopic;
import com.jacob.questionanswerplatform.models.Topic;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

	private final TopicDAO    topicDAO;
	private final SubTopicDAO subTopicDAO;

	public TopicService(TopicDAO topicDAO, SubTopicDAO subTopicDAO) {
		this.topicDAO = topicDAO;
		this.subTopicDAO = subTopicDAO;
	}

	public List<Topic> getAllTopics() {
		return topicDAO.findAll();
	}

	public Optional<Topic> getTopicById(Long id) {
		return topicDAO.findById(id);
	}

	public Optional<Long> createTopic(Topic topic) {
		return Topic.stream(topic)
		            .ifNewTopic(topicDAO)
		            .addNewSubTopicsFromTopic(subTopicDAO)
		            .updateSubTopics(subTopicDAO)
		            .saveTopic(topicDAO);
	}

	public boolean updateTopic(Long id, Topic sourceTopic) {
		Optional<Topic> optionalTopic = topicDAO.findById(id);
		if (optionalTopic.isEmpty()) return false;

		Topic topicToUpdate = optionalTopic.get();

		SubTopic.stream(sourceTopic.getSubTopics())
		        .filterNewSubTopics(subTopicDAO)
		        .addSubTopics(subTopicDAO);

		SubTopic.stream(topicToUpdate.getSubTopics())
		        .removeSubTopicsFrom(topicToUpdate);

		SubTopic.stream(sourceTopic.getSubTopics())
		        .addSubTopicsTo(topicToUpdate.getSubTopics(), subTopicDAO);

		topicToUpdate.setTopicName(sourceTopic.getTopicName());

		topicDAO.saveAndFlush(topicToUpdate);
		return true;
	}

	public boolean deleteTopic(Long id) {
		Optional<Topic> optionalTopic = topicDAO.findById(id);
		if (optionalTopic.isEmpty()) return false;

		topicDAO.deleteById(id);
		return true;
	}
}
