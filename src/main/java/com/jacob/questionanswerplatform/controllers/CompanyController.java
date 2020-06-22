package com.jacob.questionanswerplatform.controllers;

import com.jacob.questionanswerplatform.models.Company;
import com.jacob.questionanswerplatform.services.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/companies/")
public class CompanyController {

	private final CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping(value = "/")
	public List<Company> getAllCompanies() {
		return companyService.getAllCompanies();
	}

	@GetMapping(value = "/{id}")
	public Company getCompanyById(@PathVariable Long id) {
		Optional<Company> optionalCompany = companyService.getCompanyById(id);
		if (optionalCompany.isEmpty()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

		return optionalCompany.get();
	}

	@PostMapping(value = "/")
	public Long createNewCompany(@RequestBody Company company) {
		return companyService.createCompany(company);
	}

	@PutMapping(value = "/{targetId}")
	public void updateCompany(@PathVariable Long targetId, @RequestBody Company sourceCompany,
	                          HttpServletResponse response) {
		if (companyService.updateCompany(targetId, sourceCompany)) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/{id}")
	public void deleteCompany(@PathVariable Long id, HttpServletResponse response) {
		if (companyService.deleteCompany(id)) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}
}
