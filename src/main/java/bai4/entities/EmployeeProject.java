package bai4.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode
@lombok.ToString(exclude = "project")
@lombok.NoArgsConstructor
@Entity
@Table(name = "employee_project")
public class EmployeeProject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5583537085251000587L;
	int hour;
	@jakarta.persistence.Id
	@jakarta.persistence.ManyToOne
	@jakarta.persistence.JoinColumn(name = "project_id")
	Project project;
	@jakarta.persistence.Id
	@jakarta.persistence.ManyToOne
	@jakarta.persistence.JoinColumn(name = "employee_id")
	Employee employee;
	
}
