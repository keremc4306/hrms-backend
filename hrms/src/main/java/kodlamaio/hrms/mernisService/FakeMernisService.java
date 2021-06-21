package kodlamaio.hrms.mernisService;

public class FakeMernisService {
	public boolean ValidateByPersonalInfo(long natId, String firstName, String lastName, int yearOfBirth)
	{
		System.out.println(firstName + " " + lastName + " is valid person.");
		return true;
	}
}
