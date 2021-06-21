package kodlamaio.hrms.business.concretes;

import java.util.List;

import kodlamaio.hrms.business.abstracts.UserService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.UserDao;
import kodlamaio.hrms.entities.concretes.User;

public class UserManager implements UserService {

	private UserDao userDao;
	
	public UserManager(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Override
	public DataResult<List<User>> getAll() {
		return new SuccessDataResult<List<User>>(this.userDao.findAll());
	}

	@Override
	public Result add(User user) {
		this.userDao.save(user);
		return new SuccessResult("User has been added");
	}

	@Override
	public DataResult<User> getUserByEmail(String email) {
		return new SuccessDataResult<User>
		(this.userDao.findUserByEmail(email));
	}

}
