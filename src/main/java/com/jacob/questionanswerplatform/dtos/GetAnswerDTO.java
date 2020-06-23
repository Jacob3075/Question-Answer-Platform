package com.jacob.questionanswerplatform.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAnswerDTO {
	private String              answerText;
	private Long                userId;
	private int                 likes;
	private List<GetCommentDTO> answerComments;

}
