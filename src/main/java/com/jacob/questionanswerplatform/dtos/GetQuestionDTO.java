package com.jacob.questionanswerplatform.dtos;

import com.jacob.questionanswerplatform.models.Company;
import com.jacob.questionanswerplatform.models.Tag;
import com.jacob.questionanswerplatform.models.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetQuestionDTO {
	private String             questionText;
	private List<GetAnswerDTO> answers;
	private int                likes;
	private List<Company>      companies;
	private List<Tag>          tags;
	private List<Topic>        topics;
}
