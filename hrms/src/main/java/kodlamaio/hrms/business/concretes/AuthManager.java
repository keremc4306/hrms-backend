package kodlamaio.hrms.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.AuthService;
import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.business.abstracts.JobSeekerService;
import kodlamaio.hrms.business.abstracts.UserService;
import kodlamaio.hrms.core.utilities.adapters.ValidationService;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.core.verification.VerificationService;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.JobSeeker;

@Service
public class AuthManager implements AuthService {

	private UserService userService;
	private EmployerService employerService;
	private JobSeekerService jobSeekerService;
	private VerificationService verificationService;
	private ValidationService validationService;

	@Autowired
	public AuthManager(UserService userService, EmployerService employerService, 
			JobSeekerService jobSeekerService, VerificationService verificationService,
			ValidationService validationService) {
		super();
		this.userService = userService;
		this.employerService = employerService;
		this.jobSeekerService = jobSeekerService;
		this.verificationService = verificationService;
		this.validationService = validationService;
	}

	@Override
	public Result registerEmployer(Employer employer, String confirmPassword) {
		if (!checkIfNullInfoForEmployer(employer)) {

			return new ErrorResult("You have entered missing information. Please fill in all fields.");
		}

		if (!checkIfEqualEmailAndDomain(employer.getEmail(), employer.getWebAddress())) {

			return new ErrorResult("Invalid email address.");
		}

		if (!checkIfEmailExists(employer.getEmail())) {

			return new ErrorResult(employer.getEmail() + " already exists.");
		}

		if (!checkIfEqualPasswordAndConfirmPassword(employer.getPassword(), confirmPassword)) {

			return new ErrorResult("Passwords do not match.");
		}

		employerService.add(employer);
		verificationService.sendCode(employer.getEmail(), employer.getId());
		
		return new SuccessResult("Registration has been successfully completed");
	}

	@Override
	public Result registerJobSeeker(JobSeeker jobSeeker, String confirmPassword) {
		if (checkIfRealPerson(Long.parseLong(jobSeeker.getNatId()), jobSeeker.getFirstName(),
				jobSeeker.getLastName(), jobSeeker.getDateOfBirth().getYear()) == false) {
			return new ErrorResult("TC Id number could not be verified.");
		}
		
		if (!checkIfNullInfoForJobseeker(jobSeeker, confirmPassword)) {

			return new ErrorResult("You have entered missing information. Please fill in all fields.");
		}

		if(!checkIfExistsTcNum(jobSeeker.getNatId())) {

			return new ErrorResult(jobSeeker.getNatId() + " already exists.");
		}

		if (!checkIfEmailExists(jobSeeker.getEmail())) {

			return new ErrorResult(jobSeeker.getEmail() + " already exists.");
		}

		jobSeekerService.add(jobSeeker);
		verificationService.sendCode(jobSeeker.getEmail(), jobSeeker.getId());
		return new SuccessResult("Registration has been successfully completed");
	}

	private boolean checkIfNullInfoForEmployer(Employer employer) {

		if (employer.getCompanyName() != null && employer.getWebAddress() != null 
				&& employer.getEmail() != null && employer.getPhoneNumber() != null
				&& employer.getPassword() != null) {

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

	private boolean checkIfNullInfoForJobseeker(JobSeeker jobSeeker, String confirmPassword) {

		if (jobSeeker.getFirstName() != null && jobSeeker.getLastName() != null && jobSeeker.getNatId() 
				!= null && jobSeeker.getDateOfBirth() != null 
				&& jobSeeker.getPassword() != null && jobSeeker.getEmail() 
				!= null && confirmPassword != null) {

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

	private boolean checkIfRealPerson(long natId, String firstName, String lastName, int yearOfBirth) {

		if (validationService.validateByMernis(natId, firstName, lastName, yearOfBirth)) {
			return true;
		}
		return false;
	}

	private boolean checkIfEmailExists(String email) {

		if (this.userService.getUserByEmail(email).getData() == null) {

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
}
