package projet.poc.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projet.poc.dao.ProjectRepository;
import projet.poc.dao.UserRepository;
import projet.poc.domain.Project;
import projet.poc.domain.User;
import projet.poc.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	
	
	@Autowired	
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserRepository userRepository;
	// Find all projects on database
	@Transactional(readOnly = true)
	public Collection<Project> findAllProjects() {
		
		return this.projectRepository.findAll();
	}
	
	
	// Only a manager can create projects. The projects created by a manager are added to his list of projects
	@Transactional
	public Project createProject(Project project,String username) {
		
		User currentUser = userRepository.findByUsername(username).get();
				
		project.setManager(currentUser);
		
		return this.projectRepository.save(project);
	}
	
	@Transactional(readOnly = true)
	public List<Project>  findProjectsOfManager(String managerUsername) {
		
		return this.projectRepository.findAllByManagerUsername(managerUsername);
	}
}