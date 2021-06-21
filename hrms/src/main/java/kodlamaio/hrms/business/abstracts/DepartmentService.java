package kodlamaio.hrms.business.abstracts;

import java.util.List;

import kodlamaio.hrms.entities.concretes.Department;

public interface DepartmentService {
	List<Department> getAll();
	void add(Department department);
}
