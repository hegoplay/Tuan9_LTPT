package ex;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="candidates")
public class Candidate {
	@Id
	private String id;
	private String fullName;
	@Column(name= "year_of_birth")
	private int yearOfBirth;
	private String gender;
	private String email;
	@OneToMany(mappedBy = "candidate")
	private Set<Experience> experiences;
	@ManyToOne
	@JoinColumn(name = "position_id")
	private Position position;
}
