package com.jacob.questionanswerplatform.services;

import com.jacob.questionanswerplatform.daos.AnswerDAO;
import com.jacob.questionanswerplatform.daos.QuestionDAO;
import com.jacob.questionanswerplatform.daos.UserDAO;
import com.jacob.questionanswerplatform.dtos.AnswerLikeDTO;
import com.jacob.questionanswerplatform.dtos.GetAnswerDTO;
import com.jacob.questionanswerplatform.dtos.GetCommentDTO;
import com.jacob.questionanswerplatform.dtos.PostAnswerDTO;
import com.jacob.questionanswerplatform.models.Answer;
import com.jacob.questionanswerplatform.models.Comment;
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

	private final QuestionDAO    questionDAO;
	private final CommentService commentService;

	public AnswerService(AnswerDAO answerDAO, UserDAO userDAO, QuestionDAO questionDAO,
	                     CommentService commentService) {
		this.answerDAO = answerDAO;
		this.userDAO = userDAO;
		this.questionDAO = questionDAO;
		this.commentService = commentService;
	}

	public Long postAnswer(PostAnswerDTO answerDTO) {
		Answer answer = new Answer();

		answer.setComments(new ArrayList<>());
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

		Answer answer = optionalAnswer.get();

		return this.getAnswerDTO(answer);
	}

	public GetAnswerDTO getAnswerDTO(Answer answer) {
		GetAnswerDTO answerDTO = new GetAnswerDTO();

		answerDTO.setLikes(answer.getLikes());
		answerDTO.setAnswerText(answer.getAnswerText());
		answerDTO.setUserId(answer.getUser().getId());

		List<GetCommentDTO> commentDTOs = new ArrayList<>();
		List<Comment>       comments    = answer.getComments();

		comments.forEach(comment -> commentDTOs.add(commentService.getCommentDTO(comment)));

		answerDTO.setAnswerComments(commentDTOs);
		return answerDTO;
	}

	public void like(AnswerLikeDTO answerLikeDTO) {
		if (userDAO.findById(answerLikeDTO.getUserId()).isEmpty())
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

		Optional<Answer> optionalAnswer = answerDAO.findById(answerLikeDTO.getAnswerId());
		if (optionalAnswer.isEmpty()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

		Answer answer = optionalAnswer.get();
		answer.setLikes(answer.getLikes() + 1);

		answerDAO.saveAndFlush(answer);
	}
}
