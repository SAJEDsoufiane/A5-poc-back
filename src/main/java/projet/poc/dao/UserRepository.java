package projet.poc.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import projet.poc.domain.User;
@Repository
public interface UserRepository extends CrudRepository<User, String> {
	
	public Optional<User> findByUsername(String username);	
	
	
	//public List<User> findByManagerUserId(String managerId);
	
	
	
	
	public List<User> findAllByUsername(String Username);
	public List<User> findAllByManagerUsername(String managerUsername);

}
