package com.jacob.questionanswerplatform.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answers")
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String answer;

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "answers_comments",
			joinColumns = @JoinColumn(name = "answers_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "answer_comments_id", referencedColumnName = "id")
	)
	private Set<AnswerComment> answerComments = new HashSet<>();

}
