package com.jacob.questionanswerplatform.controllers;

import com.jacob.questionanswerplatform.models.SubTopic;
import com.jacob.questionanswerplatform.services.SubTopicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/subtopics/")
public class SubTopicController {

	private final SubTopicService subTopicService;

	public SubTopicController(SubTopicService subTopicService) {
		this.subTopicService = subTopicService;
	}

	@GetMapping(value = "/")
	public List<SubTopic> getAllSubTopics() {
		return subTopicService.getAllSubTopics();
	}

	@GetMapping(value = "/{id}")
	public SubTopic getSubTopicById(@PathVariable Long id) {
		Optional<SubTopic> optionalSubTopic = subTopicService.getSubTopicById(id);
		if (optionalSubTopic.isEmpty()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

		return optionalSubTopic.get();
	}

	@PostMapping(value = "/")
	public Long createNewSubTopic(@RequestBody SubTopic subTopic) {
		return subTopicService.createSubTopic(subTopic);
	}

	@PutMapping(value = "/{targetId}")
	public void updateSubTopic(@PathVariable Long targetId, @RequestBody SubTopic sourceSubTopic, HttpServletResponse response) {
		if (subTopicService.updateSubTopic(targetId, sourceSubTopic)) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/{id}")
	public void deleteSubTopic(@PathVariable Long id, HttpServletResponse response) {
		if (subTopicService.deleteSubTopic(id)) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}
}
