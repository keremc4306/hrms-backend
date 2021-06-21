package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.CompanyService;
import kodlamaio.hrms.dataAccess.abstracts.CompanyDao;
import kodlamaio.hrms.entities.concretes.Company;

@Service
public class CompanyManager implements CompanyService {
	
	private CompanyDao companyDao;

	@Autowired
	public CompanyManager(CompanyDao companyDao) {
		super();
		this.companyDao = companyDao;
	}

	@Override
	public List<Company> getAll() {
		return this.companyDao.findAll();
	}

	@Override
	public void add(Company company) {
		this.companyDao.save(company);
	}

}
