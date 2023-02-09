package projet.poc.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;

import projet.poc.domain.Time;
import projet.poc.domain.User;
import projet.poc.dto.TimeRequest;
import projet.poc.export.UserReportExporter;
import projet.poc.service.TimeService;
import projet.poc.service.UserService;

import org.springframework.web.bind.annotation.RequestMethod;


@RestController
@RequestMapping("/api/times")
public class TimeController {
	
	@Autowired 
	private TimeService timeService;
	
	@Autowired 
	private UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<Collection<Time>> findAllTimes(){
		return new ResponseEntity<Collection<Time>>(this.timeService.findAllTimes(),HttpStatus.OK);
	}
	
	@PostMapping("/{username}")
	public ResponseEntity<Time> createTime(@RequestBody TimeRequest timeRequest,@PathVariable String username) {
		return new ResponseEntity<Time>(this.timeService.createTime(timeRequest,username),HttpStatus.CREATED) ;
	}
	
	@DeleteMapping(value = "/{timeId}")
	public ResponseEntity<Long> deletePost(@PathVariable Long timeId) {

		boolean isRemoved = this.timeService.deleteTime(timeId);

	    if (!isRemoved) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }

	    return new ResponseEntity<>(timeId, HttpStatus.OK);
	}

	@GetMapping("/content/{userId}")
	public ResponseEntity<Collection<Time>> index(@PathVariable String userId ) {
		return new ResponseEntity<Collection<Time>>(timeService.findTimesOfUser(userId),HttpStatus.OK);
	}

	
	@GetMapping("/{username}/date/{date}/export/pdf")
	public void exportToPDF(HttpServletResponse response, @PathVariable String username,@PathVariable String date ) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	      
	         
	        List<Time> timesOfUser = timeService.getTimeContent(username,date);
	        
	        User user = userService.findUser(username);
	         
	        UserReportExporter exporter = new UserReportExporter(timesOfUser,user, date);
	        exporter.export(response);
	         
	    }
	
	

	@GetMapping("/{userId}")
	Collection<Time> findUserTimesForManager(@PathVariable String userId) {
		return this.timeService.findTimesOfUser(userId) ;
	}
}