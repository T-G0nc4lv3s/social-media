package dev.tsantana.social_media.domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "TB_POST")
public class Post {

	@Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String text;
	
	@Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private Instant date;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "post")
	private Set<Photo> photos = new HashSet<Photo>();

	public Post(Long id, String text, User user) {
		super();
		this.id = id;
		this.text = text;
		this.user = user;
		this.date = Instant.now();
		user.addPost(this);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void addPhoto(Photo photo) {
		this.photos.add(photo);
	}

	public void removePhoto(Photo photo) {
		this.photos.remove(photo);
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", text=" + text + ", date=" + date + ", photos=" + photos + "]";
	}

}
