package com.jacob.questionanswerplatform.services;

import com.jacob.questionanswerplatform.daos.AnswerDAO;
import com.jacob.questionanswerplatform.daos.QuestionDAO;
import com.jacob.questionanswerplatform.daos.UserDAO;
import com.jacob.questionanswerplatform.dtos.GetAnswerDTO;
import com.jacob.questionanswerplatform.dtos.GetCommentDTO;
import com.jacob.questionanswerplatform.dtos.PostAnswerDTO;
import com.jacob.questionanswerplatform.models.Answer;
import com.jacob.questionanswerplatform.models.AnswerComment;
import com.jacob.questionanswerplatform.models.Question;
import com.jacob.questionanswerplatform.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

	private final AnswerDAO answerDAO;
	private final UserDAO   userDAO;

	private final QuestionDAO questionDAO;

	public AnswerService(AnswerDAO answerDAO, UserDAO userDAO, QuestionDAO questionDAO) {
		this.answerDAO = answerDAO;
		this.userDAO = userDAO;
		this.questionDAO = questionDAO;
	}

	public Long postAnswer(PostAnswerDTO answerDTO) {
		Answer answer = new Answer();

		answer.setAnswerComments(new ArrayList<>());
		answer.setAnswerText(answerDTO.getAnswerText());
		answer.setLikes(0);

		Optional<User> optionalUser = userDAO.findById(answerDTO.getUserId());
		if (optionalUser.isEmpty()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

		User user = optionalUser.get();

		Optional<Question> optionalQuestion = questionDAO.findById(answerDTO.getQuestionId());
		if (optionalQuestion.isEmpty()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

		Question question = optionalQuestion.get();

		answer.setQuestion(question);
		answer.setUser(user);

		return answerDAO.saveAndFlush(answer).getId();
	}

	public GetAnswerDTO getAnswerDTO(Long id) {
		Optional<Answer> optionalAnswer = answerDAO.findById(id);
		if (optionalAnswer.isEmpty()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

		Answer       answer    = optionalAnswer.get();

		return this.getAnswerDTO(answer);
	}

	public GetAnswerDTO getAnswerDTO(Answer answer) {
		GetAnswerDTO answerDTO = new GetAnswerDTO();

		answerDTO.setLikes(answer.getLikes());
		answerDTO.setAnswerText(answer.getAnswerText());
		answerDTO.setUserId(answer.getUser().getId());

		List<GetCommentDTO> commentDTOs    = new ArrayList<>();
		List<AnswerComment> answerComments = answer.getAnswerComments();

		answerComments.forEach(comment -> {
			GetCommentDTO commentDTO = new GetCommentDTO();
			commentDTO.setCommentText(comment.getComment());
			commentDTO.setUserId(comment.getUser().getId());
			commentDTO.setDate(comment.getDate());
			commentDTOs.add(commentDTO);
		});

		answerDTO.setAnswerComments(commentDTOs);
		return answerDTO;
	}
}
