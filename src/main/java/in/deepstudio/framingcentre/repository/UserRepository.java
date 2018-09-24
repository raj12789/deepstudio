package in.deepstudio.framingcentre.repository;

import in.deepstudio.framingcentre.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User findOneByEmail(String email);
	@Override
	public void delete(User user);
	@Override
	boolean exists(Long primaryKey);
	@Override
	User findOne(Long id);
}
