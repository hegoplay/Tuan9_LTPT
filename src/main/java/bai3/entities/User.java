package bai3.entities;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//@Entity
@Getter
@Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.ToString
//@Table(name="users")
@lombok.EqualsAndHashCode
public class User implements Serializable {
	private static final long serialVersionUID = -3127157196329403413L;
	/**
	 * 
	 */
	@Id
	private long id;
	@ElementCollection
	@CollectionTable(name="research_areas",joinColumns = @jakarta.persistence.JoinColumn(name="user_id"))
	@jakarta.persistence.Column(name="research_areas")
	private Set<String> researchAreas;
	@jakarta.persistence.Column(name="user_email")
	private String userEmail ;
	@jakarta.persistence.Column(name="user_name")
	private String userName;
	@jakarta.persistence.Column(name="user_password")
	private String userPassword;
	@Embedded
	private Address userAddress;
	@OneToMany(mappedBy = "user")
	private Set<Comment> comments;
	
}
