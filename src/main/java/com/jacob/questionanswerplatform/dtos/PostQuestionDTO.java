package com.jacob.questionanswerplatform.dtos;

import com.jacob.questionanswerplatform.models.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostQuestionDTO {

	@Size(min = 50, max = 500)
	private String questionText;

	@Nullable
	private Long companyId;

	@NotNull
	private Long userId;

	@NotNull
	private Long subTopicId;

	@Nullable
	private List<Tag> tags;
}
