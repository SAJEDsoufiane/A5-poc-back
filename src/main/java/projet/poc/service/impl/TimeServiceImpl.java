package projet.poc.service.impl;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projet.poc.dao.ProjectRepository;
import projet.poc.dao.TimeRepository;
import projet.poc.dao.UserRepository;
import projet.poc.domain.Project;
import projet.poc.domain.Time;
import projet.poc.domain.User;
import projet.poc.dto.TimeRequest;
import projet.poc.service.TimeService;

@Service
public class TimeServiceImpl  implements TimeService{


	
	@Autowired	
	private ProjectRepository projectRepository;
	
	@Autowired	
	private TimeRepository timeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	
	// Find all time affections of a user 
	@Transactional(readOnly = true)
	public Optional<Time> findTimesById(Long id) {
		
		return this.timeRepository.findById(id);
		
	}
	// Employees can affect time to a certain project on dashboard
		@Transactional
		public Time createTime(TimeRequest timeRequest,String username) {
			

			Time time = new Time();
			
			time.setDateStart(timeRequest.getDateStart());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
			time.setDateOfProject(String.valueOf(time.getDateStart().format(formatter)));
			time.setDateEnd(timeRequest.getDateEnd());
			User currentUser = userRepository.findByUsername(username).get();
			time.setUser(currentUser);
			
			Project project = this.projectRepository.findById(timeRequest.getProjectId()).orElse(null);
			time.setProject(project);
			
			return this.timeRepository.save(time);
			
		}

	public List<Time> getTimeContent(String userId,String date) {
		// TODO Auto-generated method stub
		return timeRepository.findAllByUserUsernameAndDateOfProject(userId, date);
	}

	// Find all times of a user
	@Override
	public Collection<Time> findTimesOfUser(String idUser) {
		return this.timeRepository.findByUserUsername(idUser);
  }

	// Find all time affections on database
	@Transactional(readOnly = true)
	public Collection<Time> findAllTimes() {
		return this.timeRepository.findAll();
	}

	@Transactional
	public boolean deleteTime(Long timeId) {
		this.timeRepository.deleteById(timeId);
		return true;
	}


	
}