package in.deepstudio.framingcentre.service.impl;

import in.deepstudio.framingcentre.domain.CurrentUser;
import in.deepstudio.framingcentre.domain.User;
import in.deepstudio.framingcentre.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CurrentUserDetailsService.class);
	private final UserService userService;

	@Autowired
	public CurrentUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public CurrentUser loadUserByUsername(String email)
			throws UsernameNotFoundException {
		LOGGER.debug("Authenticating user with email={}",
				email.replaceFirst("@.*", "@***"));
		User user = userService.getUserByEmail(email);
		return new CurrentUser(user);
	}
}
