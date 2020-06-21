package com.jacob.questionanswerplatform.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

}
