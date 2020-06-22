package com.jacob.questionanswerplatform.utils.streams;

import com.jacob.questionanswerplatform.daos.SubTopicDAO;
import com.jacob.questionanswerplatform.models.SubTopic;
import com.jacob.questionanswerplatform.models.Topic;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SubTopicStream implements ForwardingStream<SubTopic> {

	private final List<SubTopic> subTopics;

	public SubTopicStream(List<SubTopic> subTopics) {
		this.subTopics = subTopics;
	}

	public SubTopicStream filterNewSubTopics(SubTopicDAO subTopicDAO) {
		return SubTopic.stream(this.getStream()
		                           .filter(
				                           subTopic -> this.isNewSubTopics(
						                           subTopic.getSubTopicName(),
						                           subTopicDAO
				                           )
		                           )
		                           .collect(Collectors.toSet())
		);

	}

	@Override
	public Stream<SubTopic> getStream() {
		return subTopics.stream();
	}

	private boolean isNewSubTopics(String name, SubTopicDAO subTopicDAO) {
		return subTopicDAO.findSubTopicBySubTopicName(name).isEmpty();
	}

	public void addSubTopics(SubTopicDAO subTopicDAO) {
		this.getStream()
		    .forEach(subTopicDAO::saveAndFlush);
	}

	public void removeSubTopicsFrom(Topic topic) {
		Set<SubTopic> subTopicsToRemove = this.getStream()
		                                      .collect(Collectors.toSet());
		topic.getSubTopics().removeIf(subTopicsToRemove::contains);
	}

	public void addSubTopicsTo(Set<SubTopic> subTopics, SubTopicDAO subTopicDAO) {
		Set<SubTopic> subTopicsToAdd = this.getStream()
		                                   .collect(Collectors.toSet());
		subTopicsToAdd.forEach(subTopic -> subTopicDAO.findSubTopicBySubTopicName(subTopic.getSubTopicName())
		                                              .ifPresent(subTopics::add));
	}
}
