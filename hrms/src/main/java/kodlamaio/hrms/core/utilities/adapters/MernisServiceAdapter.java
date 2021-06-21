package kodlamaio.hrms.core.utilities.adapters;

import org.springframework.stereotype.Component;

import kodlamaio.hrms.mernisService.FakeMernisService;

@Component 
public class MernisServiceAdapter implements ValidationService {

	@Override
	public boolean validateByMernis(long natId, String firstName, String lastName, int yearOfBirth) {
		FakeMernisService client = new FakeMernisService();
		boolean result = true;
		try {
			result = client.ValidateByPersonalInfo(natId, firstName, lastName, yearOfBirth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
