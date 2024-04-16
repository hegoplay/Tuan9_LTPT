package bai4.entities;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.ToString;

@ToString
@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@Entity
//@Table(name = "projects")
//@NamedQueries({
//	@NamedQuery(name = "Project.findByName", query = "SELECT p FROM Project p WHERE p.projectName = :name")
//
//})
public class Project implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2483558489098373880L;
	@Id
	@lombok.EqualsAndHashCode.Include
	@Column(name = "project_id")
	private long projectId;
	@Column(name = "project_name")
	private String projectName;
	private String type;
	@ElementCollection
	@CollectionTable(name = "versions", joinColumns = @jakarta.persistence.JoinColumn(name = "project_id"))
	private Set<String> versions;
	@OneToMany(mappedBy = "project")
	private final Set<EmployeeProject> empls;
	
	
}
