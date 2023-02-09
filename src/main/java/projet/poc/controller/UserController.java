package projet.poc.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import org.springframework.web.bind.annotation.RequestMethod;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet.poc.domain.User;
import projet.poc.dto.ChangeUserRequest;
import projet.poc.dto.CreateUserRequest;
import projet.poc.service.UserService;

@RestController
@RequestMapping("/api/user")


public class UserController {
@Autowired
	private UserService userService;

@PostConstruct
public void initRolesAndUsers() {
	userService.initRolesAndUser();
}

@PostMapping({"/registerNewUser"})
	public User registerNewUser(@RequestBody User user) {
		return userService.registerNewUser(user);
	}

@PostMapping("/create/{idUser}")
public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest,@PathVariable String idUser){
	return new ResponseEntity<User>(userService.createUser(createUserRequest, idUser),HttpStatus.CREATED);
}

@PostMapping("/users/change")
public ResponseEntity<User> changeAffectationUser(@RequestBody ChangeUserRequest changeUserRequest){
	return new ResponseEntity<User>(userService.changeAffectationForUser(changeUserRequest),HttpStatus.OK);
}
@GetMapping("/users")
public Iterable<User> index() {
	return userService.findUsers();
}

@PostMapping("/edit")
public ResponseEntity<User> editUser(@RequestBody User user){
	return new ResponseEntity<User>(userService.editUser(user),HttpStatus.OK);
}

@GetMapping("/{idUser}")
public ResponseEntity<User> getUserInfos(@PathVariable String idUser){
	return new ResponseEntity<User>(userService.findUser(idUser),HttpStatus.OK);
}

@GetMapping("/manager/{managerId}")
public ResponseEntity<List<User>> findAllUsersOfManager(@PathVariable String managerId){
	return new ResponseEntity<List<User>>(userService.findUsersofManager(managerId),HttpStatus.OK);
}

@GetMapping({"/forAdmin"})
public String forAdmin() {
	return "this url is only accessible to admin";
}
@GetMapping({"/forManager"})
public String forManager() {
	return "this url is only accessible to manager";
}
@GetMapping({"/forUser"})
//@PreAuthorize("hasRole('3')")
public String forUser() {
	return "this url is only accessible to user";
}
	
}
