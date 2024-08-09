package dev.tsantana.social_media.DTO;

import dev.tsantana.social_media.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsertPostDTO {

	private String text;

	@PastOrPresent
	private String date;

	@NotBlank
	private Long userId;

	public InsertPostDTO(Post post) {
		this.text = post.getText();
		this.date = post.getDate().toString();
		this.userId = post.getUser().getId();
	}

}
