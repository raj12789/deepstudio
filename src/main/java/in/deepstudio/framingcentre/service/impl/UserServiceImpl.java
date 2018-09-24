package in.deepstudio.framingcentre.service.impl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.deepstudio.framingcentre.domain.User;
import in.deepstudio.framingcentre.domain.UserCreateForm;
import in.deepstudio.framingcentre.repository.UserRepository;
import in.deepstudio.framingcentre.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserServiceImpl.class);
	
	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User getUserById(long id) {
		LOGGER.debug("Getting user={}", id);
		return userRepository.findOne(id);
	}

	@Override
	public User getUserByEmail(String email) {
		LOGGER.debug("Getting user by email={}",
				email.replaceFirst("@.*", "@***"));
		return userRepository.findOneByEmail(email);
	}

	@Override
	public Collection<User> getAllUsers() {
		LOGGER.debug("Getting all users");
		return userRepository.findAll(new Sort("email"));
	}

	@Override
	public User create(UserCreateForm form) {
		User user = new User();
		user.setId(form.getId());
		user.setEmail(form.getEmail());
		user.setPasswordHash(new BCryptPasswordEncoder().encode(form
				.getPassword()));
		user.setRole(form.getRole());
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		LOGGER.info("User Exists Or Not :"+userRepository.exists(id));
		if(userRepository.exists(id)){
			User user = userRepository.findOne(id);
			LOGGER.info("User Deleting :"+user.getEmail());
			try{
			userRepository.delete(user);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public UserCreateForm findOne(Long id) {
		// TODO Auto-generated method stub
		User user = userRepository.findOne(id);
		UserCreateForm userform = new UserCreateForm();
		userform.setId(user.getId());
		userform.setEmail(user.getEmail());
		userform.setPassword(user.getPasswordHash());
		userform.setPasswordRepeated(user.getPasswordHash());
		userform.setRole(user.getRole());
		return userform;
	}

	


}
