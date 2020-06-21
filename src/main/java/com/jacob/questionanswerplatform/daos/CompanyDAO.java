package com.jacob.questionanswerplatform.daos;

import com.jacob.questionanswerplatform.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyDAO extends JpaRepository<Company, Long> {
}
