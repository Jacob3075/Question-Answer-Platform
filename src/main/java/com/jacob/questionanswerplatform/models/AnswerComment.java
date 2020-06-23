package com.jacob.questionanswerplatform.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class AnswerComment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String comment;

	@ManyToOne
	@JoinColumn(name = "answers_id")
	private Answer answer;

	@ManyToOne
	@JoinColumn(name = "users_id")
	@JsonIgnoreProperties({"answers", "questions", "answerComments"})
	private User user;

	private Date date;

}
