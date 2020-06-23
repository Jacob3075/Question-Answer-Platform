package com.jacob.questionanswerplatform.controllers;

import com.jacob.questionanswerplatform.dtos.GetCommentDTO;
import com.jacob.questionanswerplatform.dtos.PostCommentDTO;
import com.jacob.questionanswerplatform.services.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/comments/")
public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping(value = "/{id}")
	public GetCommentDTO getComment(@PathVariable Long id) {
		return commentService.getCommentDTO(id);
	}

	@PostMapping(value = "/")
	public Long createComment(@Valid @RequestBody PostCommentDTO commentDTO) {
		return commentService.postComment(commentDTO);
	}
}
