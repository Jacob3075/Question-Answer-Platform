package com.jacob.questionanswerplatform.utils.streams;

import com.jacob.questionanswerplatform.daos.QuestionDAO;
import com.jacob.questionanswerplatform.models.Question;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class QuestionStream implements ForwardingStream<Question> {

	private final List<Question> questions;

	public QuestionStream(List<Question> questions) {
		this.questions = questions;
	}

	public QuestionStream ifNotNewQuestion(Long id, QuestionDAO questionDAO) {
		Optional<Question> optionalQuestion = this.getQuestion();
		if (optionalQuestion.isEmpty()) return Question.stream();

		if (isNewQuestion(id, questionDAO)) return Question.stream();

		return Question.stream(optionalQuestion.get());
	}

	private Optional<Question> getQuestion() {
		return this.getStream()
		           .findFirst();
	}

	private boolean isNewQuestion(Long id, QuestionDAO questionDAO) {
		return questionDAO.findById(id).isEmpty();
	}

	@Override
	public Stream<Question> getStream() {
		return this.questions.stream();
	}

	public QuestionStream updateProperties(Long targetId, QuestionDAO questionDAO) {

		Optional<Question> optionalSourceQuestion = this.getQuestion();
		if (optionalSourceQuestion.isEmpty()) return Question.stream();

		Question question = optionalSourceQuestion.get();

		Optional<Question> optionalQuestion = questionDAO.findById(targetId);
		if (optionalQuestion.isEmpty()) return Question.stream();

		Question questionToUpdate = optionalQuestion.get();

		questionToUpdate.setQuestionText(question.getQuestionText());
		questionToUpdate.setCompanies(question.getCompanies());
		questionToUpdate.setTags(question.getTags());
//		questionToUpdate.setSubTopics(question.getSubTopics());

		return Question.stream(questionToUpdate);
	}

	public boolean save(QuestionDAO questionDAO) {
		Optional<Question> optionalQuestion = this.getQuestion();

		if (optionalQuestion.isEmpty()) return false;

		questionDAO.saveAndFlush(optionalQuestion.get());
		return true;
	}
}
