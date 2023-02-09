package projet.poc.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet.poc.domain.Project;
import projet.poc.service.ProjectService;

import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
	
	@Autowired 
	private ProjectService projectService;
	
	@GetMapping("/projects")
	Collection<Project> findAllProjects(){
		return this.projectService.findAllProjects();
	}
	
	@PostMapping("/projects/{username}")
	Project createTask(@RequestBody Project project,
			@PathVariable String username) {
		return this.projectService.createProject(project,username);
	}
}