package com.jacob.questionanswerplatform.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jacob.questionanswerplatform.utils.streams.QuestionStream;
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
@Table(name = "questions")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private int likes;

	@Column(name = "question")
	private String questionText;

	@OneToMany(mappedBy = "question")
	@JsonIgnoreProperties("question")
	private List<Answer> answers = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "users_id")
	@JsonIgnoreProperties({"answers", "questions", "answerComments"})
	private User user;

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "question_company",
			joinColumns = @JoinColumn(name = "questions_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "companies_id", referencedColumnName = "id")
	)
	private List<Company> companies = new ArrayList<>();

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "question_tag",
			joinColumns = @JoinColumn(name = "questions_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "id")
	)
	private List<Tag> tags = new ArrayList<>();

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "question_topic",
			joinColumns = @JoinColumn(name = "questions_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "topics_id", referencedColumnName = "id")
	)
	private List<Topic> topics = new ArrayList<>();


	public static QuestionStream stream(List<Question> questions) {
		return new QuestionStream(questions);
	}

	public static QuestionStream stream(Question question) {
		return new QuestionStream(List.of(question));
	}

	public static QuestionStream stream() {
		return new QuestionStream(List.of());
	}

}
