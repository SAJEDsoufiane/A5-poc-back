package projet.poc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author root
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserRequest {

	private String userId;
	private String managerId;
}