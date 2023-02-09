package projet.poc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projet.poc.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{
	List<Project> findAllByManagerUsername(String ManagerUsername);


}
