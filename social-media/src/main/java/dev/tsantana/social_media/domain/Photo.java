package dev.tsantana.social_media.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
public class Photo {

	@Include
	@Id
	private String uri;

	@ManyToOne
	@JoinColumn(name = "album_id")
	private Album album;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Photo(String uri, Album album, Post post, User user) {
		super();
		this.uri = uri;
		this.album = album;
		this.post = post;
		this.user = user;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setAlbum(Album album) {
		this.album = album;
		album.addPhotos(this);
	}

	public void setPost(Post post) {
		this.post = post;
		post.addPhoto(this);
	}

	@Override
	public String toString() {
		return "Photo [uri=" + uri + "]";
	}

}
