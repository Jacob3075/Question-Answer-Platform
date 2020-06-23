package com.jacob.questionanswerplatform.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentDTO {

	@Size(min = 50, max = 500)
	private String commentText;

	@NotNull
	private Long userId;

	@NotNull
	private Long answerId;
}
