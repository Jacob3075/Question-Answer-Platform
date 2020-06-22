package com.jacob.questionanswerplatform.controllers;

import com.jacob.questionanswerplatform.models.Topic;
import com.jacob.questionanswerplatform.services.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/topics")
public class TopicController {

	private final TopicService topicService;

	public TopicController(TopicService topicService) {
		this.topicService = topicService;
	}

	@GetMapping(value = "/")
	public List<Topic> getAllTopics() {
		return topicService.getAllTopics();
	}

	@GetMapping(value = "/{id}")
	public Topic getTopicById(@PathVariable Long id) {
		Optional<Topic> optionalTopic = topicService.getTopicById(id);
		if (optionalTopic.isEmpty()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

		return optionalTopic.get();
	}

	@PostMapping(value = "/")
	public Long createNewTopic(@RequestBody Topic topic) {
		Optional<Long> optionalLong = topicService.createTopic(topic);
		if (optionalLong.isEmpty()) throw new HttpClientErrorException(HttpStatus.CONFLICT);

		return optionalLong.get();
	}

	@PutMapping(value = "/{targetId}")
	public void updateTopic(@PathVariable Long targetId, @RequestBody Topic sourceTopic,
	                        HttpServletResponse response) {
		if (topicService.updateTopic(targetId, sourceTopic)) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/{id}")
	public void deleteTopic(@PathVariable Long id, HttpServletResponse response) {
		if (topicService.deleteTopic(id)) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}
}
