package dev.tsantana.social_media.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "tb_photo")
public class Photo {

	@Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	public Photo(Long id, String uri, Album album, Post post, User user) {
		super();
		this.id = id;
		this.uri = uri;
		this.album = album;
		this.post = post;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Photo [uri=" + uri + "]";
	}

}
