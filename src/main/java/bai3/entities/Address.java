package bai3.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
@Table(name = "address")
@lombok.EqualsAndHashCode
public class Address implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6374778675777165425L;
	private String city;
	private String state;
	private String street;
	private String zipCode;

}
