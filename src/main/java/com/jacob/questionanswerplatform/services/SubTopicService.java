package com.jacob.questionanswerplatform.services;

import com.jacob.questionanswerplatform.daos.SubTopicDAO;
import com.jacob.questionanswerplatform.models.SubTopic;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubTopicService {

	private final SubTopicDAO subTopicDAO;

	public SubTopicService(SubTopicDAO subTopicDAO) {
		this.subTopicDAO = subTopicDAO;
	}

	public List<SubTopic> getAllSubTopics() {
		return subTopicDAO.findAll();
	}

	public Optional<SubTopic> getSubTopicById(Long id) {
		return subTopicDAO.findById(id);
	}

	public Long createSubTopic(SubTopic subTopic) {
		return subTopicDAO.saveAndFlush(subTopic).getId();
	}

	public boolean updateSubTopic(Long id, SubTopic sourceSubTopic) {
		Optional<SubTopic> optionalSubTopic = subTopicDAO.findById(id);
		if (optionalSubTopic.isEmpty()) return false;

		SubTopic subTopicToUpdate = optionalSubTopic.get();

		subTopicToUpdate.setSubTopicName(sourceSubTopic.getSubTopicName());

		subTopicDAO.saveAndFlush(subTopicToUpdate);
		return true;
	}

	public boolean deleteSubTopic(Long id) {
		Optional<SubTopic> optionalSubTopic = subTopicDAO.findById(id);
		if (optionalSubTopic.isEmpty()) return false;

		subTopicDAO.deleteById(id);
		return true;
	}
}
