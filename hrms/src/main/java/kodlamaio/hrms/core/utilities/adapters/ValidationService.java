package kodlamaio.hrms.core.utilities.adapters;

public interface ValidationService {
	boolean validateByMernis(long natId, String firstName, String lastName, int yearOfBirth);
}
