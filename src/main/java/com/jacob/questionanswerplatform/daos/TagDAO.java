package com.jacob.questionanswerplatform.daos;

import com.jacob.questionanswerplatform.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagDAO extends JpaRepository<Tag, Long> {
}
