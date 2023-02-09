package projet.poc.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import projet.poc.dao.RoleRepository;
import projet.poc.dao.UserRepository;
import projet.poc.domain.Role;
import projet.poc.domain.User;
import projet.poc.dto.ChangeUserRequest;
import projet.poc.dto.ChangeUserRoleRequest;
import projet.poc.dto.CreateUserRequest;

@Service
public class UserService {
	@Autowired
	private UserRepository userDao;
	@Autowired
	private RoleRepository roleDao;
	@Autowired
	private PasswordEncoder encoder;

	public User registerNewUser(User user) {
		return userDao.save(user);
	}
	
	public void initRolesAndUser() {
		Role adminRole=new Role();
		adminRole.setId((long) 1);
		adminRole.setLabel("admin");
		roleDao.save(adminRole);
		
		Role managerRole=new Role();
		managerRole.setId((long) 2);
		managerRole.setLabel("manager");
		roleDao.save(managerRole);
		
		Role userRole=new Role();
		userRole.setId((long) 3);
		userRole.setLabel("user");
		roleDao.save(userRole);
		
		User adminUser = new User();
		adminUser.setId((long) 1);
		adminUser.setUsername("admin12");
		adminUser.setFname("admin12");
		adminUser.setLname("admin12");
		adminUser.setPassword(encoder.encode("admin"));
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRole(adminRoles.iterator().next());
		userDao.save(adminUser);
		
		User managerUser = new User();
		managerUser.setId((long) 2);
		managerUser.setUsername("manager123");
		managerUser.setFname("manager");
		managerUser.setLname("manager");
		managerUser.setPassword(encoder.encode("manager"));
		Set<Role> managerRoles = new HashSet<>();
		managerRoles.add(managerRole);
		managerUser.setRole(managerRoles.iterator().next());
		userDao.save(managerUser);
		
		User User = new User();
		User.setId((long) 3);
		User.setUsername("soufiane123");
		User.setFname("soufiane");
		User.setLname("sajed");
		User.setPassword(encoder.encode("sajed"));
		Set<Role> userRoles = new HashSet<>();
		userRoles.add(userRole);
		User.setRole(userRoles.iterator().next());
		userDao.save(User);
		
	}
	public User createUser(CreateUserRequest createUserRequest,String idUser) {

		// save data's coming from inputs
		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		user.setPassword(encoder.encode(createUserRequest.getPassword()));
		user.setFirstname(createUserRequest.getFirstname());
		user.setLastname(createUserRequest.getLastname());
		user.setFullname(createUserRequest.getLastname()+" "+createUserRequest.getFirstname());

		// Set the user's role
		
		Role role = roleDao.findById(createUserRequest.getRoleId()).get();
		user.setRole(role);
		// if this ( MANAGER ) ROLE <= EMPLOYEE ( COMMING FROM the client side)
		
		
		User currentUser = userDao.findById(idUser).get();
		if(currentUser.getRole().getLabel().equals("manager"))
			user.setManager(currentUser);
		
		 if(currentUser.getRole().getLabel().equals("admin")) {
			if(createUserRequest.getManagerId()!=null)
			{User manager = userDao.findById(createUserRequest.getManagerId()).get();
			user.setManager(manager);}
		}
			
		
		User savedUser = userDao.save(user);
		
		return savedUser;
	}
	public Collection<User> findUsers() {
		Iterable<User> usersIterable = userDao.findAll();
		Collection<User> usersCollection = new ArrayList<>();
		for (User user : usersIterable) {
			usersCollection.add(user);
		}
		return usersCollection;
	}
	
public User changeAffectationForUser(ChangeUserRequest changeUserRequest) {
		
		// get user by id and manager by id
		
		User user = userDao.findById(changeUserRequest.getUserId()).get();
		User manager = userDao.findById(changeUserRequest.getManagerId()).get();
		
		//user.getRole().getId() if roleId == 1 then do
		// user.setManager(manager)
		//userRepository.save(User)
		
		if(user.getRole().getId().equals((long)1) && manager.getRole().getId().equals((long)2)) {
			user.setManager(manager);
		}
		
		return userDao.save(user);
	}
public User changeUserRole(ChangeUserRoleRequest changeUserRoleRequest) {
	
	User user = userDao.findById(changeUserRoleRequest.getUserId()).get();
	Role newRole = roleDao.findById(changeUserRoleRequest.getRoleId()).get();
	
	//if the current role is manager 
	//we should delete manager for affected users 
	//manager -> admin 
	if (user.getRole().getId().equals((long)2) && !newRole.getId().equals((long)2)) {
  
		List <User> usersOfManager = findUsersofManager(user.getUserId());
		for (User u :usersOfManager) {
			u.setManager(null);
			userDao.save(u);				
		}						
	}

	//employee -> manager or employee -> admin
	if (user.getRole().getId().equals((long)1) && (newRole.getId().equals((long)2) || newRole.getId().equals((long)3))) {
		user.setManager(null);
		
	}
	
	//affect the new role
	user.setRole(newRole);
	
	return userDao.save(user);
}
public List<User> findUsersofManager(String username) {
	return userDao.findAllByManagerUsername(username);
}
public User findUser(String idUser) {
	
	return userDao.findById(idUser).get();
}

public User editUser(User user_) {
	System.out.println(user_.getFirstname());
	System.out.println(user_.getRole().getLabel());
	System.out.println("*************");
	System.out.println(user_.getRole().getId());
	System.out.println("**************");
	User user = userDao.findById(user_.getUserId()).get();
	Role newRole = roleDao.findById(user_.getRole().getId()).get();

	
	//changing infos
	user.setFirstname(user_.getFirstname());
	user.setLastname(user_.getLastname());
	user.setUsername(user_.getUserId());
	
	
	// changing role
	if (user.getRole().getId().equals((long)2) && !newRole.getId().equals((long)2)) {
	      
		List <User> usersOfManager = findUsersofManager(user.getUserId());
		for (User u :usersOfManager) {
			u.setManager(null);
			userDao.save(u);				
		}						
	}

	//employee -> manager or employee -> admin
	if (user.getRole().getId().equals((long)1) && (newRole.getId().equals((long)2) || newRole.getId().equals((long)3))) {
		user.setManager(null);
		
	}

	user.setRole(newRole);
	
	
	//changing affectation
	if (user_.getManager()!=null){   	
		User manager = userDao.findByUsername(user_.getManager().getUserName()).get();	
		user.setManager(manager);
	}
	
	
	return userDao.save(user);	
}


}
