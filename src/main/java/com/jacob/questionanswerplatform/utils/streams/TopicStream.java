package com.jacob.questionanswerplatform.utils.streams;

import com.jacob.questionanswerplatform.daos.SubTopicDAO;
import com.jacob.questionanswerplatform.daos.TopicDAO;
import com.jacob.questionanswerplatform.models.SubTopic;
import com.jacob.questionanswerplatform.models.Topic;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class TopicStream implements ForwardingStream<Topic> {

	private final List<Topic> topics;

	public TopicStream(List<Topic> topics) {
		this.topics = topics;
	}

	public TopicStream ifNewTopic(TopicDAO topicDAO) {
		Optional<Topic> optionalTopic = this.getTopic();
		if (optionalTopic.isEmpty()) return Topic.stream();

		Topic topic = optionalTopic.get();

		if (isNewTopic(topic, topicDAO)) return Topic.stream(topic);

		return Topic.stream();
	}

	public Optional<Topic> getTopic() {
		return this.getStream()
		           .findFirst();
	}

	private boolean isNewTopic(Topic topic, TopicDAO topicDAO) {
		return topicDAO.findTopByTopicName(topic.getTopicName()).isEmpty();
	}

	@Override
	public Stream<Topic> getStream() {
		return topics.stream();
	}

	public TopicStream addNewSubTopicsFromTopic(SubTopicDAO subTopicDAO) {
		Optional<Topic> optionalTopic = this.getTopic();
		if (optionalTopic.isEmpty()) return Topic.stream();

		Topic topic = optionalTopic.get();

		SubTopic.stream(topic.getSubTopics())
		        .filterNewSubTopics(subTopicDAO)
		        .addSubTopics(subTopicDAO);

		return Topic.stream(topic);
	}

	public TopicStream updateSubTopics(SubTopicDAO subTopicDAO) {
		Optional<Topic> optionalTopic = this.getTopic();
		if (optionalTopic.isEmpty()) return Topic.stream();

		Topic         topic     = optionalTopic.get();
		Set<SubTopic> subTopics = Set.copyOf(topic.getSubTopics());

		SubTopic.stream(subTopics)
		        .removeSubTopicsFrom(topic);

		SubTopic.stream(subTopics)
		        .addSubTopicsTo(topic.getSubTopics(), subTopicDAO);

		return Topic.stream(topic);

	}

	public Optional<Long> saveTopic(TopicDAO topicDAO) {
		Optional<Topic> optionalTopic = this.getTopic();
		if (optionalTopic.isEmpty()) return Optional.empty();

		Long createdTopicId = topicDAO.saveAndFlush(optionalTopic.get()).getId();
		return Optional.of(createdTopicId);
	}
}
