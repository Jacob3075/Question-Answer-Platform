package com.jacob.questionanswerplatform.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

	@ToString.Exclude
	@OneToMany(mappedBy = "user")
	private List<Answer> answers = new ArrayList<>();

	@ToString.Exclude
	@OneToMany(mappedBy = "user")
	private List<Question> questions = new ArrayList<>();

	@ToString.Exclude
	@OneToMany(mappedBy = "user")
	private List<Comment> comments = new ArrayList<>();
}
