package dev.tsantana.social_media.DTO;

import dev.tsantana.social_media.domain.Album;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
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

	@PastOrPresent
	private String date;

	@NotBlank
	private Long userId;

	public InsertAlbumDTO(Album album) {
		this.title = album.getTitle();
		this.date = album.getDate().toString();
		this.userId = album.getUser().getId();
	}

}
