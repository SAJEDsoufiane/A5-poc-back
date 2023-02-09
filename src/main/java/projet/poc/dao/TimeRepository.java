package projet.poc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projet.poc.domain.Time;

public interface TimeRepository extends JpaRepository<Time, Long> {
	public List<Time> findAllByUserUsernameAndDateOfProject(String username,String dateOfProject);

	public List<Time> findByUserUsername(String userUsername);

}
