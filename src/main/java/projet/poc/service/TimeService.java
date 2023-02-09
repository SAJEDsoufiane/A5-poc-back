package projet.poc.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import projet.poc.domain.Time;
import projet.poc.dto.TimeRequest;

public interface TimeService {
public Collection<Time> findAllTimes();
	
	
	public Optional<Time> findTimesById(Long id);
	
	
	public Time createTime(TimeRequest timeRequest,String username);
	
	
	public List<Time> getTimeContent(String userId,String date);
	

	public Collection<Time> findTimesOfUser(String idUser);  	


	public boolean deleteTime(Long timeId);

}
