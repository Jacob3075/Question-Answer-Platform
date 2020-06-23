package com.jacob.questionanswerplatform.controllers;

import com.jacob.questionanswerplatform.dtos.GetQuestionDTO;
import com.jacob.questionanswerplatform.dtos.PostQuestionDTO;
import com.jacob.questionanswerplatform.dtos.QuestionLikeDTO;
import com.jacob.questionanswerplatform.services.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}
