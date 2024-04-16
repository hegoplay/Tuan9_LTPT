package ex;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "experiences")
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class Experience {
	@Column(name = "company_name")
	private String companyName;
	@Column(name ="from_date")
	private LocalDate fromDate;
	@Column(name ="to_date")	
	private LocalDate toDate;
	private String description;
	@Id
	@ManyToOne
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;
	@Id
	@ManyToOne
	@JoinColumn(name = "position_id")
	private Position position;
}
