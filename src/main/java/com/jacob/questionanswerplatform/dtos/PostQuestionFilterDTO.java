package com.jacob.questionanswerplatform.dtos;

import com.jacob.questionanswerplatform.models.Company;
import com.jacob.questionanswerplatform.models.SubTopic;
import com.jacob.questionanswerplatform.models.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostQuestionFilterDTO {

	@Nullable
	private List<Company> companies;

	@Nullable
	private List<Tag> tags;

	@Nullable
	private List<SubTopic> subTopics;

	@Nullable
	private int likesCount;

	@Nullable
	private Date date;


}
