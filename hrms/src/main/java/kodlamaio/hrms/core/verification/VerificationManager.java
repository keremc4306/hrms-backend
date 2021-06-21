package kodlamaio.hrms.core.verification;

import java.util.UUID;

public class VerificationManager implements VerificationService {

	@Override
	public void sendLink(String email) {
		UUID uuid = UUID.randomUUID();
		String verfLink = "https://hrmsverificationmail/" + uuid.toString();
		System.out.println("Verification link has been sent to " + email );
		System.out.println("Please click on the link to verify your account: " + verfLink);
	}

	@Override
	public void sendCode(String email) {
		UUID uuid = UUID.randomUUID();
		String verfCode = uuid.toString();
		System.out.println("Verification link has been sent to " + email );
		System.out.println("Your activation code: " + verfCode);	
	}

}
