package com.jacob.questionanswerplatform.controllers;

import com.jacob.questionanswerplatform.dtos.GetQuestionDTO;
import com.jacob.questionanswerplatform.dtos.PostQuestionDTO;
import com.jacob.questionanswerplatform.models.Question;
import com.jacob.questionanswerplatform.services.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/questions")
public class QuestionController {

	private final QuestionService questionService;

	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}

	@GetMapping(value = "/")
	public List<Question> getAllQuestions() {
		return questionService.getAllQuestions();
	}

	@PutMapping(value = "/{id}")
	public void updateQuestion(@PathVariable Long id, @RequestBody Question updatedQuestion,
	                           HttpServletResponse response) {
		if (questionService.updateQuestion(id, updatedQuestion)) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/{id}")
	public void deleteQuestion(@PathVariable Long id) {
		questionService.deleteQuestion(id);
	}

	@PostMapping(value = "/")
	public Long newQuestion(@Valid @RequestBody PostQuestionDTO postQuestionDTO) {
		return questionService.addNewQuestion(postQuestionDTO);
	}

	@GetMapping(value = "/{id}")
	public GetQuestionDTO findQuestionById(@PathVariable Long id) {
		return questionService.findQuestionById(id);
	}

}
