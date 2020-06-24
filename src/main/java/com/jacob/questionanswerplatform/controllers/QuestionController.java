package com.jacob.questionanswerplatform.controllers;

import com.jacob.questionanswerplatform.dtos.*;
import com.jacob.questionanswerplatform.services.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/questions")
public class QuestionController {

	private final QuestionService questionService;

	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}

	@PostMapping(value = "/")
	public Long newQuestion(@Valid @RequestBody PostQuestionDTO postQuestionDTO) {
		return questionService.postQuestion(postQuestionDTO);
	}

	@GetMapping(value = "/{id}")
	public GetQuestionDTO findQuestionById(@PathVariable Long id) {
		return questionService.getQuestionDTO(id);
	}

	@PostMapping(value = "/like")
	public void likeQuestion(@Valid @RequestBody QuestionLikeDTO questionLikeDTO) {
		questionService.like(questionLikeDTO);
	}

	@PostMapping(value = "/filter-by/")
	public List<GetQuestionFilterDTO> filterQuestions(@Valid @RequestBody PostQuestionFilterDTO questionFilterDTO) {
		return questionService.filterBy(questionFilterDTO);
	}
}
