package dev.tsantana.social_media.DTO;

import dev.tsantana.social_media.domain.Album;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InsertAlbumDTO {

	private String title;
	private String date;
	private Long userId;

	public InsertAlbumDTO(Album album) {
		this.title = album.getTitle();
		this.date = album.getDate().toString();
		this.userId = album.getUser().getId();
	}

}
