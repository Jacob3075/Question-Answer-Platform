package com.jacob.questionanswerplatform.services;

import com.jacob.questionanswerplatform.daos.QuestionDAO;
import com.jacob.questionanswerplatform.models.Question;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

	private final QuestionDAO questionDAO;

	public QuestionService(QuestionDAO questionDAO) {
		this.questionDAO = questionDAO;
	}

	public List<Question> getAllQuestions() {
		return questionDAO.findAll();
	}

	public Optional<Question> getQuestionById(Long id) {
		return questionDAO.findById(id);
	}

	public void save(Question question) {
		questionDAO.saveAndFlush(question);
	}

	public boolean updateQuestion(Long id, Question question) {
		return  Question.stream(question)
		        .ifNotNewQuestion(id, questionDAO)
		        .updateProperties(id, questionDAO)
		        .save(questionDAO);

	}

	public void deleteQuestion(Long id) {
		questionDAO.deleteById(id);
	}
}
