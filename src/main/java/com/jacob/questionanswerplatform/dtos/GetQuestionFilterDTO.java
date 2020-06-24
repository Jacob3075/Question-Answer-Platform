package com.jacob.questionanswerplatform.dtos;

import com.jacob.questionanswerplatform.models.Company;
import com.jacob.questionanswerplatform.models.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetQuestionFilterDTO {
	private Long          questionId;
	private String        questionText;
	private String        mostLikedAnswer;
	private int           likes;
	private Date          date;
	private List<Company> companies;
	private List<Tag>     tags;
}
