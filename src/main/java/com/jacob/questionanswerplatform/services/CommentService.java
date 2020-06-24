package com.jacob.questionanswerplatform.services;

import com.jacob.questionanswerplatform.daos.CommentDAO;
import com.jacob.questionanswerplatform.daos.AnswerDAO;
import com.jacob.questionanswerplatform.daos.UserDAO;
import com.jacob.questionanswerplatform.dtos.GetCommentDTO;
import com.jacob.questionanswerplatform.dtos.PostCommentDTO;
import com.jacob.questionanswerplatform.models.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;


@Service
public class CommentService {

	private final CommentDAO commentDAO;
	private final AnswerDAO  answerDAO;
	private final UserDAO    userDAO;

	public CommentService(CommentDAO commentDAO, AnswerDAO answerDAO, UserDAO userDAO) {
		this.commentDAO = commentDAO;
		this.answerDAO = answerDAO;
		this.userDAO = userDAO;
	}

	public Long postComment(PostCommentDTO commentDTO) {
		Comment comment = new Comment();

		comment.setComment(commentDTO.getCommentText());
		comment.setDate(Date.valueOf(LocalDate.now()));

		answerDAO.findById(commentDTO.getAnswerId())
		         .ifPresent(comment::setAnswer);

		userDAO.findById(commentDTO.getUserId())
		       .ifPresent(comment::setUser);

		return commentDAO.saveAndFlush(comment).getId();

	}

	public GetCommentDTO getCommentDTO(Long id) {

		Optional<Comment> optionalComment = commentDAO.findById(id);
		if (optionalComment.isEmpty()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

		Comment comment = optionalComment.get();

		return getCommentDTO(comment);
	}

	public GetCommentDTO getCommentDTO(Comment comment) {

		GetCommentDTO commentDTO = new GetCommentDTO();

		commentDTO.setCommentText(comment.getComment());
		commentDTO.setDate(comment.getDate());
		commentDTO.setUserId(comment.getUser().getId());

		return commentDTO;
	}

}
