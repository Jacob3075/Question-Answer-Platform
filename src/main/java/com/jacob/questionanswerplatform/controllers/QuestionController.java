package com.jacob.questionanswerplatform.controllers;

import com.jacob.questionanswerplatform.models.Question;
import com.jacob.questionanswerplatform.services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

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

	@GetMapping(value = "/{id}")
	public Question getQuestionById(@PathVariable Long id) {
		Optional<Question> optionalQuestion = questionService.getQuestionById(id);
		if (optionalQuestion.isEmpty()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

		return optionalQuestion.get();
	}

	@PostMapping(value = "/")
	public void addQuestion(Question question) {
		questionService.save(question);
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

}
