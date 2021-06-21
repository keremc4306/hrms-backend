package kodlamaio.hrms.business.abstracts;

import java.util.List;

import kodlamaio.hrms.entities.concretes.Company;

public interface CompanyService {
	List<Company> getAll();
	void add(Company company);
}
