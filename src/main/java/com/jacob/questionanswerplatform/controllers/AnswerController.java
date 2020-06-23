package com.jacob.questionanswerplatform.controllers;

import com.jacob.questionanswerplatform.dtos.GetAnswerDTO;
import com.jacob.questionanswerplatform.dtos.PostAnswerDTO;
import com.jacob.questionanswerplatform.services.AnswerService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestControllerAdvice
@RequestMapping(value = "api/answers/")
public class AnswerController {

	private final AnswerService answerService;

	public AnswerController(AnswerService answerService) {
		this.answerService = answerService;
	}

	@PostMapping(value = "/")
	public Long postAnswer(@Valid @RequestBody PostAnswerDTO answerDTO) {
		return answerService.postAnswer(answerDTO);
	}

	@GetMapping(value = "/{id}")
	public GetAnswerDTO getAnswer(@PathVariable Long id) {
		return answerService.getAnswerDTO(id);
	}
}
