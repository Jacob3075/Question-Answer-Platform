package com.jacob.questionanswerplatform.models;

import com.jacob.questionanswerplatform.utils.streams.QuestionStream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String question;

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "question_answers",
			joinColumns = @JoinColumn(name = "questions_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "answers_id", referencedColumnName = "id")
	)
	private Set<Answer> answers = new HashSet<>();

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "question_company",
			joinColumns = @JoinColumn(name = "questions_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "companies_id", referencedColumnName = "id")
	)
	private Set<Company> companies = new HashSet<>();



	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "question_tag",
			joinColumns = @JoinColumn(name = "questions_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
	)
	private Set<Tag> tags = new HashSet<>();

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "question_sub_topic",
			joinColumns = @JoinColumn(name = "questions_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "sub_topics_id", referencedColumnName = "id")
	)
	private Set<SubTopic> subTopics = new HashSet<>();

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