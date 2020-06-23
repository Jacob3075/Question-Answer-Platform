package com.jacob.questionanswerplatform.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostAnswerDTO {

	@NotNull
	private Long questionId;

	@Size(min = 50, max = 500)
	private String answerText;

	@NotNull
	private Long userId;
}
