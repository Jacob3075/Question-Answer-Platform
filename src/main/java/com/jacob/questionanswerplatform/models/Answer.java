package com.jacob.questionanswerplatform.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answers")
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "answer")
	private String answerText;

	private int likes;

	@ManyToOne
	@JoinColumn(name = "users_id")
	@JsonIgnoreProperties({"answers", "questions", "answerComments"})
	private User user;

	@ManyToOne
	@JoinColumn(name = "questions_id")
	private Question question;

	@OneToMany(mappedBy = "answer")
	@JsonIgnoreProperties({"answer"})
	private List<AnswerComment> answerComments = new ArrayList<>();

}
