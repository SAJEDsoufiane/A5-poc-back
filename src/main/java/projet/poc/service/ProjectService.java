package projet.poc.service;

import java.util.Collection;
import java.util.List;

import projet.poc.domain.Project;

public interface ProjectService {

	public Collection<Project> findAllProjects();
	
	public Project createProject(Project project,String username);
	
	public List<Project>  findProjectsOfManager(String managerId);

}
