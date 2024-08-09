package dev.tsantana.social_media.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldMessage {

	private String fieldName;
	private String message;
}
