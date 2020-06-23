package com.jacob.questionanswerplatform.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionLikeDTO {

	@NotNull
	private Long userId;

	@NotNull
	private Long questionId;
}
