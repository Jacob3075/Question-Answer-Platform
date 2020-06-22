package com.jacob.questionanswerplatform.services;

import com.jacob.questionanswerplatform.daos.CompanyDAO;
import com.jacob.questionanswerplatform.models.Company;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

	private final CompanyDAO companyDAO;

	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public List<Company> getAllCompanies() {
		return companyDAO.findAll();
	}

	public Optional<Company> getCompanyById(Long id) {
		return companyDAO.findById(id);
	}

	public Long createCompany(Company company) {
		return companyDAO.saveAndFlush(company).getId();
	}

	public boolean updateCompany(Long id, Company sourceCompany) {
		Optional<Company> optionalCompany = companyDAO.findById(id);
		if (optionalCompany.isEmpty()) return false;

		Company companyToUpdate = optionalCompany.get();

		companyToUpdate.setCompanyName(sourceCompany.getCompanyName());

		companyDAO.saveAndFlush(companyToUpdate);
		return true;
	}

	public boolean deleteCompany(Long id) {
		Optional<Company> optionalCompany = companyDAO.findById(id);
		if (optionalCompany.isEmpty()) return false;

		companyDAO.deleteById(id);
		return true;
	}


}
