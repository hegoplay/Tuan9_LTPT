package bai2.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@Entity
public class Staff implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5346831173976218539L;
	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private int active;
}
