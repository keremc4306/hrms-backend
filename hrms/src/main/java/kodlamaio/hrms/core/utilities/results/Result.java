package kodlamaio.hrms.core.utilities.results;

public class Result {
	private boolean success;
	private String message;
	
	public Result(boolean success) {
		this.success = success; //success'i set etmiş oluyor.
	}
	
	public Result(boolean success, String message) {
		this(success); //Tek parametreli olan constructor'ı çağırdık.
		this.message = message; //Kendi mesajını set etmiş oluyor.
	}
	
	public boolean isSuccess() {
		return this.success;
	}
	
	public String getMessage() {
		return this.message;
	}
}