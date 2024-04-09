package bai3.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.ToString(exclude = {"user"})
@lombok.EqualsAndHashCode
@Entity
@Table(name="comments")
@NamedQueries({
	@NamedQuery(
			name = "Comment.findById", 
			query = "SELECT c FROM Comment c "
					+ "where c.user.id = :user_id "
					+ "and c.news.id = :news_id"),
})
public class Comment implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1163644136950594773L;
	private String commnentContent;
	private LocalDateTime date;
	@ManyToOne
	@jakarta.persistence.JoinColumn(name="user_id")
	@Id
	private User user;
	@ManyToOne
	@jakarta.persistence.JoinColumn(name="news_id")
	@Id
	private News news;
}
