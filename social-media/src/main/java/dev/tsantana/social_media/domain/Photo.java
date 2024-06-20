package dev.tsantana.social_media.domain;

import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Photo {

	@Include
	private String uri;
	private Album album;
	private Post post;
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
