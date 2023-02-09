package projet.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet.poc.service.RoleService;
import projet.poc.domain.Role;

@RestController
@RequestMapping("/api/role/")

public class RoleController {
@Autowired
	private RoleService RoleService;
	@GetMapping("index")
	public String index() {
		return "hello from role ";
	}
	

	@PostMapping("createNewRole")
	public Role createNewRole(@RequestBody Role role) {
return RoleService.createNewRole(role);
	}
	@GetMapping("/roles")
	public Iterable<Role> findRoles() {
		return RoleService.findAllRoles();
	}
	

}
