package bai3.entities;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.ToString(exclude = "comments")
@Entity
@Table(name = "news")
@lombok.EqualsAndHashCode
public class News implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1384997248403349497L;
	private LocalDateTime creationDate;
	@Id
	@Column(name = "news_id")
	private long id;
	@ElementCollection
	@CollectionTable(name = "categories", joinColumns = @jakarta.persistence.JoinColumn(name = "news_id"))
	private Set<String> newCategories;
	@jakarta.persistence.Column(name = "news_content")
	private String newsContent;
	@jakarta.persistence.Column(name = "news_title")
	private String newsTitle;
	@ElementCollection
	@CollectionTable(name = "tags", joinColumns = @jakarta.persistence.JoinColumn(name = "news_id"))
	private Set<String> tags;
	@ElementCollection
	@CollectionTable(name = "votes", joinColumns = @jakarta.persistence.JoinColumn(name = "news_id"))
	private Set<Integer> votes;
	@OneToMany(mappedBy = "news")
	private Set<Comment> comments;
}
