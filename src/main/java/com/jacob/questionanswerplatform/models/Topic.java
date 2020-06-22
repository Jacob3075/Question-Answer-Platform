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
@Table(name = "topics")
public class Topic {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "topic_name")
	private String topicName;

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "sub_topic_topic",
			joinColumns = @JoinColumn(name = "sub_topics_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "topics_id", referencedColumnName = "id")
	)
	private Set<SubTopic> subTopics = new HashSet<>();
}
