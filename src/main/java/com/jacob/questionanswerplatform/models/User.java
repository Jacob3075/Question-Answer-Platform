package com.jacob.questionanswerplatform.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "user_name")
	private String userName;

	//region Liked Entities
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "liked_answers",
			joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "answers_id", referencedColumnName = "id")
	)
	@JsonIgnoreProperties("answerComments")
	private Set<Answer> likedAnswers = new HashSet<>();

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "liked_questions",
			joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "questions_id", referencedColumnName = "id")
	)
	@JsonIgnoreProperties("answers")
	private Set<Question> likedQuestions = new HashSet<>();

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "liked_comments",
			joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "answer_comments_id", referencedColumnName = "id")
	)
	private Set<AnswerComment> likedComments = new HashSet<>();
	//endregion

	//region Users Entities
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "user_questions",
			joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "questions_id", referencedColumnName = "id")
	)
	@JsonIgnoreProperties("answers")
	private Set<Question> questions = new HashSet<>();

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "user_answers",
			joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "answers_id", referencedColumnName = "id")
	)
	@JsonIgnoreProperties("answerComments")
	private Set<Answer> answers = new HashSet<>();

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "user_answer_comments",
			joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "answer_comments_id", referencedColumnName = "id")
	)
	private Set<AnswerComment> answerComments = new HashSet<>();
	//endregion

}
