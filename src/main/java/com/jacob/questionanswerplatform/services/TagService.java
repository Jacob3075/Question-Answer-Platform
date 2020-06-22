package com.jacob.questionanswerplatform.services;

import com.jacob.questionanswerplatform.daos.TagDAO;
import com.jacob.questionanswerplatform.models.Tag;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

	private final TagDAO tagDAO;

	public TagService(TagDAO tagDAO) {
		this.tagDAO = tagDAO;
	}

	public List<Tag>  getAllTags() {
		return tagDAO.findAll();
	}

	public Optional<Tag> getTagById(Long id) {
		return tagDAO.findById(id);
	}

	public Long createTag(Tag tag) {
		return tagDAO.saveAndFlush(tag).getId();
	}

	public boolean updateTag(Long id, Tag sourceTag) {
		Optional<Tag> optionalTag = tagDAO.findById(id);
		if (optionalTag.isEmpty()) return false;

		Tag tagToUpdate = optionalTag.get();

		tagToUpdate.setTagName(sourceTag.getTagName());

		tagDAO.saveAndFlush(tagToUpdate);
		return true;
	}

	public boolean deleteTag(Long id) {
		Optional<Tag> optionalTag = tagDAO.findById(id);
		if (optionalTag.isEmpty()) return false;

		tagDAO.deleteById(id);
		return true;
	}
}
