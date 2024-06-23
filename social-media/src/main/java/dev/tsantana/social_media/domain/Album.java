package dev.tsantana.social_media.domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

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
@Table(name = "TB_ALBUM")
public class Album {

	@Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private Instant date;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "album")
	private Set<Photo> photos = new HashSet<Photo>();

	public Album(Long id, String title, User user) {
		super();
		this.id = id;
		this.title = title;
		this.user = user;
		this.date = Instant.now();
		user.addAlbum(this);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void addPhotos(Photo photo) {
		this.photos.add(photo);
	}

	public void removePhotos(Photo photo) {
		this.photos.remove(photo);
	}

	@Override
	public String toString() {
		return "Album [id=" + id + ", title=" + title + ", date=" + date + ", photos=" + photos + "]";
	}

}
