package ex;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "certificates")
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class Certificate {
	@Id
	@Column(name = "certificate_id")
	private String id;
	
	private String name;
	private String organization;
	@Column(name = "issue_date")
	private LocalDate issueDate;
	@ManyToOne
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;
	
}
