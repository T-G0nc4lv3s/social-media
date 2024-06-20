package dev.tsantana.social_media.domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Post {

	@Include
	private Long id;
	private String text;
	private Instant date;
	private User user;

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
