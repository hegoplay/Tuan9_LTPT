package bai4.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode(onlyExplicitlyIncluded = true)
@lombok.ToString(exclude = "projs")
@Entity
public class Employee implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -88390763733514462L;
	@lombok.EqualsAndHashCode.Include
	@Id
	private long employeeId;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	private String email;
	private String phoneNumber;
	@OneToMany(mappedBy = "employee")
	private Set<EmployeeProject> projs;
	
}
