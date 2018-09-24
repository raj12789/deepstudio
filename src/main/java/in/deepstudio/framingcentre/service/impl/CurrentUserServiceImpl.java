package in.deepstudio.framingcentre.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import in.deepstudio.framingcentre.domain.CurrentUser;
import in.deepstudio.framingcentre.domain.Role;
import in.deepstudio.framingcentre.service.CurrentUserService;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CurrentUserDetailsService.class);

	@Override
	public boolean canAccessUser(CurrentUser currentUser, Long userId) {
		LOGGER.debug("Checking if user={} has access to user={}", currentUser,
				userId);
		return currentUser != null
				&& (currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.SUPER_ADMIN || currentUser.getId()
						.equals(userId));
	}

}
