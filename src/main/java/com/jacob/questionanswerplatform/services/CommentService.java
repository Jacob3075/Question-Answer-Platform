package com.jacob.questionanswerplatform.services;

import com.jacob.questionanswerplatform.daos.AnswerCommentDAO;
import com.jacob.questionanswerplatform.daos.AnswerDAO;
import com.jacob.questionanswerplatform.daos.UserDAO;
import com.jacob.questionanswerplatform.dtos.GetCommentDTO;
import com.jacob.questionanswerplatform.dtos.PostCommentDTO;
import com.jacob.questionanswerplatform.models.AnswerComment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;


@Service
public class CommentService {

	private final AnswerCommentDAO answerCommentDAO;
	private final AnswerDAO        answerDAO;
	private final UserDAO          userDAO;

	public CommentService(AnswerCommentDAO answerCommentDAO, AnswerDAO answerDAO, UserDAO userDAO) {
		this.answerCommentDAO = answerCommentDAO;
		this.answerDAO = answerDAO;
		this.userDAO = userDAO;
	}

	public Long postComment(PostCommentDTO commentDTO) {
		AnswerComment answerComment = new AnswerComment();

		answerComment.setComment(commentDTO.getCommentText());
		answerComment.setDate(Date.valueOf(LocalDate.now()));

		answerDAO.findById(commentDTO.getAnswerId())
		         .ifPresent(answerComment::setAnswer);

		userDAO.findById(commentDTO.getUserId())
		       .ifPresent(answerComment::setUser);

		return answerCommentDAO.saveAndFlush(answerComment).getId();

	}

	public GetCommentDTO getCommentDTO(Long id) {

		Optional<AnswerComment> optionalComment = answerCommentDAO.findById(id);
		if (optionalComment.isEmpty()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

		AnswerComment answerComment = optionalComment.get();

		return getCommentDTO(answerComment);
	}

	public GetCommentDTO getCommentDTO(AnswerComment answerComment) {

		GetCommentDTO commentDTO = new GetCommentDTO();

		commentDTO.setCommentText(answerComment.getComment());
		commentDTO.setDate(answerComment.getDate());
		commentDTO.setUserId(answerComment.getUser().getId());

		return commentDTO;
	}

}
