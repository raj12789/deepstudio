package in.deepstudio.framingcentre.service;

import java.util.Collection;

import in.deepstudio.framingcentre.domain.User;
import in.deepstudio.framingcentre.domain.UserCreateForm;

public interface UserService {
	User getUserById(long id);
	User getUserByEmail(String email);
	Collection<User> getAllUsers();
	User create(UserCreateForm form);
	void  delete(Long id);
	UserCreateForm findOne(Long id);
}
