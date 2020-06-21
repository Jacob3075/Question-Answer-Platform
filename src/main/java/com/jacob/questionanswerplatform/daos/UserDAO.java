package com.jacob.questionanswerplatform.daos;

import com.jacob.questionanswerplatform.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Long> {
}
