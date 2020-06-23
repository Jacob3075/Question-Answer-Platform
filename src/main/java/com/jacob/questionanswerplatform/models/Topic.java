package com.jacob.questionanswerplatform.models;


import com.jacob.questionanswerplatform.utils.streams.TopicStream;
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
@Table(name = "topics")
public class Topic {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "topic_name")
	private String topicName;

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "topic_subtopic",
			joinColumns = @JoinColumn(name = "topics_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "subtopics_id", referencedColumnName = "id")
	)
	private Set<SubTopic> subTopics = new HashSet<>();

	public static TopicStream stream(List<Topic> topics) {
		return new TopicStream(topics);
	}

	public static TopicStream stream(Topic topic) {
		return new TopicStream(List.of(topic));
	}

	public static TopicStream stream() {
		return new TopicStream(List.of());
	}
}
