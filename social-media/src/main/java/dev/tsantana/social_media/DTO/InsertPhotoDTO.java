package dev.tsantana.social_media.DTO;

import dev.tsantana.social_media.domain.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertPhotoDTO {

	private String uri;
	private Long userId;
	private Long postId;
	private Long albumId;

	public InsertPhotoDTO(Photo photo) {
		this.uri = photo.getUri();
		this.userId = photo.getUser().getId();
		this.postId = photo.getPost() == null ? null : photo.getPost().getId();
		this.albumId = photo.getPost() == null ? null : photo.getPost().getId();
	}
}
