package com.jacob.questionanswerplatform.utils.streams;

import com.jacob.questionanswerplatform.dtos.GetQuestionFilterDTO;
import com.jacob.questionanswerplatform.models.*;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuestionStream implements ForwardingStream<Question> {

	private final List<Question> questions;

	public QuestionStream(List<Question> questions) {
		this.questions = questions;
	}

	public QuestionStream filterByLikesGreaterThan(Optional<Integer> optionalLikesCount) {
		if (optionalLikesCount.isEmpty()) return Question.stream(this.questions);
		int likesCount = optionalLikesCount.get();

		return Question.stream(this.getStream()
		                           .filter(question -> question.getLikes() > likesCount)
		                           .collect(Collectors.toList())
		);
	}

	@Override
	public Stream<Question> getStream() {
		return this.questions.stream();
	}

	public QuestionStream filterByDatePostedOn(Optional<Date> optionalDate) {
		if (optionalDate.isEmpty()) return Question.stream(this.questions);
		Date date = optionalDate.get();

		return Question.stream(this.getStream()
		                           .filter(question -> question.getDate().toLocalDate().isEqual(date.toLocalDate()))
		                           .collect(Collectors.toList())
		);
	}

	public Stream<GetQuestionFilterDTO> toFilterDTO() {
		return this.getStream()
		           .map(question -> {
			           GetQuestionFilterDTO getQuestionFilterDTO = new GetQuestionFilterDTO();

			           getQuestionFilterDTO.setQuestionText(question.getQuestionText());
			           getQuestionFilterDTO.setCompanies(question.getCompanies());
			           getQuestionFilterDTO.setDate(question.getDate());
			           getQuestionFilterDTO.setTags(question.getTags());
			           getQuestionFilterDTO.setQuestionId(question.getId());
			           getQuestionFilterDTO.setLikes(question.getLikes());

			           question.getAnswers()
			                   .stream()
			                   .max(Comparator.comparingInt(Answer::getLikes))
			                   .map(Answer::getAnswerText)
			                   .ifPresentOrElse(
					                   getQuestionFilterDTO::setMostLikedAnswer,
					                   () -> getQuestionFilterDTO.setMostLikedAnswer("")
			                   );

			           return getQuestionFilterDTO;
		           });
	}

	public <T> QuestionStream filterByContains(Optional<List<T>> optionalFilterList,
	                                           Function<Question, List<T>> getSourceList) {
		if (optionalFilterList.isEmpty()) return Question.stream(this.questions);
		List<T> companies = optionalFilterList.get();

		return Question.stream(this.getStream()
		                           .filter(question -> !Collections.disjoint(
				                           companies,
				                           getSourceList.apply(question)
		                           ))
		                           .collect(Collectors.toList())
		);
	}
}
