package projet.poc.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projet.poc.domain.Role;

@Repository
	public interface RoleRepository extends JpaRepository<Role, Long> {

	}

