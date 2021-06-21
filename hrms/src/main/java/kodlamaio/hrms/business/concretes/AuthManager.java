package kodlamaio.hrms.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.AuthService;
import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.business.abstracts.JobSeekerService;
import kodlamaio.hrms.business.abstracts.UserService;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.JobSeeker;

@Service
public class AuthManager implements AuthService {

	private UserService userService;
	private EmployerService employerService;
	private JobSeekerService jobSeekerService;

	@Autowired
	public AuthManager(UserService userService, EmployerService employerService, JobSeekerService jobSeekerService) {
		super();
		this.userService = userService;
		this.employerService = employerService;
		this.jobSeekerService = jobSeekerService;
	}

	@Override
	public Result registerEmployer(Employer employer) {
		if (!checkIfNullInfoForEmployer(employer)) {

			return new ErrorResult("You have entered missing information. Please fill in all fields.");
		}

		if (!checkIfEqualEmailAndDomain(employer.getEmail(), employer.getWebAddress())) {

			return new ErrorResult("Invalid email address.");
		}

		if (!checkIfEmailExists(employer.getEmail())) {

			return new ErrorResult(employer.getEmail() + " already exists.");
		}

		if (!checkIfEqualPasswordAndConfirmPassword(employer.getPassword(), employer.getConfirmPassword())) {

			return new ErrorResult("Passwords do not match.");
		}

		employerService.add(employer);

		return new SuccessResult("Registration has been successfully completed");
	}

	@Override
	public Result registerJobSeeker(JobSeeker jobSeeker) {
		if (!checkIfNullInfoForJobseeker(jobSeeker)) {

			return new ErrorResult("You have entered missing information. Please fill in all fields.");
		}

		if(!checkIfExistsTcNum(jobSeeker.getNatId())) {

			return new ErrorResult(jobSeeker.getNatId() + " already exists.");
		}

		if (!checkIfEmailExists(jobSeeker.getEmail())) {

			return new ErrorResult(jobSeeker.getEmail() + " already exists.");
		}

		jobSeekerService.add(jobSeeker);
		return new SuccessResult("Registration has been successfully completed");
	}

	// Validation for employer register ---START---

	private boolean checkIfNullInfoForEmployer(Employer employer) {

		if (employer.getCompanyName() != null && employer.getWebAddress() != null && employer.getEmail() != null
				&& employer.getPhoneNumber() != null && employer.getPassword() != null
				&& employer.getConfirmPassword() != null) {

			return true;

		}

		return false;
	}

	private boolean checkIfEqualEmailAndDomain(String email, String website) {
		String[] emailArr = email.split("@", 2);
		String domain = website.substring(4, website.length());

		if (emailArr[1].equals(domain)) {

			return true;
		}

		return false;
	}

	private boolean checkIfEqualPasswordAndConfirmPassword(String password, String confirmPassword) {

		if (!password.equals(confirmPassword)) {
			return false;
		}

		return true;
	}

	// Validation for employer register ---END---

	// Validation for jobseeker register ---START---
	private boolean checkIfNullInfoForJobseeker(JobSeeker jobSeeker) {

		if (jobSeeker.getFirstName() != null && jobSeeker.getLastName() != null && jobSeeker.getNatId() != null
				&& jobSeeker.getYearOfBirth() != null && jobSeeker.getPassword() != null
				&& jobSeeker.getConfirmPassword() != null && jobSeeker.getEmail() != null) {

			return true;

		}

		return false;
	}

	private boolean checkIfExistsTcNum(String natId) {

		if (this.jobSeekerService.getJobSeekerByNatId(natId).getData() == null) {
			return true;
		}
		return false;
	}

	// Validation for jobseeker register ---END---

	// Common Validation

	private boolean checkIfEmailExists(String email) {

		if (this.userService.getUserByEmail(email).getData() == null) {

			return true;
		}

		return false;
	}
}
