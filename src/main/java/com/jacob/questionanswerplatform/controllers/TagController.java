package com.jacob.questionanswerplatform.controllers;

import com.jacob.questionanswerplatform.models.Tag;
import com.jacob.questionanswerplatform.services.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/tags")
public class TagController {

	private final TagService tagService;

	public TagController(TagService tagService) {
		this.tagService = tagService;
	}

	@GetMapping(value = "/")
	public List<Tag> getAllTags() {
		return tagService.getAllTags();
	}

	@GetMapping(value = "/{id}")
	public Tag getTagById(@PathVariable Long id) {
		Optional<Tag> optionalTag = tagService.getTagById(id);
		if (optionalTag.isEmpty()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

		return optionalTag.get();
	}

	@PostMapping(value = "/")
	public Long createNewTag(@RequestBody Tag tag) {
		return tagService.createTag(tag);
	}

	@PutMapping(value = "/{targetId}")
	public void updateTag(@PathVariable Long targetId, @RequestBody Tag sourceTag, HttpServletResponse response) {
		if (tagService.updateTag(targetId, sourceTag)) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/{id}")
	public void deleteTag(@PathVariable Long id, HttpServletResponse response) {
		if (tagService.deleteTag(id)) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}
}
