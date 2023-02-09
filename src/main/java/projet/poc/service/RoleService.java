package projet.poc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;


import lombok.AllArgsConstructor;
import projet.poc.dao.RoleRepository;
import projet.poc.domain.Role;

@Service
@AllArgsConstructor
@Transactional
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600, methods = { RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH })
public class RoleService {
	
@Autowired
	private RoleRepository RoleDao;
	public Role createNewRole(Role role) {
		return RoleDao.save(role);
		
	}
	public List<Role> findAllRoles() {
		return RoleDao.findAll();
	}
	

}
