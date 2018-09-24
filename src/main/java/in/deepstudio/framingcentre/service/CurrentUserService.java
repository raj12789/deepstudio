package in.deepstudio.framingcentre.service;

import in.deepstudio.framingcentre.domain.CurrentUser;

public interface CurrentUserService {
	boolean canAccessUser(CurrentUser currentUser, Long userId);
}
