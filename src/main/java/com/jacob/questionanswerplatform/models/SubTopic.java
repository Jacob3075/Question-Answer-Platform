package com.jacob.questionanswerplatform.models;

import com.jacob.questionanswerplatform.utils.streams.SubTopicStream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sub_topics")
public class SubTopic {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "sub_topic_name")
	private String subTopicName;


	public static SubTopicStream stream(Set<SubTopic> subTopics) {
		return new SubTopicStream(new ArrayList<>(subTopics));
	}

	public static SubTopicStream stream(SubTopic subTopic) {
		return new SubTopicStream(List.of(subTopic));
	}

	public static SubTopicStream stream() {
		return new SubTopicStream(List.of());
	}
}
